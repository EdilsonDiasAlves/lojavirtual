package com.mz.lojavirtual.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mz.lojavirtual.services.DBService;

@Configuration
@Profile("local")
public class LocalConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean initDatabase() throws ParseException {
		dbService.initializeLocalDatabase();
		return true;
	}

}
