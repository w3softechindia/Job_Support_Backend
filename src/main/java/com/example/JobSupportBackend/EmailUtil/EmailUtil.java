package com.example.JobSupportBackend.EmailUtil;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.example.JobSupportBackend.entity.User;
import com.example.JobSupportBackend.exceptions.InvalidIdException;
import com.example.JobSupportBackend.repo.UserRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserRepository userRepository;

	public void sendOtpMail(String email, String otp) throws MessagingException, InvalidIdException, UnsupportedEncodingException {
//		User user = userRepository.findById(email).orElseThrow(()-> new InvalidIdException("Email doesnot exist..!!"));
//		String name=user.getUsername();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
		helper.setTo(email);
		helper.setSubject("Email Verification");
		String emailBody = String.format("<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; text-align: center; padding: 20px;\">\r\n"
				+ "\r\n"
				+ "    <div style=\"max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\r\n"
				+ "        <h2 style=\"color: #ff5722;\">JobSupport4U OTP Verification</h2>\r\n"
				+ "        <h2>Dear "+email+",</h2>\r\n" + "        <p>Your OTP for Verification is:</p>\r\n"
				+ "        \r\n"
				+ "        <div style=\"font-size: 36px; color: #ff5722; margin: 20px 0; padding: 10px; border: 2px solid #ff5722; border-radius: 5px;\">\r\n"
				+ "            <strong>" + otp + "</strong>\r\n" + "        </div>\r\n" + "\r\n"
				+ "        <p>This OTP is valid for a single use and will expire in 1 minutes.</p>\r\n"
				+ "        <p>For security reasons, do not share this OTP with anyone. If you didn't request this OTP, please ignore this message.</p>\r\n"
				+ "\r\n" + "       \r\n"
				+ "    </div>\r\n" + "\r\n" + "</body>", otp);
		helper.setText(emailBody, true);
		javaMailSender.send(message);
	}

	public void sendPasswordOtp(String email, String otp) throws MessagingException, InvalidIdException, UnsupportedEncodingException {
		User user = userRepository.findById(email).orElseThrow(()-> new InvalidIdException("Email doesnot exist..!!"));
		String name=user.getName();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
		helper.setTo(email);
		helper.setSubject("OTP Verification");
		String emailBody = String.format(
				"<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; text-align: center; padding: 20px;\">\r\n"
						+ "\r\n"
						+ "    <div style=\"max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\r\n"
						+ "        <h2 style=\"color: #ff5722;\">JobSupport4U OTP Verification</h2>\r\n"
						+ "        <h2>Dear "+name+",</h2>\r\n" + "        <p>Your OTP for Changing Password is:</p>\r\n"
						+ "        \r\n"
						+ "        <div style=\"font-size: 36px; color: #ff5722; margin: 20px 0; padding: 10px; border: 2px solid #ff5722; border-radius: 5px;\">\r\n"
						+ "            <strong>" + otp + "</strong>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <p>This OTP is valid for a single use and will expire in 1 minutes.</p>\r\n"
						+ "        <p>For security reasons, do not share this OTP with anyone. If you didn't request this OTP, please ignore this message.</p>\r\n"
						+ "\r\n" + "       \r\n"
						+ "    </div>\r\n" + "\r\n" + "</body>",
				otp);
		helper.setText(emailBody, true);
		javaMailSender.send(message);
	}

	public void sendFreelancerHiringNotification(String email, String projectName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
        helper.setTo(email);
        helper.setSubject("Congratulations! You've been hired");

        String content = String.format("<h1>Congratulations!</h1>" +
                "<p>Hello "+ email + ",</p>" +
                "<p>We are pleased to inform you that you have been hired for the project <strong>"+ projectName +"</strong>.</p>" +
                "<p>We look forward to your great contributions. Further details will follow shortly.</p>" +
                "<p>Best Regards,<br/>Your Team</p>", email, projectName);
        helper.setText(content, true);

        javaMailSender.send(message);
    }
	
	public void sendProjectStartedNotification(String employerEmail, String freelancerName, String projectName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
        helper.setTo(employerEmail);
        helper.setSubject("Your project is now in progress");
        String content= String.format("<h1>Project Update</h1>" +
                "<p>Dear Project Owner,</p>" +
                "<p>We are pleased to inform you that your project <strong>"+ projectName +"</strong> is now being worked on by <strong>"+ freelancerName +"</strong>.</p>" +
                "<p>We will keep you updated on the progress.</p>" +
                "<p>Best Regards,<br/>Your Team</p>", projectName, freelancerName);
        helper.setText(content, true);
        javaMailSender.send(message);
    }
	
	public void sendRejectionNotification(String email, String projectName) throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
	    helper.setTo(email);
	    helper.setSubject("Update on Your Proposal Submission");
	    String content=String.format("<h1>Proposal Rejected</h1>" +
                "<p>Hello,</p>" +
                "<p>We regret to inform you that your proposal for the project <strong>"+ projectName +"</strong> has been rejected.</p>" +
                "<p>We encourage you to apply for other projects and wish you better luck next time.</p>" +
                "<p>Best Regards,<br/>Your Team</p>", projectName);
	    helper.setText(content, true);
	    javaMailSender.send(message);
	}
	
	public void sendProjectRejectionToEmployer(String email, String projectName) throws MessagingException, UnsupportedEncodingException {
	    MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("jobsupport4u@w3softech.in","JobSupport4U");
	    helper.setTo(email);
	    helper.setSubject("Update on Your Project");
	    String content=String.format("<h1>Project Rejected</h1>" +
                "<p>Hello,</p>" +
                "<p>We regret to inform you that your project <strong>"+ projectName +"</strong> has been rejected to publish by the Admin</p>" +
                "<p>We encourage you to post other projects and wish you better luck next time.</p>" +
                "<p>Best Regards,<br/>Your Team</p>", projectName);
	    helper.setText(content, true);
	    javaMailSender.send(message);
	}

}
