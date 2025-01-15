package com.doa.doajewelry.repositories;

import com.doa.doajewelry.entities.Jewelry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface JewelryRepository extends JpaRepository<Jewelry, Long> {

    @Query("SELECT j FROM Jewelry j WHERE TYPE(j) = :clazz")
    <T extends Jewelry> List<T> findAllByType(@Param("clazz") Class<T> clazz);

}
