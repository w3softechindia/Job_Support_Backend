package com.example.JobSupportBackend.dto;

import java.util.Set;

import com.example.JobSupportBackend.entity.Certification;
import com.example.JobSupportBackend.entity.Education;
import com.example.JobSupportBackend.entity.Experience;
import com.example.JobSupportBackend.entity.Language;
import com.example.JobSupportBackend.entity.Skills;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDataDTO {
	
    private Set<Skills> skills;
    private Set<Education> educations;
    private Set<Certification> certifications;
    private Set<Experience> experiences;
    private Set<Language> languages;
}

