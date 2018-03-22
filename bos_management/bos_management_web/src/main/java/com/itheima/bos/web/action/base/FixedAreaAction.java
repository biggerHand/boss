package com.itheima.bos.web.action.base;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.YamlProcessor.ResolutionMethod;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Customer;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.action.CommonAction;

import net.sf.json.JsonConfig;

/**
 * ClassName:FixedAreaAction <br/>
 * Function: <br/>
 * Date: 2018年3月21日 下午8:14:27 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class FixedAreaAction extends CommonAction<FixedArea> {

    public FixedAreaAction() {
        super(FixedArea.class);
    }

    @Autowired
    private FixedAreaService fixedAreaService;
    
    private Long takeTimeId;
    private Long courierId;
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    
    @Action(value = "fixedAreaAction_associationCourierToFixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String associationCourierToFixedArea() {
        fixedAreaService.associationCourierToFixedArea(takeTimeId,courierId,model.getId());
        return SUCCESS;
    }

    
    private Long[] customerIds;
    public void setCustomerIds(Long[] customerIds) {
        this.customerIds = customerIds;
    }
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea", results = {
            @Result(name = "success", location = "/pages/base/fixed_area.html", type = "redirect")})
    public String assignCustomers2FixedArea() {
        WebClient.create("http://localhost:8180/crm/webService/customerService/assignCustomers2FixedArea")
                .query("customerIds", customerIds).query("fixedAreaId", model.getId())
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).put(null);
        return SUCCESS;
    }

    @Action(value = "fixedAreaAction_pageQuery")
    public String pageQuery() {
        Pageable pageable = new PageRequest(page - 1, rows);
        Page<FixedArea> query = fixedAreaService.pageQuery(pageable);
        List<FixedArea> content = query.getContent();
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[] {"subareas", "couriers"});
        list2json(content, jsonConfig);
        return NONE;
    }

    @Action("fixedAreaAction_findNoassociationSelect")
    public String findNoassociationSelect() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomersUnAssociated")
                .type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }

    @Action("fixedAreaAction_findAssociationSelect")
    public String findAssociationSelect() {
        List<Customer> list = (List<Customer>) WebClient
                .create("http://localhost:8180/crm/webService/customerService/findCustomersAssociated2FixedArea")
                .query("fixedAreaId", model.getId()).type(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
}
