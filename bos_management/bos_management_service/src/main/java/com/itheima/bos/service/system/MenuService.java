package com.itheima.bos.service.system;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.system.Menu;

/**  
 * ClassName:MenuService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:20:33 <br/>       
 */
public interface MenuService {

    void save(Menu model);

    List<Menu> findParentMenu();

    Page<Menu> findAll(Pageable pageable);

    List<Menu> findAll();

}
  
