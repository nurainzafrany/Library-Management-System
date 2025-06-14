package com.example.csc3402_lab.librarymanagement.service;

import com.example.csc3402_lab.librarymanagement.model.Borrower;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final Borrower borrower;

    public CustomUserDetails(Borrower borrower) {
        this.borrower = borrower;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_BORROWER"));
    }

    @Override
    public String getPassword() {
        return borrower.getBorrowerPass();
    }

    @Override
    public String getUsername() {
        return borrower.getBorrowerUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // can be linked to a borrower field if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // change as needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // change as needed
    }

    @Override
    public boolean isEnabled() {
        return true; // change as needed
    }

    public Borrower getBorrower() {
        return borrower;
    }
}