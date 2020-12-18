package com.zking.shiro01;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

public class Test {


    public static void main(String[] args) {
        //加载读取ini的配置文件
        IniSecurityManagerFactory factory=
                new IniSecurityManagerFactory("classpath:shiro-permission.ini");

        //创建securityManager安全管理器
        SecurityManager securityManager = factory.getInstance();
        //将安全管理器委托Securityutils
        SecurityUtils.setSecurityManager(securityManager);
        //创建subject主体
        Subject subject = SecurityUtils.getSubject();

        //创建登录token令牌
        UsernamePasswordToken token=new UsernamePasswordToken(
                "zs",
                "123"
        );
        //异常：
        // org.apache.shiro.authc.UnknownAccountException：账号错误
        //org.apache.shiro.authc.IncorrectCredentialsException：密码错误
        //org.apache.shiro.authz.UnauthorizedException:授权异常


        //身份验证
        try {
            subject.login(token);
            System.out.println("用户认证成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("用户认证失败");
        }

        //角色认证
        try {
           /* if (subject.hasRole("role2")) {
                System.out.println("角色认证成功！");
            } else {
                System.out.println("角色认证失败！");
            }*/
           subject.checkRole("role1");
            System.out.println("角色认证成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //权限验证
        try {
           /* if (subject.isPermitted("system:user:delete")) {
                System.out.println("权限验证成功！");
            } else {
                System.out.println("权限验证失败！");
            }*/
           subject.checkPermission("system:user:add");
            System.out.println("权限验证成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("权限验证失败！");
        }

        //安全退出
        subject.logout();

    }

}
