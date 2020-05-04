package com.indra.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.indra.service.ImportarCsvService;

@RestController
@RequestMapping("importarCsv")
@CrossOrigin(origins= "*")
public class ImportarCsvController {
	
	@Autowired
	private ImportarCsvService service;
	
	
	@PostMapping
	public boolean importarCsv() throws IOException {

		return service.importarCsv();
		
	}	

}
