package com.raj.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.raj.entity.Notes;
import com.raj.entity.User;
import com.raj.repository.UserRepository;
import com.raj.service.NotesService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private NotesService notesService;
	
	 @ModelAttribute
	 public User getUser(HttpSession session, Model m) {
	        //
	       
		 User user = (User) session.getAttribute("user");
		 m.addAttribute("user", user);
		 return user;
	        } 
	    
   
	
	@GetMapping("/addNote")
	public String addNote() {
		return ("addNote");
	}
    @GetMapping("/viewNote")
	public String viewNote(Model m ,HttpSession session ) {
    	User user=getUser(session,m);
    	List<Notes> notes = notesService.getNotesByUser(user);
    	m.addAttribute("notesList",notes);
		return ("viewNote");
	}
    
    @GetMapping("/editNote/{id}")
	public String editNote(@PathVariable int id , Model m) {
    	Notes notes=notesService.getNotesById(id);
    	m.addAttribute("n",notes);
		return ("editNote");
	}
    
    @PostMapping("/saveNotes")
    public String saveNotes(@ModelAttribute Notes notes ,HttpSession session , Model m) {
    	notes.setDate(LocalDate.now());
    	notes.setUser(getUser(session,m));
    	
    	Notes saveNotes=notesService.saveNotes(notes);
    	 if (saveNotes != null) {
             session.setAttribute("msg", "Note save successfully.");
              // Redirect to the sign-in page
         } else {
             session.setAttribute("msg", "Something went wrong .");
               // Redirect back to the register page in case of an error
         }
    	 return "redirect:/user/viewNote";
    }
    
    @PostMapping("/updateNotes")
    public String updateNotes(@ModelAttribute Notes notes ,HttpSession session , Model m) {
    	notes.setDate(LocalDate.now());
    	notes.setUser(getUser(session,m));
    	
    	Notes saveNotes=notesService.saveNotes(notes);
    	 if (saveNotes != null) {
             session.setAttribute("msg", "Note update successfully.");
              // Redirect to the sign-in page
         } else {
             session.setAttribute("msg", "Something went wrong .");
               // Redirect back to the register page in case of an error
         }
    	 return "redirect:/user/viewNote";
    }
    
    @GetMapping("/deleteNote/{id}")
   	public String deleteNote(@PathVariable int id ,HttpSession session ) {
       	boolean f=notesService.deleteNotes(id);
       	
       	if (f) {
            session.setAttribute("msg", "Delete successfully.");
             // Redirect to the sign-in page
        } else {
            session.setAttribute("msg", "Something went wrong .");
              // Redirect back to the register page in case of an error
        }
       	
   		return "redirect:/user/viewNote";
   	}
    
    
}
