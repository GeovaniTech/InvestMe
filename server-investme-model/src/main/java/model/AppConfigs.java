package model;

public class AppConfigs {
	
	private String language;
	private boolean darkMode;
	private boolean showCardValues;
	private boolean showCardValuesStartUp;
	
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
	public boolean isShowCardValues() {
		return showCardValues;
	}
	public void setShowCardValues(boolean showCardValues) {
		this.showCardValues = showCardValues;
	}
	public boolean isShowCardValuesStartUp() {
		return showCardValuesStartUp;
	}
	public void setShowCardValuesStartUp(boolean showCardValuesStartUp) {
		this.showCardValuesStartUp = showCardValuesStartUp;
	}
	
}
