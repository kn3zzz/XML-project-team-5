package com.example.AgentApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Post {
	
	@Id
	@Column(name="id", unique=true, nullable=false)
	public int id;
	@Column
	public String content;
	
	public Post(int id, String content) {
		super();
		this.id = id;
		this.content = content;
	}
	
	public Post() {}
}
