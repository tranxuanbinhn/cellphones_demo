package com.cellphones10.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenricService <T>{
    T save(T t);
    List<T> findAll(Pageable pageable);
    boolean delete(List<Long> list);



}
