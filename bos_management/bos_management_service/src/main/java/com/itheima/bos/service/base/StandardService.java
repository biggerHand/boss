package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月14日 下午3:40:52 <br/>       
 */
public interface StandardService {

    void save(Standard standard);

    Page<Standard> pageQuery(Pageable pageable);

}
  
