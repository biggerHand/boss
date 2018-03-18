package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
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

import com.alibaba.fastjson.JSONArray;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:25:46 <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
    
    private Standard model;
    @Override
    public Standard getModel() {
        if(model==null){
            model=new Standard();
        }
        return model;
    }
    @Autowired
    private StandardService standardService;
    @Action(value="standardAction_save",results={
            @Result(location="/pages/base/standard.html",name="success",type="redirect")})
    public String save(){
        standardService.save(model);
        return SUCCESS;
    }
    
    private int page;
    private int rows;
    public void setPage(int page) {
        this.page = page;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    
    @Action(value="standardAction_pageQuery")
    public String pageQuery() throws IOException{
        Pageable pageable = new PageRequest(page-1, rows);
        Page<Standard> query = standardService.pageQuery(pageable);
        List<Standard> content = query.getContent();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", query.getTotalElements());
        map.put("rows", content);
        String json = JSONObject.fromObject(map).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
    
    @Action(value="standardAction_findAll")
    public String findAll() throws IOException{
        Page<Standard> query = standardService.pageQuery(null);
        List<Standard> list = query.getContent();
        String json = JSONArray.toJSON(list).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
        return NONE;
    }
    
}
  
