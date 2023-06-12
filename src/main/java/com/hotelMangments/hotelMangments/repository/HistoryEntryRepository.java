package com.hotelMangments.hotelMangments.repository;

import com.hotelMangments.hotelMangments.entity.HistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoryEntryRepository extends JpaRepository<HistoryEntry, Long> {

}
