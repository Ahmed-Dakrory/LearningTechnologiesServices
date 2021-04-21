package main.com.zc.services.presentation.dashboard.dto;

public class OverloadRequestDashboardElement extends DashboardElement
{
	public OverloadRequestDashboardElement(String value, String Role)
	{
		super("Overload Request ("+Role+")", value, "newOverload", "newOverload");
	}
}
