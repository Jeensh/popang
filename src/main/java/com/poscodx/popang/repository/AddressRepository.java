package com.poscodx.popang.repository;

import com.poscodx.popang.domain.Address;
import com.poscodx.popang.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
