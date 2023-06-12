package com.hotelMangments.hotelMangments.servicesImpl;

import com.hotelMangments.hotelMangments.entity.Files;
import com.hotelMangments.hotelMangments.exception.FileNotFoundException;
import com.hotelMangments.hotelMangments.exception.FileStorageException;
import com.hotelMangments.hotelMangments.repository.FileRepository;
import com.hotelMangments.hotelMangments.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Service
public class FileService {

    @Autowired
    private FileRepository dbFileRepository;

    public Files storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            fileName= Utils.generateRandomString(30)+new Date()+fileName;
            Files dbFile = new Files(fileName, file.getContentType(), file.getBytes());
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Files getFile(String fileName) {
        return dbFileRepository.findByFileName(fileName)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileName));
    }
}
