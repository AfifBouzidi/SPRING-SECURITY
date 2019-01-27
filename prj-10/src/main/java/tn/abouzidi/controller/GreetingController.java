package tn.abouzidi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class GreetingController {

	@RequestMapping(value = "/greetings", method = RequestMethod.GET, produces = "text/plain")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<String> sendGreetingMessage() {
		String userName=SecurityContextHolder.getContext().getAuthentication().getName();
		return new ResponseEntity<>("Hello "+userName, HttpStatus.OK);
	}

}
