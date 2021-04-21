/**
 * 
 */
package main.com.zc.services.presentation.dashboard.dto;

/**
 * @author omnya
 *
 */
public class IncompleteGradeDashboardElement extends DashboardElement
{
	public IncompleteGradeDashboardElement(String value, String Role)
	{
		super("Incomplete Grade ("+Role+")", value, "newIncompleteGrade", "newIncompleteGrade");
	}
}
