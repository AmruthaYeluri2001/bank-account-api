package com.thoughtworks.bankaccountapi.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;

public class AccountPrincipal implements UserDetails {
    AccountModel accountModel;

    public AccountPrincipal(AccountModel accountModel) {
        this.accountModel = accountModel;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return accountModel.getPassword();
    }

    @Override
    public String getUsername() {
        return accountModel.getAccountNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
