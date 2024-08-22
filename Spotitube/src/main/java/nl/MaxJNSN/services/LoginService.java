package nl.MaxJNSN.services;

import jakarta.inject.Inject;
import nl.MaxJNSN.datasource.dao.LoginDAO;
import nl.MaxJNSN.services.dto.LoginRequestDTO;
import nl.MaxJNSN.services.dto.LoginResponseDTO;
import nl.MaxJNSN.services.exceptions.DatabaseException;
import nl.MaxJNSN.services.exceptions.InvalidLoginException;

public class LoginService {

    private LoginDAO loginDAO;

    @Inject
    public LoginService(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    public LoginResponseDTO loginUser(LoginRequestDTO loginRequestDTO) {
        try {
            if (loginDAO.doesUserExist(loginRequestDTO)) {
                String token = loginDAO.generateToken(loginRequestDTO);
                return new LoginResponseDTO(loginRequestDTO.getUser(), token);
            } else {
                throw new InvalidLoginException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException();
        }
    }
}
