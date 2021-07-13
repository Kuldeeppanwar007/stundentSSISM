package com.student_app.controllers.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.student_app.models.admin.Admin;
import com.student_app.services.admin.AdminService;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminRegistrationController {
	
	@Autowired
	private AdminService adminService;
	private static final Logger log = LoggerFactory.getLogger(AdminRegistrationController.class);
	
	@PostMapping("/register_admin")
	public ResponseEntity<?> addAdmin(@RequestBody Admin admin)
	{
		log.info("Request come on the register new admin controller");
		      
		   Admin tempAdmin = this.adminService.addAdmin(admin);
			if(tempAdmin == null)
			{
				log.debug("New admin not registred");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); 
			}
			else {
				log.debug("New admin registred successfully");
				return ResponseEntity.status(HttpStatus.OK).build();
			}
	}

	        
}
