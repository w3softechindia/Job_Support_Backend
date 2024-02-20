package com.example.JobSupportBackend.entity;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = -700205366029960906L;
	private String authority;

	@Override
	public String getAuthority() {

		return this.authority;
	}

}
