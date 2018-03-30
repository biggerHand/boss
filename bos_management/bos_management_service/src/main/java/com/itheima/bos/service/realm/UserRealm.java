package com.itheima.bos.service.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.User;

import net.sf.json.JsonConfig;

/**  
 * ClassName:UserReakm <br/>  
 * Function:  <br/>  
 * Date:     2018年3月28日 下午8:35:23 <br/>       
 */
@Component
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;
    //授权的方法
    //每次访问需要资源时调用
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRole("admin");
        return null;
    }
    //认证的方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken token2 =(UsernamePasswordToken) token;
        String username = token2.getUsername();
        User user =userRepository.findByUsername(username);
        if(user!=null){
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
            return info;
        }
        return null;
    }

}
  
