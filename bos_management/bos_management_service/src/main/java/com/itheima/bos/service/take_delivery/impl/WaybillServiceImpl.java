package com.itheima.bos.service.take_delivery.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.take_delivery.WaybillRepository;
import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WaybillService;

/**  
 * ClassName:WaybillServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月26日 下午4:02:12 <br/>       
 */
@Transactional
@Service
public class WaybillServiceImpl implements WaybillService {

    @Autowired
    private WaybillRepository waybillRepository;

    @Override
    public void save(WayBill wayBill) {
        waybillRepository.save(wayBill);
    }
    
}
  
