package com.trynumbers.attempt.entity;

import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sochema.numbers")
public class MyNumber {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private long name;
	
	@Column(name = "roma_notation")
	private String romaNotation;
	
	@Column(name = "binary_notation")
	private String binaryNotation;
	
	@Column(name = "description")
	private String description;
	

	public MyNumber() {
		super();
	}


	public MyNumber(long name, String romaNotation, String binaryNotation, String description) {
		super();
		this.name = name;
		this.romaNotation = romaNotation;
		this.binaryNotation = binaryNotation;
		this.description = description;
	}

	


	public long getName() {
		return name;
	}

	public void setName(long name) {
		this.name = name;
	}

	public String getRomaNotation() {
		return romaNotation;
	}

	public void setRomaNotation(String romaNotation) {
		this.romaNotation = romaNotation;
	}

	public String getBinaryNotation() {
		return binaryNotation;
	}

	public void setBinaryNotation(String binaryNotation) {
		this.binaryNotation = binaryNotation;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	
}
