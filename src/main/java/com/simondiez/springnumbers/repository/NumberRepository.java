package com.simondiez.springnumbers.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simondiez.springnumbers.entity.NaturalNumber;

@Repository
public interface NumberRepository extends JpaRepository<NaturalNumber, Long> {

}
