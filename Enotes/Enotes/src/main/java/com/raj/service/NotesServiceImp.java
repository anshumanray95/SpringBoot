package com.raj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.raj.entity.Notes;
import com.raj.entity.User;
import com.raj.repository.NotesRepository;

@Service
public class NotesServiceImp implements  NotesService{
    
	@Autowired
	private NotesRepository notesRepository;
	
	
	@Override
	public Notes saveNotes(Notes notes) {
		// TODO Auto-generated method stub
		
		return notesRepository.save(notes);
	}

	@Override
	public Notes getNotesById(int id) {
		// TODO Auto-generated method stub
		return notesRepository.findById(id).get();
	}

	@Override
	public List<Notes> getNotesByUser(User user) {
		// TODO Auto-generated method stub
		return notesRepository.findByUser(user);
	}

	@Override
	public Notes updateNotes(Notes notes) {
		// TODO Auto-generated method stub
		return notesRepository.save(notes);
	}

	@Override
	public boolean deleteNotes(int id) {
		// TODO Auto-generated method stub
		Notes notes=notesRepository.findById(id).get();
		if(notes!=null) {
			notesRepository.delete(notes);
			return true;
		}
		
		return false;
	}

}
