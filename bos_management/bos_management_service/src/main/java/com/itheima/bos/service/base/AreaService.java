package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午2:56:22 <br/>       
 */
public interface AreaService {

    void save(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

    List<Area> findByQ(String q);

}
  
