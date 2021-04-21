package main.com.zc.services.presentation.dashboard.dto;

public class AddDropDashboardElement extends DashboardElement
{
	public AddDropDashboardElement(String value,String Role)
	{
		super("Drop ("+Role+")", value, "newAddDrop", "newAddDrop");
	}
}
