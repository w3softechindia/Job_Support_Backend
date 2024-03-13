package com.example.JobSupportBackend.dto;

import java.util.List;

import com.example.JobSupportBackend.entity.Portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioDto {
	private List<Portfolio> portfolio;
}
