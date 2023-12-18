package com.msurbaNavSecurity.msurbaNavSecurity.Controllers;

import com.msurbaNavSecurity.msurbaNavSecurity.Models.Permission;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.User;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.UserRepository;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.EncryptionService;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.JwtService;
import com.msurbaNavSecurity.msurbaNavSecurity.Services.ValidatorService;
import com.msurbaNavSecurity.msurbaNavSecurity.Models.Session;
import com.msurbaNavSecurity.msurbaNavSecurity.Repositories.SessionRepository;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private SessionRepository sessionRepository;

    // private static final String BEARER_PREFIX = "Bearer ";

    @PostMapping("login")
    public boolean login(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
        User actualUser = this.theUserRepository.getUserByEmail(theUser.getEmail());
        if (actualUser != null
                && actualUser.getPassword().equals(encryptionService.convertirSHA256(theUser.getPassword()))) {

            // Crear y guardar la sesión
            Session userSession = new Session(true, generateRandomCode());
            userSession.setUser(actualUser);
            sessionRepository.save(userSession);

        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        return true;
    }

    @PostMapping("login2")
    public String login2(@RequestBody User theUser, final HttpServletResponse response) throws IOException {
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

    private int generateRandomCode() {
        Random random = new Random();
        return 1000 + random.nextInt(9000); // Genera un número entre 1000 y 9999
    }

    @GetMapping("getSessionCode")
    public ResponseEntity<Integer> getSessionCode(@RequestParam String email) {
        User actualUser = theUserRepository.getUserByEmail(email);
        if (actualUser != null) {
            Optional<Session> userSessionOpt = sessionRepository.findByUserAndActive(actualUser, true);
            if (userSessionOpt.isPresent()) {
                return ResponseEntity.ok(userSessionOpt.get().getCode());
            }
        }
        return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(null);
    }



    @PostMapping("verifyCode")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, Object> requestBody) {
        String email = (String) requestBody.get("email");
        int code = (Integer) requestBody.get("code");

        User actualUser = theUserRepository.getUserByEmail(email);
        if (actualUser == null) {
            return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body(false);
        }

        Optional<Session> userSessionOpt = sessionRepository.findByUserAndActive(actualUser, true);
        if (userSessionOpt.isPresent() && userSessionOpt.get().getCode() == code) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }


    //Método reset pass
    
    @GetMapping("token-validation")
    public User tokenValidation(final HttpServletRequest request) {
        User theUser=this.validatorService.getUser(request);
        return theUser;
    }
      
    // ofrece servicio para verificar el rolePermission
    @PostMapping("permissions-validation")
    public boolean permissionsValidation(final HttpServletRequest request, @RequestBody Permission thePermission) {
        System.out.println(thePermission);
        boolean success = this.validatorService.validationRolePermission(request, thePermission.getUrl(),
                thePermission.getMethod());
        return success;
    }



}
