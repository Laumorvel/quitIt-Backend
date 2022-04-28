package com.example.demo.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ScheduledMessage {
	// string_in_string = "Shepherd {} is on duty.".format(shepherd)

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String text;
	
	@Column(nullable = false)
	private Boolean sent;

	public ScheduledMessage() {
	}

	public ScheduledMessage(String text) {
		this.text = text;
	}

	public ScheduledMessage(String text, Boolean sent) {
		this.text = text;
		this.sent = sent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Boolean getSent() {
		return sent;
	}

	public void setSent(Boolean sent) {
		this.sent = sent;
	}

	@Override
	public String toString() {
		return "ScheduledMessage [text=" + text + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(text);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ScheduledMessage other = (ScheduledMessage) obj;
		return Objects.equals(text, other.text);
	}

}
