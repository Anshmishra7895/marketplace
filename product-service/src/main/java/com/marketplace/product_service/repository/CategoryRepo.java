package com.marketplace.product_service.repository;

import com.marketplace.product_service.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {

    List<Category> findByParentId(Long parentId);

    boolean existsByName(String name);

}
