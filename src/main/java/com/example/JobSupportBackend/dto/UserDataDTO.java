package com.example.JobSupportBackend.dto;

import java.util.List;

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

    private List<Skills> skills;
    private List<Education> educations;
    private List<Certification> certifications;
    private List<Experience> experiences;
    private List<Language> languages;
}

