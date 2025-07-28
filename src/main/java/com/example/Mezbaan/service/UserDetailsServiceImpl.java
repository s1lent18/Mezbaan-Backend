package com.example.Mezbaan.service;

import com.example.Mezbaan.database.models.Users;
import com.example.Mezbaan.database.models.Vendor;
import com.example.Mezbaan.database.repository.UsersRepository;
import com.example.Mezbaan.database.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Users> users = usersRepository.findByEmail(email);

        if (users.isPresent()) {
            return createUserDetails(users.get(), "USERS");
        }

        Optional<Vendor> vendor = vendorRepository.findByEmail(email);

        if (vendor.isPresent()) {
            return createUserDetails(vendor.get(), "VENDORS");
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }

    private UserDetails createUserDetails(Object user, String role) {
        if (user instanceof Users users) {
            return new org.springframework.security.core.userdetails.User(
                    users.getEmail(),
                    users.getPassword(),
                    List.of(new SimpleGrantedAuthority(role))
            );
        }
        if (user instanceof Vendor vendor) {
            return new org.springframework.security.core.userdetails.User(
                    vendor.getEmail(),
                    vendor.getPassword(),
                    List.of(new SimpleGrantedAuthority(role))
            );
        }
        throw new IllegalArgumentException("Unknown user type");
    }
}