package com.silalahi.valentinus.ci.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.silalahi.valentinus.ci.entity.Product;

public interface ProductDao extends PagingAndSortingRepository<Product, String> { }
