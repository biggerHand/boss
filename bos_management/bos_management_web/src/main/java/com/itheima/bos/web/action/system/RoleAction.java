package com.itheima.bos.web.action.system;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:RoleAction <br/>
 * Function: <br/>
 * Date: 2018年3月29日 下午8:41:04 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class RoleAction extends CommonAction<Role> {

    public RoleAction() {
        super(Role.class);
    }

    @Autowired
    private RoleService roleService;
    @Action(value = "roleAction_queryPage")
    public String queryPage() {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Role> page = roleService.queryPage(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});
        findByPage(page, jsonConfig);
        return NONE;
    }
    
    private Long[] permissionIds;
    private String menuIds;
    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }
    public void setPermissionIds(Long[] permissionIds) {
        this.permissionIds = permissionIds;
    }
    
    @Action(value = "roleAction_save", results = {
            @Result(location = "/pages/system/role.html", name = "success", type = "redirect")})
    public String save() {
        roleService.save(model,permissionIds,menuIds);
        return SUCCESS;
    }
    
    @Action(value = "roleAction_findAll")
    public String findAll() {
        List<Role> list = roleService.findAll();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"users", "permissions", "menus"});
        list2json(list, jsonConfig);
        return NONE;
    }
}
