package main.com.zc.services.presentation.dashboard.dto;

public class ReadmissionDashboardElement extends DashboardElement
{
	public ReadmissionDashboardElement(String value,String Role)
	{
		super("Readmission ("+Role+")", value, "newChangeMajor", "newReadmission");
	}
}
