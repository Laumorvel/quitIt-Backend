package com.example.demo.model;

import java.util.Objects;

public class ScheduledMessage {
	
	private String text;
	
	public ScheduledMessage() {}

	public ScheduledMessage(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
