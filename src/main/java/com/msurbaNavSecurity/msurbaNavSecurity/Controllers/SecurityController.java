package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Permission;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.EncryptionService;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.JwtService;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.ValidatorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@CrossOrigin
@RestController
@RequestMapping("api/public/security")
public class SecurityController {

    @Autowired
    private UserRepository theUserRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EncryptionService encryptionService;

    @Autowired
    private ValidatorService validatorService;

    // private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("login")
    public String login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        String token = "";
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser != null
                && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))) {

            token = jwtService.generateToken(actualUser);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return token;
    }

    /*
     * 
     * //Método logout
     * //Método reset pass
     * 
     * @GetMapping("token-validation")
     * public User tokenValidation(final HttpServletRequest request) {
     * User theUser=this.validatorService.getUser(request);
     * return theUser;
     * }
     * 
     * // ofrece servicio para verificar el rolePermission
     */
    @PostMapping("permissions-validation")
    public boolean permissionsValidation(final HttpServletRequest request, @RequestBody Permission thePermission) {
        System.out.println(thePermission);
        boolean success = this.validatorService.validationRolePermission(request, thePermission.getUrl(),
                thePermission.getMethod());
        return success;
    }

}
