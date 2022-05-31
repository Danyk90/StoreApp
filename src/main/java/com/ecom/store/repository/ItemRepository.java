package com.ecom.store.repository;

import com.ecom.store.models.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Items, Long> {

    public List<Items> findItemsByActiveIsTrue();
}