package nl.MaxJNSN.controllers;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nl.MaxJNSN.services.LoginService;
import nl.MaxJNSN.services.dto.LoginRequestDTO;
import nl.MaxJNSN.services.dto.LoginResponseDTO;

@Path("/login")
public class LoginController {

    private LoginService loginService;

    @Inject
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequestDTO loginRequestDTO) {
        try {
            LoginResponseDTO responseDTO = loginService.loginUser(loginRequestDTO);
            return Response.ok(responseDTO).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid login").build();
        }
    }
}
