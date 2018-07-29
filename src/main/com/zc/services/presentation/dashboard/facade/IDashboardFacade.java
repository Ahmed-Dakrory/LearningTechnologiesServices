/**
 * 
 */
package main.com.zc.services.presentation.dashboard.facade;

import java.util.List;

import main.com.zc.services.presentation.dashboard.dto.DashboardElement;

/**
 * @author momen
 *
 */

public interface IDashboardFacade {

	List<DashboardElement> fillDashboardElements();
	List<DashboardElement> fillAdminDashboardElements();
}
