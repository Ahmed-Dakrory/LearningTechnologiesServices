package main.com.zc.services.domain.person.model;

import java.util.List;

public interface UserDao {
public void saveUser ( User user );
public List<User> getUser();
}
