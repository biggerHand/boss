package com.itheima.bos.fore.web.action;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.crm.domain.Customer;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 下午4:26:05 <br/>       
 */
@Controller
@Namespace("/")
@ParentPackage("struts-default")
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer> {
    
    private Customer model;
    @Override
    public Customer getModel() {
        if(model==null){
            model=new Customer();
        }
        return model;
    }
    
    @Action("customerAction_sendSMS")
    public String sendSMS() throws ClientException{
        String random = RandomStringUtils.randomNumeric(6);
        System.out.println(random);
        ServletActionContext.getRequest().getSession().setAttribute("random", random);
        //SmsUtils.sendSms(model.getTelephone(), code);
        return NONE;
    }
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Action(value = "customerAction_regist",results = {
            @Result(name = "success", location = "/index.html", type = "redirect"),
            @Result(name = "error", location = "/signup.html")})
    public String save(){
        String random = (String) ServletActionContext.getRequest().getSession().getAttribute("random");
        if(checkcode.equals(random)){
            //MailUtils.sendMail(subject, content, to);
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .post(model);
            return SUCCESS;
        }else{
            return ERROR;
        }
    }
}
  
