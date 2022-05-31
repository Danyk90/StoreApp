package com.ecom.store.repository;

import com.ecom.store.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface OrderRepository extends JpaRepository<Orders,Long> {


}
