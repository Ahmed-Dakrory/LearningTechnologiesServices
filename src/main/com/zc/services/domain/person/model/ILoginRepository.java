/**
 * 
 */
package main.com.zc.services.domain.person.model;

import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface ILoginRepository {
public int add(Login login);
public boolean remove(int id);
public Login update(Login login);
public List<Login> getAll();
public Login getLoginByPersonId(int personId);
public Login getLoginByFileNo(int fileNo);
public Login getById(int id);


}
