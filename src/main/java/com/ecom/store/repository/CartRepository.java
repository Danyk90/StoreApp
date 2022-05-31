package com.ecom.store.repository;

import com.ecom.store.models.Carts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Carts,Long> {

    public Optional<Carts> getByUserId(Long id);
}
