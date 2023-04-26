package service;

import dao.UserDao;
import entity.UserEntity;
import java.util.List;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserService {

    private static final UserService INSTANCE = new UserService();

    private final UserDao userDao = UserDao.getInstance();

    public List<UserEntity> findAll() {
        return userDao.findAll();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
