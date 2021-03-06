package com.api.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.models.SmsPojo;
import com.api.models.Student;
import com.api.services.OtpService;
import com.api.services.SmsService;
import com.api.services.StudentService;

@RestController
public class RegistrationController {
	
	@Autowired
	private OtpService otpservice;
	
    @Autowired
    private StudentService studentService;
	
	@Autowired
    SmsService service;

    @Autowired
    private SimpMessagingTemplate webSocket;
    
    private final String  TOPIC_DESTINATION = "/lesson/sms";
    

	
	   @PostMapping("/getotp")
	   public ResponseEntity<String> sendOTP(@RequestBody SmsPojo sms)
	   {
		   
		   try{
	         	System.out.println("hello"); 
	         	service.send(sms);
	             System.out.println("hello");
	             webSocket.convertAndSend(TOPIC_DESTINATION, getTimeStamp() + ": SMS has been sent!: "+sms.getPhoneNo());
	             return new ResponseEntity<String>("OTP Send successfully",HttpStatus.OK);
	         }
	         catch(Exception e){

	         	 return new ResponseEntity<String>("somthing problem",HttpStatus.INTERNAL_SERVER_ERROR);
	         }
		   
	   }
	   
	   private String getTimeStamp() {
	        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	     }
	
	   
	   
	   
	   @PostMapping("/register")
	   public ResponseEntity<String> addStudent(@RequestBody Student student)
	   {
//		         ok
				System.out.println(student.toString());
				
				String phoneno = "+91"+Long.toString(student.getMobileNumber());
//				String otp = Integer.toString(student.getOTP());/
				
			    int serverOtp = otpservice.getOtp(phoneno);
				int clientOtp = student.getOTP();
				System.out.println("client"+clientOtp);
				System.out.println("server"+serverOtp);
				
				if(clientOtp == serverOtp) {
					otpservice.clearOTP(phoneno);
//					student.setOTP(0);
					
//					code for save data to DB
					  Student student2 = this.studentService.addStudent(student);
					   if(student2 == null)
					   {
						   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();   
					   }
					   else {
					   return ResponseEntity.ok("Your registration is done!");
					   }
					
					
				}else {
					return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("please enter correct 'OTP'!!!");
				}	
	   }
}