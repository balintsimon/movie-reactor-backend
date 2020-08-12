package com.drbsimon.apigateway.repository;

import com.drbsimon.apigateway.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByUsername(String username);

    Visitor getById(Long id);

    List<Visitor> findAll();

    Visitor getGenderByUsername(String username);
}
