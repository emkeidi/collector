package com.blobplop.collector.entities;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table(name = "app_user")
public class AppUser implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long appUserId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    // nullable because of social login option
    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enabled;

    // name of the provider, e.g. "local" or "google"
    @Column(name = "provider")
    private String provider;

    // id of the provider, e.g. "local" or "google"
    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;

    @Column(name = "last_login")
    private String lastLogin;

    @Column(name = "failed_login_attempts")
    private int failedLoginAttempts;

    @Transient
    private Collection<GrantedAuthority> authorities;


    public Long getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(Long id) {
        this.appUserId = id;
    }

    public AppUser() {
    }

    public AppUser(int appUserId, String username, String password,
                   boolean enabled, List<String> roles) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;

        this.enabled = enabled;
        this.authorities = convertRolesToAuthorities(roles);
    }

    public AppUser(int appUserId, String username, String password,
                   String email,
                   boolean enabled, List<String> roles) {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.enabled = enabled;
        this.authorities = convertRolesToAuthorities(roles);
    }

    public AppUser(long appUserId, String username, String password,
                   boolean enabled,
                   String provider, String providerId, String createdAt,
                   String updatedAt, String lastLogin,
                   int failedLoginAttempts, List<String> roles)  {
        this.appUserId = appUserId;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
        this.failedLoginAttempts = failedLoginAttempts;
        this.authorities = convertRolesToAuthorities(roles);
    }

    public AppUser(String username, String password, boolean enabled,
                   String provider, String providerId, String createdAt,
                   String updatedAt, String lastLogin,
                   int failedLoginAttempts, List<String> roles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.provider = provider;
        this.providerId = providerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLogin = lastLogin;
        this.failedLoginAttempts = failedLoginAttempts;
        this.authorities = convertRolesToAuthorities(roles);

    }

    private static Collection<GrantedAuthority> convertRolesToAuthorities(List<String> roles) {
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>(authorities);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return enabled == appUser.enabled
                && createdAt == appUser.createdAt
                && updatedAt == appUser.updatedAt
                && lastLogin == appUser.lastLogin
                && failedLoginAttempts == appUser.failedLoginAttempts
                && appUserId == appUser.appUserId && username.equals(appUser.username)
                && email.equals(appUser.email)
                && Objects.equals(password, appUser.password)
                && Objects.equals(provider, appUser.provider)
                && Objects.equals(providerId, appUser.providerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appUserId, username, email, password, enabled, provider,
                providerId, createdAt, updatedAt, lastLogin, failedLoginAttempts);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", provider='" + provider + '\'' +
                ", providerId='" + providerId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", lastLogin=" + lastLogin +
                '}';
    }
}

