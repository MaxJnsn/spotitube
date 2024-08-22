package nl.MaxJNSN.services.dto;

public class LoginResponseDTO {
    private String user;
    private String token;

    public LoginResponseDTO(String user, String token) {
        this.user = user;
        this.token = token;
    }



    // Getters and Setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
