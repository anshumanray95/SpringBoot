package com.raj.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.raj.entity.Notes;
import com.raj.entity.User;

public interface NotesRepository extends JpaRepository<Notes , Integer>{

	public List<Notes> findByUser(User user);
	  
}
