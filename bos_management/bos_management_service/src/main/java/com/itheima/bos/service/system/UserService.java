package com.itheima.bos.service.system;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.User;

/**  
 * ClassName:UserService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月30日 上午10:30:11 <br/>       
 */
public interface UserService {

    Page<User> findAll(Pageable pageable);

    void save(User model, Long[] roleIds);

}
  
