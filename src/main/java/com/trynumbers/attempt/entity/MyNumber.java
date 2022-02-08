package com.trynumbers.attempt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.IntArrayType;

@Entity
@Table(name = "numbers")
@TypeDefs({
	@TypeDef(name = "int-array", typeClass = IntArrayType.class)
})
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
	
	@Type(type = "int-array")
	@Column(name = "divisors", columnDefinition = "integer[]")
	private int[] divisors;
	

	public MyNumber() {
		super();
	}


	public MyNumber(long name, String romaNotation, String binaryNotation, String description, int[] divisors) {
		super();
		this.name = name;
		this.romaNotation = romaNotation;
		this.binaryNotation = binaryNotation;
		this.description = description;
		this.divisors = divisors;
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


	public int[] getDivisors() {
		return divisors;
	}


	public void setDivisors(int[] divisors) {
		this.divisors = divisors;
	}



	
	
}
