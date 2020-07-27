package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class HomeController {
    private UserMapper userMapper;
    private NoteMapper noteMapper;

    public HomeController(UserMapper userMapper, NoteMapper noteMapper) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
    }


    @GetMapping("/home")
    public String homeView(Authentication auth, Model model) {
        String username = auth.getName();
        int userid = userMapper.getUser(username).getUserid();
        Note n = new Note(1,1,"hi","there");
        noteMapper.insert(n);
        ArrayList<Note> notes = noteMapper.getNotes(userid);
        model.addAttribute("notes", notes);
        return "home";
    }

    //TODO add logic
//    @PostMapping("/file-upload")
//    public String uploadFile(@RequestParam("fileUpload") MultipartFile file, Model model) throws IOException {
//        InputStream fis = file.getInputStream();
//        return null;
//    }

    @PostMapping("/notes")
    public String addNote(Authentication auth, Note note, Model model){
        System.out.println("showNote");
        String username = auth.getName();
        User user = userMapper.getUser(username);
        int userid = user.getUserid();
        note.setUserid(userid);
        noteMapper.insert(note);
        System.out.println(userid+" user");
        ArrayList<Note> notes = noteMapper.getNotes(userid);
        model.addAttribute("notes", notes);
        System.out.println(notes);
        return "home";
    }
}