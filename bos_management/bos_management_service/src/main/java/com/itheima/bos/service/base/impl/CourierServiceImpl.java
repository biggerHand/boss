package com.itheima.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午3:15:17 <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements com.itheima.bos.service.base.CourierService {
    @Autowired
    private CourierRepository courierRepository;

    @Override
    public Page<Courier> findByPage(Specification<Courier> specification,Pageable pageable) {
        return courierRepository.findAll(specification,pageable);
    }

    @Override
    public void save(Courier model) {
        courierRepository.save(model);
    }

    @Override
    public void batchDel(String string) {
        courierRepository.updateById(Long.parseLong(string));
    }

    @Override
    public Page<Courier> findByPage(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    
}
  
