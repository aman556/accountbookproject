package com.example.javaproject.accountbook.repository;


import com.example.javaproject.accountbook.model.Company;
import com.example.javaproject.accountbook.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("SELECT u FROM Company u WHERE u.companyName = ?1")
    Company findBy(String name);

    @Query(value = "select List<Data> from Company b where b.companyName = company", nativeQuery = true)
    public List<Data> getdatasByCompany(String company);
}