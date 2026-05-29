package dev.mhmd.finances.auth;


import dev.mhmd.finances.shared.exception.DuplicateResourceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {
    private final JdbcTemplate database;

    public UserRepository(JdbcTemplate database) {
        this.database = database;
    }

    public User save(String username, String password) {
        var query = "INSERT INTO users(username, password) VALUES(?, ?) RETURNING id";

        try {
            return this.database.queryForObject(query, (rs, rn) -> new User(rs.getInt("id"), username, null), username, password);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateResourceException("Username already exists");
        }
    }

    public Optional<User> findUserByUsername(String username) {
        var query = "SELECT id, username, password FROM users WHERE username = ?";
        var user = this.database.queryForObject(query, (rs, rn) -> new User(rs.getInt("id"), username, rs.getString("password")), username);

        return Optional.ofNullable(user);
    }
}
