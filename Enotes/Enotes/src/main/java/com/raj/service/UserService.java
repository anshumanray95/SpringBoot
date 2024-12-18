package com.raj.service;

import com.raj.entity.User;

public interface UserService  {
	
         public User saveUser(User user);
         
         public boolean existEmailCheck(String email);
         public User userLogin(String email, String password);

		public User findByEmail(String email);

//		public boolean checkPassword(User user, String password);

		
}
