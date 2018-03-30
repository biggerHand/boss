package com.itheima.bos.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;

/**  
 * ClassName:MenuServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:20:49 <br/>       
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    
    @Autowired
    private MenuRepository menuRepository;
    @Override
    public void save(Menu model) {
        if(model.getParentMenu()!=null && model.getParentMenu().getId()==null){
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }
    
    @Override
    public List<Menu> findParentMenu() {
        
        return menuRepository.findByParentMenuIsNull();
    }

    @Override
    public Page<Menu> findAll(Pageable pageable) {
        
        return menuRepository.findAll(pageable);
    }

    @Override
    public List<Menu> findAll() {
          
        return menuRepository.findAll();
    }

}
  
