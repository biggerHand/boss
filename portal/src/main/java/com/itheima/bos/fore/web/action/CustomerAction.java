package com.itheima.bos.fore.web.action;

import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
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
    
    @Autowired
    private JmsTemplate jmsTemplate;
    
    @Action("customerAction_sendSMS")
    public String sendSMS() throws ClientException{
        final String random = RandomStringUtils.randomNumeric(6);
        System.out.println(random);
        ServletActionContext.getRequest().getSession().setAttribute("random", random);
        jmsTemplate.send("sms",new MessageCreator() {
            
            @Override
            public Message createMessage(Session session) throws JMSException {
                
                MapMessage message = session.createMapMessage();
                message.setString("tel", model.getTelephone());
                message.setString("code", random);
                return message;
            }
        });
        //SmsUtils.sendSms(model.getTelephone(), code);
        return NONE;
    }
    private String checkcode;
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    
    @Autowired
    RedisTemplate<String, String> redisTemplate;
    
    @Action(value = "customerAction_regist",results = {
            @Result(name = "success", location = "/index.html", type = "redirect"),
            @Result(name = "error", location = "/signup.html")})
    public String regist(){
        String random = (String) ServletActionContext.getRequest().getSession().getAttribute("random");
        if(checkcode.equals(random)){
            //MailUtils.sendMail(subject, content, to);
            WebClient.create("http://localhost:8180/crm/webService/customerService/save")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .post(model);
            String activeCode = RandomStringUtils.randomNumeric(32);
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode,1,TimeUnit.DAYS);
            String emailBody =
                    "感谢您注册本网站的帐号，请在24小时之内点击<a href='http://localhost:8280/portal/customerAction_active.action?activeCode="
                            + activeCode + "&telephone=" + model.getTelephone()
                            + "'>本链接</a>激活您的帐号";
            MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);
            return SUCCESS;
        }else{
            return ERROR;
        }
    }
}
  
