package com.itheima.bos.web.action.system;

import java.io.IOException;
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

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;


/**  
 * ClassName:Menu <br/>  
 * Function:  <br/>  
 * Date:     2018年3月29日 下午3:16:13 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class MenuAction extends CommonAction<Menu> {

    public MenuAction() {
        super(Menu.class);  
    }
    
    @Autowired
    private MenuService menuService;
    
    @Action(value = "menuAction_save",
            results = {@Result(name = "success", location = "/pages/system/menu.html", type = "redirect")})
    public String save(){
        menuService.save(model);
        return SUCCESS;
    }
    
    @Action(value = "menuAction_findParentMenu",
            results = {@Result(name = "success", location = "/pages/system/menu.html", type = "redirect")})
    public String findParentMenu(){
        List<Menu> list = menuService.findParentMenu();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"childrenMenus","parentMenu","roles"});
        list2json(list, jsonConfig);
        return NONE;
    }
    
    @Action(value = "menuAction_findByPage")
    public String findByPage(){
        Pageable pageable = new PageRequest(Integer.parseInt(model.getPage())-1, rows);
        Page<Menu> page = menuService.findAll(pageable);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{"childrenMenus","parentMenu","roles"});
        findByPage(page, jsonConfig);
        return NONE;
    }
}
  
