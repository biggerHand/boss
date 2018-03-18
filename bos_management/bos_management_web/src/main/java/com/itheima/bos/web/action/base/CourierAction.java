package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

/**
 * ClassName:CourierAction <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午3:02:17 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
    private Courier model;
    @Autowired
    private CourierService courierService;

    @Override
    public Courier getModel() {
        if (model == null) {
            model = new Courier();
        }
        return model;
    }

    @Action(value = "courierAction_save", results = {
            @Result(name = "success", location = "/pages/base/courier.html", type = "redirect")})
    public String save() {
        courierService.save(model);
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

    @Action(value = "courierAction_findByPage")
    public String findByPage() {
       
        Specification<Courier> specification = new Specification<Courier>() {
            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String company = model.getCompany();
                String courierNum = model.getCourierNum();
                Standard standard = model.getStandard();
                String type = model.getType();
                List<Predicate> list = new ArrayList<>();
                Predicate p1;
                if (!StringUtils.isEmpty(company)) {
                    p1 = cb.equal(root.get("company").as(String.class), company);
                    list.add(p1);
                }
                if (!StringUtils.isEmpty(courierNum)) {
                    p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                if (!StringUtils.isEmpty(type)) {
                    p1 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p1);
                }
                if(standard!=null){
                    if (!StringUtils.isEmpty(standard.getName())) {
                        p1 = cb.equal(root.get("standard.getName()").as(String.class), standard.getName());
                        list.add(p1);
                    }
                }
                if(list.size()==0){
                    return null;
                }
                Predicate[] arr = new Predicate[list.size()];
                list.toArray(arr);
                Predicate predicate = cb.and(arr);
                return predicate;
            }
        };
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<Courier> findByPage = courierService.findByPage(specification,pageable);
        Map<String, Object> map = new HashMap<>();
        map.put("total", findByPage.getTotalElements());
        map.put("rows", findByPage.getContent());

        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"fixedAreas", "takeTime"});

        String json = JSONObject.fromObject(map, jsonConfig).toString();
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    private String ids;

    public void setIds(String ids) {
        this.ids = ids;
    }

    @Action(value = "courierAction_batchDel", results = {
            @Result(name = "success", type = "redirect", location = "/pages/base/courier.html")})
    public String batchDel() {
        String[] split = ids.split(",");
        for (int i = 0; i < split.length; i++) {
            courierService.batchDel(split[i]);
        }
        return SUCCESS;
    }
}
