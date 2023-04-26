package dao;

import entity.UserEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import model.Gender;
import model.Role;
import util.ConnectionManager;

@NoArgsConstructor
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_ALL_SQL = "SELECT id, name, email, password, gender, role, birthday FROM users";

    @SneakyThrows
    public List<UserEntity> findAll() {
        try (Connection connection = ConnectionManager.get();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<UserEntity> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }
            return users;
        }
    }

    private UserEntity buildUser(ResultSet resultSet) throws SQLException {
        return UserEntity.builder()
                .id(resultSet.getInt("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .role(Role.valueOf(resultSet.getString("role")))
                .gender(Gender.valueOf(resultSet.getString("gender")))
                .birthday(resultSet.getDate("birthday").toLocalDate())
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
