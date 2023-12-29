package com.example.DoAnJava.repository;

import com.example.DoAnJava.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//thao tác tới csdl
@Repository
public interface ICategoryRepository extends JpaRepository<Category,Long> {
}
