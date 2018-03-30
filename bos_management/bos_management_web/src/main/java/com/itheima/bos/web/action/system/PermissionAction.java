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

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:PermissionAction <br/>
 * Function: <br/>
 * Date: 2018年3月29日 下午8:10:18 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class PermissionAction extends CommonAction<Permission> {

    public PermissionAction() {
        super(Permission.class);
    }

    @Autowired
    private PermissionService permissionService;

    @Action(value = "permissionAction_queryPage")
    public String queryPage() {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Permission> page = permissionService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        findByPage(page, jsonConfig);
        return NONE;
    }

    @Action(value = "permissionAction_save",
            results = {@Result(location = "/pages/system/permission.html", name = "success",
                    type = "redirect")})
    public String save() {
        permissionService.save(model);
        return SUCCESS;
    }
    
    @Action(value = "permissionAction_findAll")
    public String findAll() {
        List<Permission> list = permissionService.findAll();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"roles"});
        list2json(list, jsonConfig);
        return NONE;
    }
}
