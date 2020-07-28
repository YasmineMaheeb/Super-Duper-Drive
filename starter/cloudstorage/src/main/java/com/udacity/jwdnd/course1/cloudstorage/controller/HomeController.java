package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Delete;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String addOrUpdateNote(Authentication auth, Note note, Model model){
        String username = auth.getName();
        User user = userMapper.getUser(username);
        int userid = user.getUserid();
        if(note.getNoteid()!=null){
            noteMapper.updateNote(note.getNoteid(), note.getNotetitle(), note.getNotedescription());
        }
        else {
            note.setUserid(userid);
            noteMapper.insert(note);
        }
        ArrayList<Note> notes = noteMapper.getNotes(userid);
        model.addAttribute("notes", notes);
        return "home";
    }

    @PostMapping("/deletenote")
    public String deleteNote(Authentication auth, Note note, Model model){
        String username = auth.getName();
        User user = userMapper.getUser(username);
        int userid = user.getUserid();
        System.out.println(note);
        noteMapper.delete(note.getNoteid());
        ArrayList<Note> notes = noteMapper.getNotes(userid);
        model.addAttribute("notes", notes);
        return "home";
    }
}