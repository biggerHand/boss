package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.ui.Model;

import com.itheima.bos.domain.base.Courier;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CommonAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午4:19:05 <br/>       
 */
public class CommonAction<T> extends ActionSupport implements ModelDriven<T> {
    private Class<T> clazz;
    protected T model;
    @Override
    public T getModel() {
        try {
            model = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return model;
    }
    public CommonAction(Class<T> clazz) {
        this.clazz = clazz;
    }
    protected int page;
    protected int rows;

    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
    public void findByPage(Page<Courier> findByPage,String jsonCf){
        Map<String, Object> map = new HashMap<>();
        map.put("total", findByPage.getTotalElements());
        map.put("rows", findByPage.getContent());

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", jsonCf});

        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
  
