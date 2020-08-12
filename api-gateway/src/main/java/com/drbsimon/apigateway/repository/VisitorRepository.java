package com.drbsimon.apigateway.repository;

import com.drbsimon.apigateway.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {

    Optional<Visitor> findByUsername(String username);
    Visitor getByUsername(String username);

    List<Visitor> findAll();

//    @Query("SELECT v.watchListMovieIds FROM Visitor v WHERE v.id=:userid")
//    List<Long> getWatchListByUser(@Param("userid") Long userId);

    @Query("SELECT v FROM Visitor v WHERE v.username=:username")
    Visitor findUserByUsername(@Param("username") String username);

    Visitor getGenderByUsername(String username);
}
