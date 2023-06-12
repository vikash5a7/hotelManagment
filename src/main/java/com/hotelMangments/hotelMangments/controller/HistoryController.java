package com.hotelMangments.hotelMangments.controller;

import com.hotelMangments.hotelMangments.entity.HistoryEntry;
import com.hotelMangments.hotelMangments.servicesImpl.HistoryEntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class HistoryController {
    private final HistoryEntryService historyEntryService;

    public HistoryController(HistoryEntryService historyEntryService) {
        this.historyEntryService = historyEntryService;
    }

    @GetMapping
    public ResponseEntity<List<HistoryEntry>> getAllHistoryEntries() {
        List<HistoryEntry> historyEntries = historyEntryService.getAllHistoryEntries();
        return ResponseEntity.ok(historyEntries);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistoryEntry> getHistoryEntryById(@PathVariable Long id) {
        HistoryEntry historyEntry = historyEntryService.getHistoryEntryById(id);
        if (historyEntry != null) {
            return ResponseEntity.ok(historyEntry);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<HistoryEntry> createHistoryEntry(@RequestBody HistoryEntry historyEntry) {
        HistoryEntry createdEntry = historyEntryService.createHistoryEntry(historyEntry);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEntry);
    }

    @GetMapping("/recent")
    public ResponseEntity<List<HistoryEntry>> getRecentActivities() {
        List<HistoryEntry> recentActivities = historyEntryService.getRecentActivities(10);
        return ResponseEntity.ok(recentActivities);
    }
}

