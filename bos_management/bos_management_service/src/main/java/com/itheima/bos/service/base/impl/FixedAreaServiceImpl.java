package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:19:15 <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }

    @Override
    public void associationCourierToFixedArea(Long taketakeTimeId, Long courierId, Long id) {
        FixedArea fixedArea = fixedAreaRepository.findOne(id);
        Courier courier = courierRepository.findOne(courierId);
        TakeTime takeTime = takeTimeRepository.findOne(taketakeTimeId);
        courier.setTakeTime(takeTime);
        fixedArea.getCouriers().add(courier);
    }
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Override
    public void associationSubAreaToFixedArea(Long courierId, Long id) {
          SubArea subArea = subAreaRepository.findOne(courierId);
          FixedArea fixedArea = fixedAreaRepository.findOne(id);
          subArea.setFixedArea(fixedArea);
        
    }
}
  
