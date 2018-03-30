package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.take_delivery.Order;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     2018年3月16日 下午2:57:34 <br/>       
 */
public interface AreaRepository extends JpaRepository<Area, Long> {
    
    @Query("from Area where province like ?1 or  city like ?1  or  district like ?1  or  postcode like ?1  or  citycode like ?1  or  shortcode like ?1")
    List<Area> findByQ(String q);
    @Query("from Area where province=? and city=? and district=?")
    Area findByrecArea(String province,String city,String district);
    
}
  
