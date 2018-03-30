package com.itheima.bos.service.system.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;

/**  
 * ClassName:UserServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午10:30:25 <br/>       
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void save(User model, Long[] roleIds) {
        User user = userRepository.save(model);
        Set<Role> roles = user.getRoles();
        Role role;
        if(roleIds!=null && roleIds.length>0){
            for (int i = 0; i < roleIds.length; i++) {
                role =new Role();
                role.setId(roleIds[i]);
                roles.add(role);
            }
        }
    }
}
  
