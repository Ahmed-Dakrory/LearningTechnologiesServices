package main.com.zc.services.presentation.dashboard.dto;

public class AcademicPetitionDashboardElement extends DashboardElement
{
	public AcademicPetitionDashboardElement(String value,String Role)
	{
		super("Academic Petition ("+Role+")", value, "newPetition", "newPetition");
	}
}
