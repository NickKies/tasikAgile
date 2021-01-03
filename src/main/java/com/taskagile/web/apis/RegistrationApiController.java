package com.taskagile.web.apis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class RegistrationApiController {
    @Autowired
    private UserService service;

    @PostMapping("/api/registraions")
    public ResponseEntity<ApiResult> register(@Valid @RequestBody RegistraionPayload payload) {
        try {
            service.register(payload.toCommand());
            return Result.created();
        } catch (RegistraionException e) {
            String errorMessage = "Registraion failed";
            if ( e instanceof UsernameExistsException) {
                errorMessage = "Username already exists";
            } else if ( e instanceof EmalAddressExistsException) {
                errorMessage = "Email address already exists";
            }
            return Result.failure(erroeMessage);
        }
    }
}
