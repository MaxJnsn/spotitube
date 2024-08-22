package nl.MaxJNSN.datasource.dao;

import jakarta.inject.Inject;
import nl.MaxJNSN.datasource.mappers.LoginResponseMapper;
import nl.MaxJNSN.datasource.util.DatabaseProperties;
import nl.MaxJNSN.services.dto.LoginRequestDTO;
import nl.MaxJNSN.services.dto.LoginResponseDTO;

import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginDAO {
    private Logger logger = Logger.getLogger(getClass().getName());
    private DatabaseProperties databaseProperties;
    private LoginResponseMapper loginResponseMapper;

    @Inject
    public LoginDAO(DatabaseProperties databaseProperties, LoginResponseMapper loginResponseMapper) {
        this.databaseProperties = databaseProperties;
        this.loginResponseMapper = loginResponseMapper;
    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) throws SQLException {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            LoginResponseDTO response = loginResponseMapper.mapToDTO(getUsername(loginRequestDTO, connection));
            return response;
        } catch (SQLException e) {
            throw new SQLException();
        }
    }

    private ResultSet getUsername(LoginRequestDTO loginRequestDTO, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from user where user = ? and password = ?");
        statement.setString(1, loginRequestDTO.getUser());
        statement.setString(2, loginRequestDTO.getPassword());
        return statement.executeQuery();
    }

    public boolean doesUserExist(LoginRequestDTO user) {
        try (Connection connection = DriverManager.getConnection(databaseProperties.connectionString())) {
            PreparedStatement statement = connection.prepareStatement("select id from user where user = ? and password = ?");
            statement.setString(1, user.getUser());
            statement.setString(2, user.getPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Username and/or Password is incorrect", e);
            return false;
        }
        return false;
    }

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public String generateToken(LoginRequestDTO loginRequestDTO) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
}