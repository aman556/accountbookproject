package com.example.javaproject.accountbook.repository;

import com.example.javaproject.accountbook.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataRepository extends JpaRepository<Data,Long> {
    @Query(value = "select b from Data b where b.companyName=:my_name")
    List<Data> findByACompanyName(String my_name);
}
