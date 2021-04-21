package main.com.zc.services.presentation.dashboard.dto;

public class ChangeConcentrationDashboardElement extends DashboardElement
{
	public ChangeConcentrationDashboardElement(String value,String Role)
	{
		super("Change Concentration ("+Role+")", value, "newCourseRepeat", "changeconcentration");
	}
}
