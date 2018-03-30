package com.itheima.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * ClassName:OrderAction <br/>
 * Function: <br/>
 * Date: 2018年3月23日 下午8:11:40 <br/>
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order> {

    private Order model;

    @Override
    public Order getModel() {
        if (model == null) {
            model = new Order();
        }
        return model;
    }
    private String recAreaInfo;
    private String SendAreaInfo;
    public void setRecAreaInfo(String recAreaInfo) {
        this.recAreaInfo = recAreaInfo;
    }
    public void setSendAreaInfo(String sendAreaInfo) {
        SendAreaInfo = sendAreaInfo;
    }
    @Action(value = "orderAction_add",
            results = {@Result(location = "/index.html", name = "success", type = "redirect")})
    public String add() {
        if(StringUtils.isNotEmpty(recAreaInfo)){
            String[] recAreaArr = recAreaInfo.split("/");
            Area recArea = new Area();
            String province = recAreaArr[0];
            recArea.setProvince(province.substring(0, province.length()-1));
            String city = recAreaArr[1];
            recArea.setCity(city.substring(0, city.length()-1));
            String district = recAreaArr[2];
            recArea.setDistrict(district.substring(0, district.length()-1));
            model.setRecArea(recArea);
        }
        if(StringUtils.isNotEmpty(SendAreaInfo)){
            String[] sendAreaArr = SendAreaInfo.split("/");
            Area sendArea = new Area();
            String province = sendAreaArr[0];
            sendArea.setProvince(province.substring(0, province.length()-1));
            String city = sendAreaArr[1];
            sendArea.setCity(city.substring(0, city.length()-1));
            String district = sendAreaArr[2];
            sendArea.setDistrict(district.substring(0, district.length()-1));
            model.setSendArea(sendArea);
        }
        
        WebClient.create("http://localhost:8080/bos_management_web/webService/orderService/saveAndFindCourier")
                .accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(model);
        return SUCCESS;
    }

}
