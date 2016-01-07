package it.anggen.generation.frontend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.anggen.generation.JsGenerator;

@Service
public class FrontEndGenerator {
	
	@Autowired
	private JsGenerator jsGenerator;
	
	
	
	public FrontEndGenerator() {
		// TODO Auto-generated constructor stub
	}

}
