package br.com.devpree.investme.api.common.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AppConfig {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String language;
	private boolean darkMode;
	private boolean showValues;
	private boolean showValuesStartUp;
	
	// Getters and Setters
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public boolean isDarkMode() {
		return darkMode;
	}
	public void setDarkMode(boolean darkMode) {
		this.darkMode = darkMode;
	}
	public boolean isShowValuesStartUp() {
		return showValuesStartUp;
	}
	public void setShowValuesStartUp(boolean showValuesStartUp) {
		this.showValuesStartUp = showValuesStartUp;
	}
	public boolean isShowValues() {
		return showValues;
	}
	public void setShowValues(boolean showValues) {
		this.showValues = showValues;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
