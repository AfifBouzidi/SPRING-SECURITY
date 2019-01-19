package tn.abouzidi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GreetingController {

	@RequestMapping(value = "/greetings", method = RequestMethod.GET, produces = "text/plain")
	public ResponseEntity<String> sendGreetingMessage() {
		return new ResponseEntity<>("Hello World", HttpStatus.OK);
	}

}
