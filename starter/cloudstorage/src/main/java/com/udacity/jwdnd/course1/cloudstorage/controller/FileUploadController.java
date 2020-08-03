package com.udacity.jwdnd.course1.cloudstorage.controller;


import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.stream.Collectors;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileSystemStorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {

    private final FileSystemStorageService storageService;
    private UserService userService;

    @Autowired
    public FileUploadController(FileSystemStorageService storageService, UserService userService) {
        this.storageService = storageService;
        this.userService = userService;
    }

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

    @GetMapping("/files/{fileId:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable int fileId) throws SQLException {

        File file = storageService.load(fileId);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        byte[] data = file.getFiledata();//file.getFiledata().getBytes(1, (int)file.getFiledata().length());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(data));
    }

    @PostMapping("/files")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   Model model, Authentication auth) throws IOException, SQLException {
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        if (file != null) {
            storageService.databaseStore(userid, file);
            model.addAttribute("message",
                    "You successfully uploaded " + file.getOriginalFilename() + "!");
        } else {

            model.addAttribute("message",
                    "You did not choose a file!");
        }
        return "redirect:/home";
    }

    @PostMapping("/deletefile")
    public String deleteFile(File file, Model model, Authentication auth){
        storageService.delete(file.getFileId());
        return "redirect:/home";
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}