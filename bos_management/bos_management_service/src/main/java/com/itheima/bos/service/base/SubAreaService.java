package com.itheima.bos.service.base;

import java.util.ArrayList;
import java.util.List;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubAreaService <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午10:51:51 <br/>       
 */
public interface SubAreaService {

    void save(ArrayList<SubArea> list);

    List<SubArea> findAll();

    void save(SubArea model);

}
  
