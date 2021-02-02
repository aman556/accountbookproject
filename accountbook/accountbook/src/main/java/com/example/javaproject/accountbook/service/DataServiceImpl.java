package com.example.javaproject.accountbook.service;

import com.example.javaproject.accountbook.model.Data;
import com.example.javaproject.accountbook.repository.DataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataRepository dataRepository;


    @Override
    public List<Data> getAllEmployes() {
        return dataRepository.findAll();
    }

    @Override
    public void saveData(Data data) {
        this.dataRepository.save(data);
    }

    @Override
    public Data getDataById(long id) {
        Optional<Data> optional = dataRepository.findById(id);
        Data data = null;
        if(optional.isPresent()){
            data =optional.get();

        }
        else {
            throw new RuntimeException(" Employee not found for id :: " + id);
        }
        return data;
    }

    @Override
    public void deleteDataById(long id) {
        this.dataRepository.deleteById(id);
    }
}
