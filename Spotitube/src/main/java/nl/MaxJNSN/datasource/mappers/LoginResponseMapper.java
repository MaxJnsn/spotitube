package nl.MaxJNSN.datasource.mappers;

import nl.MaxJNSN.services.dto.LoginResponseDTO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginResponseMapper implements DataMapper<LoginResponseDTO> {

    @Override
    public LoginResponseDTO mapToDTO(ResultSet resultSet) throws SQLException {
        resultSet.next();
        return new LoginResponseDTO(resultSet.getString("user"), resultSet.getString("token"));
    }
}