package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.itheima.bos.domain.base.Courier;

/**
 * ClassName:CourierRepository <br/>
 * Function: <br/>
 * Date: 2018年3月15日 下午3:16:08 <br/>
 */
public interface CourierRepository
        extends JpaRepository<Courier, Long>, JpaSpecificationExecutor<Courier> {

    @Modifying
    @Query("update Courier set deltag=1 where id =?")
    void updateById(Long id);

    List<Courier> findByDeltagIsNull();
}
