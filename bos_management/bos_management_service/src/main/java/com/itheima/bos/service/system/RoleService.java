package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Role;

/**  
 * ClassName:RoleService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午8:43:09 <br/>       
 */
public interface RoleService {

    Page<Role> queryPage(Pageable pageable);

    void save(Role model, Long[] permissionIds, String menuIds);

    List<Role> findAll();




}
  
