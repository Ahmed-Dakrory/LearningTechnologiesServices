/**
 * 
 */
package main.com.zc.services.domain.person.model;

import java.util.List;

/**
 * @author Omnya
 *
 */
public interface IStudentRepository {
	public int add ( Student user );
	public List<Student> getAll();
	public Student getPersonById(int id);
	public Student getPersonByFileNo(int fileNo);
	public Student getPersonByMail(String mail);
	public List<Student> getByLevel(Integer level);
}
