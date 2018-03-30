package com.itheima.bos.service.take_delivery.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.hibernate.boot.jaxb.internal.stax.FilteringXMLEventReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ctc.wstx.util.WordResolver;
import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.OrderRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.take_delivery.WorkbillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;

/**  
 * ClassName:OrderServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月23日 下午8:36:44 <br/>       
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkbillRepository workbillRepository;
    @Override
    public void saveAndFindCourier(Order order) {
        
        Area sendArea = order.getSendArea();
        if(sendArea!=null){
            Area area = areaRepository.findByrecArea(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
            order.setSendArea(area);
        }
        Area recArea = order.getRecArea();
        if(recArea!=null){
            Area area = areaRepository.findByrecArea(recArea.getProvince(),recArea.getCity(),recArea.getDistrict());
            order.setRecArea(area);
        }
        String orderNum = UUID.randomUUID().toString().replace("-", "");
        order.setOrderNum(orderNum);
        order.setOrderTime(new Date());
        orderRepository.save(order);
        
        String sendAddress = order.getSendAddress();
        if(StringUtils.isNotEmpty(sendAddress)){
            String fixedAreaId = WebClient.create("http://localhost:8180/crm/webService/customerService/findFixedAreaIdByAddress")
            .accept(MediaType.APPLICATION_JSON)
            .type(MediaType.APPLICATION_JSON)
            .query("address", sendAddress)
            .get(String.class);
            if(StringUtils.isNotEmpty(fixedAreaId)){
                FixedArea fixedArea = fixedAreaRepository.findOne(Long.parseLong(fixedAreaId));
                Set<Courier> couriers = fixedArea.getCouriers();
                if(!couriers.isEmpty()){
                    Iterator<Courier> iterator = couriers.iterator();
                    Courier courier = iterator.next();
                    order.setCourier(courier);
                    
                    WorkBill workBill = new WorkBill();
                    workBill.setAttachbilltimes(0);
                    workBill.setBuildtime(new Date());
                    workBill.setCourier(courier);
                    workBill.setOrder(order);
                    workBill.setPickstate("新单");
                    workBill.setRemark(order.getRemark());
                    workBill.setSmsNumber("111");
                    workBill.setType("新");
                    
                    workbillRepository.save(workBill);
                    order.setOrderType("自动分单");
                    return;
                }
            }else{
                Area sendArea2 = order.getSendArea();
                if(sendArea2!=null){
                    Set<SubArea> subareas = sendArea2.getSubareas();
                    if(!subareas.isEmpty()){
                        for (SubArea subArea : subareas) {
                            String keyWords = subArea.getKeyWords();
                            String assistKeyWords = subArea.getAssistKeyWords();
                            if (sendAddress.contains(keyWords)
                                    || sendAddress.contains(assistKeyWords)) {
                                FixedArea fixedArea2 = subArea.getFixedArea();

                                if (fixedArea2 != null) {
                                    // 查询快递员
                                    Set<Courier> couriers =
                                            fixedArea2.getCouriers();
                                    if (!couriers.isEmpty()) {
                                        Iterator<Courier> iterator =
                                                couriers.iterator();
                                        Courier courier = iterator.next();
                                        // 指派快递员
                                        order.setCourier(courier);
                                        // 生成工单
                                        WorkBill workBill = new WorkBill();
                                        workBill.setAttachbilltimes(0);
                                        workBill.setBuildtime(new Date());
                                        workBill.setCourier(courier);
                                        workBill.setOrder(order);
                                        workBill.setPickstate("新单");
                                        workBill.setRemark(order.getRemark());
                                        workBill.setSmsNumber("111");
                                        workBill.setType("新");

                                        workbillRepository.save(workBill);
                                        // 发送短信,推送一个通知
                                        // 中断代码的执行
                                        order.setOrderType("自动分单");
                                        return;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        order.setOrderType("人工分单");
    }
}
  
