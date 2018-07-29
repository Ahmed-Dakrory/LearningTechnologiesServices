package main.com.zc.services.domain.person.service.business;

import java.util.List;

import main.com.zc.services.domain.person.model.User;

public interface UserService {
	public void addUser(User user);

	public List<User> getUser();
}
