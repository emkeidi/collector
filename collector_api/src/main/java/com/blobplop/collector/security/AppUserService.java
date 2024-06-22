package com.blobplop.collector.security;

import com.blobplop.collector.domain.ActionStatus;
import com.blobplop.collector.domain.Result;
import com.blobplop.collector.entities.AppUser;
import com.blobplop.collector.repositories.AppUserRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppUserService implements UserDetailsService {
    private final AppUserRepository repository;
    private final PasswordEncoder encoder;

    public AppUserService(AppUserRepository repository,
                          PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws
                                                           UsernameNotFoundException {
        AppUser appUser = repository.findByUsername(username);

        if (appUser == null || !appUser.isEnabled()) {
            throw new UsernameNotFoundException(username + " not found");
        }

        return appUser;
    }

    public Result<AppUser> create(String username, String password, String email) {
        Result<AppUser> result = validate(username, password, email);
        if (!result.isSuccess()) {
            return result;
        }

        password = encoder.encode(password);

        AppUser appUser = new AppUser(0, username, password, email, true, List.of("USER"));

        // Check if username already exists
        if (repository.findByUsername(username) != null) {
            result.addMessage(ActionStatus.INVALID, "The provided username " + username + " already exists");
            return result;
        }

        // Check if email already exists
        if (repository.findByEmail(email) != null) {
            result.addMessage(ActionStatus.INVALID, "The provided email " + email + " already exists");
            return result;
        }

        try {
            appUser = repository.save(appUser);
            result.setPayload(appUser);
        } catch (DuplicateKeyException e) {
            // This catch block may not be necessary anymore if you're checking for duplicates beforehand
            result.addMessage(ActionStatus.INVALID, "Duplicate key error");
        }

        return result;
    }

    private Result<AppUser> validate(String username, String password,
                                     String email) {
        Result<AppUser> result = new Result<>();
        if (username == null || username.isBlank()) {
            result.addMessage(ActionStatus.INVALID, "username is required");
            return result;
        }

        if (password == null) {
            result.addMessage(ActionStatus.INVALID, "password is required");
            return result;
        }

        if (username.length() > 50) {
            result.addMessage(ActionStatus.INVALID, "username must be less " +
                    "than 50 characters");
        }

        if (!isValidPassword(password)) {
            result.addMessage(ActionStatus.INVALID,
                    "password must be at least 8 character and contain a " +
                            "digit," +
                            " a letter, and a non-digit/non-letter");
        }

        if (!isValidEmail(email)) {
            result.addMessage(ActionStatus.INVALID, "email is not valid");
        }

        return result;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }

        int digits = 0;
        int letters = 0;
        int others = 0;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                digits++;
            } else if (Character.isLetter(c)) {
                letters++;
            } else {
                others++;
            }
        }

        return digits > 0 && letters > 0 && others > 0;
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
