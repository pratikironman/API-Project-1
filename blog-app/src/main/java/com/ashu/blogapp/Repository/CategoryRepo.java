package com.ashu.blogapp.Repository;

import com.ashu.blogapp.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {


}
