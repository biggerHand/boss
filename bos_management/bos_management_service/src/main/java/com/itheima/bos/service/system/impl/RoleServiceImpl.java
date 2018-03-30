package com.itheima.bos.service.system.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;

/**  
 * ClassName:RoleServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午8:43:21 <br/>       
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private MenuRepository menuRepository;
    
    @Override
    public Page<Role> queryPage(Pageable pageable) {
        return roleRepository.findAll(pageable);
    }

    @Override
    public void save(Role model, Long[] permissionIds, String menuIds) {
        Role role = roleRepository.save(model);
        Set<Permission> permissions = role.getPermissions();
        Permission permission;
        /*for (int i = 0; i < permissionIds.length; i++) {
            permission = permissionRepository.findOne(permissionIds[i]);
            permissions.add(permission);
        }*/
        if(permissionIds!=null && permissionIds.length>0){
            for (int i = 0; i < permissionIds.length; i++) {
                permission = new Permission();
                permission.setId(permissionIds[i]);
                permissions.add(permission);
            }
        }
        Set<Menu> menus = role.getMenus();
        Menu menu;
       /* for (int z = 0; z < split.length; z++) {
            menu = menuRepository.findOne(Long.parseLong(split[z]));
            menus.add(menu);
        }*/
        if(StringUtils.isNotEmpty(menuIds)){
            String[] split = menuIds.split(",");
            for (int z = 0; z < split.length; z++) {
                menu = new Menu();
                menu.setId(Long.parseLong(split[z]));
                menus.add(menu);
            }
        }
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
  
