package com.mohamednasser.sadaqa.repository;

import com.mohamednasser.sadaqa.model.Cause;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CauseRepository extends JpaRepository<Cause, Long> {

    @Query("SELECT a FROM Cause a JOIN a.owner u WHERE u.handle = :handle")
    Page<Cause> findAllByHandle(String handle, Pageable pageable);
}
