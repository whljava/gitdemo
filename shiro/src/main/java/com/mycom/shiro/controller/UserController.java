package com.mycom.shiro.controller;

import com.mycom.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @功能描述：TODO
 * @创建日期: 2019/2/22 19:05
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public String index() {
        return "index.html";
    }

    @RequestMapping("/add")
    public String add() {
        return "add.html";
    }

    @RequestMapping("/update")
    public String update() {
        return "update.html";
    }

    @RequestMapping("/tologin")
    public String toLogin() {
        return "login.html";
    }

    @RequestMapping("/noAuth")
    public String noAuth() {
        return "noAuth.html";
    }
    @RequestMapping("/login")
    public String login(String name,String password) {
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装使用户数据
        UsernamePasswordToken  token=new UsernamePasswordToken(name,password);
        try {
            //交给Realm处理--->执行它的认证方法
            subject.login(token);
            //登录成功
            System.out.println("成功");
            return "redirect:/index";
        }catch (UnknownAccountException e){
            //登录失败:用户名不存在
            System.out.println("用户名不存在");
            return "redirect:/tologin";
        }catch (IncorrectCredentialsException e){
            //登录失败：密码错误
            System.out.println("密码错误");
            return "redirect:/tologin";
        }

    }
}
