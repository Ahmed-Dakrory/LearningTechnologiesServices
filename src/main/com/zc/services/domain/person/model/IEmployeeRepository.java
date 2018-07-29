/**
 * 
 */
package main.com.zc.services.domain.person.model;

import java.util.Date;
import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface IEmployeeRepository {
	public Employee add(Employee instructor);

	public boolean remove(int id);

	public Employee update(Employee instructor);

	//Due to the changing of employees table get all means get all instructors
	public List<Employee> getAll();
	
	public List<Employee>getAllTas();
	
	public List<Employee>getByType(Integer typeID);
	
	public Employee getById(int id);
	
	public Employee getByName(String name);
	public Employee getByMail(String mail);

	void updateLastNotificationDate(Date last, Integer id);

	List<Employee> getAllEmp();
}
