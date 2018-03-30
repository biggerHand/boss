package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Permission;

/**  
 * ClassName:PermissionService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午8:11:14 <br/>       
 */
public interface PermissionService {

    Page<Permission> findAll(Pageable pageable);

    void save(Permission model);

    List<Permission> findAll();


}
  
