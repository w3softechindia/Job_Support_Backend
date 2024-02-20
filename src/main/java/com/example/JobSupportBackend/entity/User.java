package com.example.JobSupportBackend.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



import jakarta.persistence.Entity;
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
public class User implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String email;
	private String username;
	private String password;

	private String firstname;
	private String lastname;
	private long phonenumber;
	private String role;

	private String dob;
	private String jobtitle;
	private String typeofjob;
	private String description;
	
	@OneToMany
	private List<Skills> skills;

//	private String skills;
//	private String level;
//	private String degree;
//	private String university;
//	private String startdate;
//	private String enddate;
//
//	private String certification;
//	private String certifiedfrom;
//	private String year;
//	private String companyname;
//	private String position;
//	private String companystartdate;
//	private String companyenddate;
//
//	private String language;
//	private String chooselevel;

	private String facebook;
	private String instagram;
	private String linkedin;

	private String persnolurl;
	private String address;
	private String city;
	private String state;
	private String postcode;
	private String postcodetype;
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
