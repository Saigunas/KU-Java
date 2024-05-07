package com.example.SaigunasBirbalas.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    public void store(MultipartFile file, String fileName){
        try{
            Path target= (Paths.get("uploads")).resolve(fileName).normalize().toAbsolutePath();
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        }catch(IOException e){
            System.out.println("Išsaugant įvyko klaida");
            e.printStackTrace();
        }
    }

    public void deleteFile(String fileName) {
        try {
            Path filePath = Paths.get("uploads").resolve(fileName).normalize();
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Resource loadFile(String fileName){
        try{
            Path target= (Paths.get("uploads")).resolve(fileName).normalize();
            Resource r=new UrlResource(target.toUri());
            return r;
        }catch(IOException e){
            System.out.println("Įvyko klaida");
            e.printStackTrace();
        }
        return null;

    }
}
