package fr.openwide.core.showcase.core.business.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.openwide.core.jpa.exception.SecurityServiceException;
import fr.openwide.core.jpa.exception.ServiceException;
import fr.openwide.core.jpa.security.business.person.service.GenericSimpleUserServiceImpl;
import fr.openwide.core.showcase.core.business.user.dao.IUserDao;
import fr.openwide.core.showcase.core.business.user.model.User;

@Service("personService")
public class UserServiceImpl extends GenericSimpleUserServiceImpl<User> implements IUserService {

	@Autowired
	public UserServiceImpl(IUserDao userDao) {
		super(userDao);
	}
	
	@Override
	public void updateUserPosition(List<User> userList) throws ServiceException, SecurityServiceException {
		for (User user : userList) {
			update(user);
		}
	}
}
