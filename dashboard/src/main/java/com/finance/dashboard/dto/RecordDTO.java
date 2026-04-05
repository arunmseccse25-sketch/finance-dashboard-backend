package com.finance.dashboard.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordDTO {

	private Long id;

	@NotNull(message = "Amount is required")
	@DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
	private BigDecimal amount;

	@NotBlank(message = "Type is required")
	private String type;

	@NotBlank(message = "Category is required")
	private String category;

	@NotNull(message = "Date is required")
	private LocalDate date;

	private String description;

	@NotNull(message = "UserId is required")
	private Long userId;
}