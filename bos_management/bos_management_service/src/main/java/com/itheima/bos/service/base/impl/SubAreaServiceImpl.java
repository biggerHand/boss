package com.itheima.bos.service.base.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     2018年3月22日 上午10:52:06 <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    
    @Autowired
    private SubAreaRepository subAreaRepository;
    
    @Override
    public void save(ArrayList<SubArea> list) {
          
    }

    @Override
    public List<SubArea> findAll() {
        return subAreaRepository.findAll();
    }

    @Override
    public void save(SubArea model) {
          subAreaRepository.save(model);
    }

}
  
