package com.itheima.bos.web.action.system;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.realm.UserRealm;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:UserAction <br/>
 * Function: <br/>
 * Date: 2018年3月28日 下午8:17:46 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class UserAction extends CommonAction<User> {

    public UserAction() {
        super(User.class);
    }

    private String checkcode;

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    @Action(value = "userAction_login",
            results = {@Result(name = "success", location = "/index.html", type = "redirect"),
                    @Result(name = "login", location = "/login.html",type = "redirect")})
    public String login() throws IOException {
        String code = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        if (StringUtils.isNotEmpty(checkcode) && checkcode.equalsIgnoreCase(code)) {
            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token =
                    new UsernamePasswordToken(model.getUsername(), model.getPassword());
            try {
                subject.login(token);
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            } catch (UnknownAccountException e) {
                e.printStackTrace();
                System.out.println("用户名错误");
            } catch (IncorrectCredentialsException e) {
                e.printStackTrace();
                System.out.println("密码错误");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("服务器异常");
            }
        }
        return LOGIN;
    }
    @Action(value = "userAction_logout",
            results = {@Result(name = "success", location = "/login.html", type = "redirect")})
    public String logout() throws IOException {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HttpSession session = ServletActionContext.getRequest().getSession();
        session.removeAttribute("user");
        return SUCCESS;
    }
    @Autowired
    private UserService userService;
    @Action(value = "userAction_queryPage")
    public String queryPage(){
        Pageable pageable = new PageRequest(page-1, rows);
        Page<User> page = userService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"roles"});
        findByPage(page, jsonConfig );
        return NONE;
    }
    private Long[] roleIds;
    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }
    @Action(value = "userAction_save", results = {@Result(name = "success", location = "/pages/system/userindex.html", type = "redirect")})
    public String save() {
        userService.save(model,roleIds);
        return SUCCESS;
    }
}
