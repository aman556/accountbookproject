package com.example.javaproject.accountbook.repository;

import com.example.javaproject.accountbook.model.Data;
import com.example.javaproject.accountbook.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<Data,Long> {

}
