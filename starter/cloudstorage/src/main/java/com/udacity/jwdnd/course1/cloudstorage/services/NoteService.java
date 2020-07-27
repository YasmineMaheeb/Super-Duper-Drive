package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@Service
public class NoteService {
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public void addNote(Note note){

    }

    public void deleteNote(int noteId){

    }

    public void updateNote(int noteId, String title, String description){

    }

    public Note getNote(int noteId){
        return null;
    }

    public ArrayList<Note> getNotes(int userId){
        return null;
    }

}
