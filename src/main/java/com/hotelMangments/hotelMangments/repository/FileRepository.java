package com.hotelMangments.hotelMangments.repository;

import com.hotelMangments.hotelMangments.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<Files, String> {

    Optional<Files> findByFileName(String fileName);
}
