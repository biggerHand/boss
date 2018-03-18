package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月15日 下午3:15:05 <br/>       
 */
public interface CourierService {

    Page<Courier> findByPage(Specification<Courier> specification,Pageable pageable);

    void save(Courier model);

    void batchDel(String string);

    Page<Courier> findByPage(Pageable pageable);

}
  
