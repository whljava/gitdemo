package com.mycom.shiro.config;

import com.mycom.shiro.dao.User;


import com.mycom.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @功能描述：TODO
 * @创建日期: 2019/2/22 18:56
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行授权逻辑");
        //给资源授权
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();

        //添加资源授权字符串(静态)
        //info.addStringPermission("user:add");

        //到数据库查询当前用户的授权字符串
        //从登录认证逻辑获取当前的登录用户
        Subject subject=SecurityUtils.getSubject();
        User user=(User)subject.getPrincipal();
        //查询出来用户包含用户权限的所有用户
        User user1=userService.findById(user.getId());
        info.addStringPermission(user1.getPerms());
        return info;
    }


    /**
     * 执行认证逻辑
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("执行认证逻辑");

        //编写shiro判断逻辑，判断用户名和密码
        UsernamePasswordToken token  =  (UsernamePasswordToken)authenticationToken;

        //测试登录拦截
//        String name="admin";
//        String password="123456";
//        if(!token.getUsername().equals(name)){
//            return null;
//        }
//
//        return new SimpleAuthenticationInfo("",password,"");
        //根据token传递的用户名查询对应的数据库数据
        User user = userService.findByName(token.getUsername());
//
        //1、判断用户名
        if(user == null){
            //用户名不存在
            return null; //shiro底层会抛出UnKnowAccountException
        }

        //2、判断密码, 这里的user是principal
        return new SimpleAuthenticationInfo(user,user.getPassword(),"");
    }


}
