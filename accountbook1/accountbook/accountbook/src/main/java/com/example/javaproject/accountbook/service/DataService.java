package com.example.javaproject.accountbook.service;

import com.example.javaproject.accountbook.model.Data;

import java.util.List;

public interface DataService {
    List<Data> getAllEmployes();
    void saveData(Data data);
    Data getDataById(long id);
    void deleteDataById(long id);

}
