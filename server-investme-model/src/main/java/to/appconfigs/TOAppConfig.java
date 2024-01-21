package to.appconfigs;

import abstracts.AbstractTOObject;

public class TOAppConfig extends AbstractTOObject {
	private static final long serialVersionUID = -187618168381057175L;
	
	private int id;
	private String language;
	private boolean darkMode;
	private boolean showValues;
	private boolean showValuesStartUp;
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
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
	public boolean isShowValues() {
		return showValues;
	}
	public void setShowValues(boolean showValues) {
		this.showValues = showValues;
	}
	public boolean isShowValuesStartUp() {
		return showValuesStartUp;
	}
	public void setShowValuesStartUp(boolean showValuesStartUp) {
		this.showValuesStartUp = showValuesStartUp;
	}

}
