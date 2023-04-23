package com.example.spring6restmvc.model;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Customer {
	private UUID id;
	private String name;
	private int version;
	private LocalDateTime createdDate;
	private LocalDateTime lastModifiedDate;
}
