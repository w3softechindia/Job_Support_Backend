package com.example.JobSupportBackend.EmailUtil;

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

	public void sendOtpMail(String email, String otp) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
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
				+ "\r\n" + "        <p style=\"margin-top: 30px;\">Safe travels with Railways!</p>\r\n"
				+ "    </div>\r\n" + "\r\n" + "</body>", otp);
		helper.setText(emailBody, true);
		javaMailSender.send(message);
	}

	public void setPassword(String email, String otp) throws MessagingException, InvalidIdException {
		User user = userRepository.findById(email).orElseThrow(()-> new InvalidIdException("Email doesnot exist..!!"));
		String name=user.getUsername();
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setTo(email);
		helper.setSubject("OTP Verification");
		String emailBody = String.format(
				"<body style=\"font-family: Arial, sans-serif; background-color: #f4f4f4; color: #333; text-align: center; padding: 20px;\">\r\n"
						+ "\r\n"
						+ "    <div style=\"max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\r\n"
						+ "        <h2 style=\"color: #ff5722;\">JobSupport4U OTP Verification</h2>\r\n"
						+ "        <h2>Dear "+name+",</h2>\r\n" + "        <p>Your OTP for Verification is:</p>\r\n"
						+ "        \r\n"
						+ "        <div style=\"font-size: 36px; color: #ff5722; margin: 20px 0; padding: 10px; border: 2px solid #ff5722; border-radius: 5px;\">\r\n"
						+ "            <strong>" + otp + "</strong>\r\n" + "        </div>\r\n" + "\r\n"
						+ "        <p>This OTP is valid for a single use and will expire in 1 minutes.</p>\r\n"
						+ "        <p>For security reasons, do not share this OTP with anyone. If you didn't request this OTP, please ignore this message.</p>\r\n"
						+ "\r\n" + "        <p style=\"margin-top: 30px;\">Safe travels with Railways!</p>\r\n"
						+ "    </div>\r\n" + "\r\n" + "</body>",
				otp);
		helper.setText(emailBody, true);
		javaMailSender.send(message);
	}
}
