package com.hotelMangments.hotelMangments.controller;

import com.hotelMangments.hotelMangments.entity.Files;
import com.hotelMangments.hotelMangments.response.FileResponse;
import com.hotelMangments.hotelMangments.servicesImpl.FileService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private FileService fileStorageService;

    @PostMapping("/uploadFile")
    @ApiOperation(value = "Upload a file")
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) {
    	Files fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/downloadFile/")
                .path(fileName.getFileName())
                .toUriString();

        return new FileResponse(fileName.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<FileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Files Files = fileStorageService.getFile(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(Files.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + Files.getFileName() + "\"")
                .body(new ByteArrayResource(Files.getData()));
    }
}
