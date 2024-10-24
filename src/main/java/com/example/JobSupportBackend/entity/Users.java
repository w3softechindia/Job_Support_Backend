package com.example.JobSupportBackend.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users implements UserDetails {

	/**
	 * 
	 */ //this is my commit
	private static final long serialVersionUID = 1L;
	@Id
	private String email;
	private String name;
	private String password;
	private String otp;
	private LocalDateTime otpGeneratedtime;
	private boolean verified;
	private String status;

	private String imagePath;

	private String firstname;
	private String lastname;
	private long phonenumber;
	private String role;

	private String dob;
	private String jobtitle;
	private String typeofjob;
	private String description;

	@Column(name = "image_bytes")
	private byte[] imageBytes;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Skills> skills;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Education> education;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Experience> experience;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Certification> certification;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Language> language;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "users")
	@JsonManagedReference
	private List<Portfolio> portfolio;

	private String facebook;
	private String instagram;
	private String linkedin;

	private String persnolurl;
	private String address;
	private String city;
	private String state;
	private String postcode;
	private String documenttype;
	private String documentnumber;

	private String ecompany;
	private String etagline;
	private String establishdate;
	private String ecompanyownername;
	private String industry;
	private String ewebsite;
	private String eteamsize;
	private String edescribe;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<Authority> authorities = new HashSet<>();
		authorities.add(new Authority("ROLE_" + this.role));
		return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
