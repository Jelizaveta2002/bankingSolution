package com.example.bankingSolution.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CustomerDao {
    boolean existsCustomerById(@Param("id") Long id);
}
