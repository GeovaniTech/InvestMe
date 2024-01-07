package model;

public class AppConfigs {
	
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
	
}
