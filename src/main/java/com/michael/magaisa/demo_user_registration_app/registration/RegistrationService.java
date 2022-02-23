package com.michael.magaisa.demo_user_registration_app.registration;

import com.michael.magaisa.demo_user_registration_app.appuser.AppUser;
import com.michael.magaisa.demo_user_registration_app.appuser.AppUserRole;
import com.michael.magaisa.demo_user_registration_app.appuser.AppUserService;
import com.michael.magaisa.demo_user_registration_app.registration.token.ConfirmationToken;
import com.michael.magaisa.demo_user_registration_app.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class RegistrationService {
    private final String inValidEmailMsg = "The email address: %s is not valid";
    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        if (!isValidEmail){
            throw new IllegalStateException(String.format(inValidEmailMsg, request.getEmail()));
        }
        return appUserService.signUpUser(
                new AppUser(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        AppUserRole.USER
                )
        );
    }

    @Transactional
    public String confirmToken( String token){
        ConfirmationToken confirmationToken = confirmationTokenService
        .getToken(token).
                orElseThrow(() -> new IllegalStateException("Invalid Token"));
        if (confirmationToken.getConfirmedAt() != null){
            throw new IllegalStateException("Email already confirmed!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())){
            throw new IllegalStateException(("Token has expired..."));
        }

        confirmationTokenService.setConfirmedAt(token);
        appUserService.enableAppUser(confirmationToken.getAppUser().getEmail());

        return "confirmed!";
    }
}
