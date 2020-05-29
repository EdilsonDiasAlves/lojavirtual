package com.mz.lojavirtual.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.mz.lojavirtual.security.UserDetailsImpl;

public class UserService {
	
	public static UserDetailsImpl getAuthenticated() {
		try {
			return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}

}
