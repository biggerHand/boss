package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月21日 下午8:19:05 <br/>       
 */

public interface FixedAreaService {

    Page<FixedArea> pageQuery(Pageable pageable);

    void associationCourierToFixedArea(Long taketakeTimeId, Long courierId, Long id);

    void associationSubAreaToFixedArea(Long courierId, Long id);

}
  
