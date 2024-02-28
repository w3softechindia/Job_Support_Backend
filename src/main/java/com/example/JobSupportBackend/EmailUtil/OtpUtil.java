package com.example.JobSupportBackend.EmailUtil;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class OtpUtil {
	
	public String generateOtp() {
		Random random=new Random();
		int randomNumber=random.nextInt(9999);
		String output=Integer.toString(randomNumber);
		
		while(output.length()<4) {
			output="0"+output;
		}
		return output;
	}
}
