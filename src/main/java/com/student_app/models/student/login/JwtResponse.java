package com.student_app.models.student.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
	private String token;
    private String activeFirstName;
    private String activeLastName;
    }
