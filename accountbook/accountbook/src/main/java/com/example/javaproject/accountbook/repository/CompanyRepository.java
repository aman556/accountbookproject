package com.example.javaproject.accountbook.repository;


import com.example.javaproject.accountbook.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
    @Query("SELECT u FROM Company u WHERE u.companyName = ?1")
    Company findBy(String name);
}