/**
 * 
 */
package main.com.zc.services.domain.person.model;

import java.util.List;

/**
 * @author Omnya Alaa
 *
 */
public interface ILoginDataRepository {
public List<LoginData> getAll();
public int add(LoginData loginStaff);
public boolean remove(int id);
public LoginData update(LoginData loginStaff);
public LoginData getById(int id);
public LoginData getByMail(String mail);


}
