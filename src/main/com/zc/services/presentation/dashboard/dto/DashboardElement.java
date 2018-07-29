package main.com.zc.services.presentation.dashboard.dto;

public class DashboardElement
{
	private String Name;
	private String Value;
	private String CSS;
	private String LinkUrl;
	
	public DashboardElement()
	{}
	
	public DashboardElement(String name,String val,String css,String linkUrl)
	{ Name = name ; Value = val ; CSS = css;LinkUrl = linkUrl;}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getValue() {
		return Value;
	}
	public void setValue(String value) {
		Value = value;
	}

	public String getCSS() {
		return CSS;
	}
	public void setCSS(String cSS) {
		CSS = cSS;
	}
	public String getLinkUrl() {
		return LinkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		LinkUrl = linkUrl;
	}
}
