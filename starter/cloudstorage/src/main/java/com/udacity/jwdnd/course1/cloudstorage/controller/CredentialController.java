package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class CredentialController {
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credentials")
    public String addOrUpdateCredential(Authentication auth, Credential credential, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        if(credential.getCredentialid()!=null){
            credentialService.updateCredential(credential.getCredentialid(), credential.getUrl(), credential.getUsername(), credential.getPassword());
        }
        else {
            credential.setUserid(userid);
            credentialService.addCredential(credential);
        }
        ArrayList<Credential> crds = credentialService.getCredentials(userid);
        model.addAttribute("credentials", crds);
        return "home";
    }

    @PostMapping("/deletecredential")
    public String deleteNote(Authentication auth, Credential credential, Model model){
        String username = auth.getName();
        User user = userService.getUser(username);
        int userid = user.getUserid();
        credentialService.deleteCredential(credential.getCredentialid());
        ArrayList<Credential> crds = credentialService.getCredentials(userid);
        model.addAttribute("credentials", crds);
        return "home";
    }
}