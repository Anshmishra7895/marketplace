package com.marketplace.product_service.repository;

import com.marketplace.product_service.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    /* SELECT * FROM Product where id > 5 ORDER BY id ASC LIMIT 5 */
    @Query("SELECT p FROM Product p "+
            "WHERE (:cursor IS NULL OR p.id > :cursor) "+
            "ORDER BY p.id ASC")
    List<Product> fetchNextPage(@Param(value = "cursor") Long cursor, Pageable pageable);

    Window<Product> fetchNextPageWithWindow(ScrollPosition position, Pageable pageable);

    Page<Product> findByPriceBetween(Double min, Double max, Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p "+
    "WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) "+
            "OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Product> searchProducts(@Param("keyword") String keyword, Pageable pageable);


    @Query("SELECT p FROM Product p "+
            "WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER (CONCAT('%', :keyword, '%'))) "+
            "AND(:categoryId IS NULL OR p.category.id = :categoryId)"+
            "AND(p.price BETWEEN :minPrice AND :maxPrice)")
    Page<Product> advanceFilter(@Param("keyword") String keyword,
                                @Param("categoryId") Long categoryId,
                                @Param("minPrice") Double minPrice,
                                @Param("maxPrice") Double maxPrice,
                                Pageable pageable);

}
