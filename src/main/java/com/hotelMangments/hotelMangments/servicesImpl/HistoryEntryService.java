package com.hotelMangments.hotelMangments.servicesImpl;

import com.hotelMangments.hotelMangments.entity.HistoryEntry;
import com.hotelMangments.hotelMangments.exception.NotFoundException;
import com.hotelMangments.hotelMangments.repository.HistoryEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryEntryService {
    private final HistoryEntryRepository historyEntryRepository;

    public HistoryEntryService(HistoryEntryRepository historyEntryRepository) {
        this.historyEntryRepository = historyEntryRepository;
    }


    public List<HistoryEntry> getAllHistoryEntries() {
        return historyEntryRepository.findAll();
    }

    public HistoryEntry getHistoryEntryById(Long id) {
        return historyEntryRepository.findById(id).orElseThrow(() -> new NotFoundException("History entry not found with id: " + id));
    }

    public HistoryEntry createHistoryEntry(HistoryEntry historyEntry) {
        return historyEntryRepository.save(historyEntry);
    }

    public void saveHistory(String event, String eventDetails){
        HistoryEntry historyEntry = new HistoryEntry(event,eventDetails);
        historyEntryRepository.save(historyEntry);
    }

    public List<HistoryEntry> getRecentActivities(int count) {
        Sort sort = Sort.by(Sort.Direction.DESC, "timestamp");
        Pageable pageable = PageRequest.of(0, count, sort);
        Page<HistoryEntry> historyEntries = historyEntryRepository.findAll(pageable);
        return historyEntries.getContent();
    }

    // Other methods for managing history entries, such as filtering, searching, etc.
}