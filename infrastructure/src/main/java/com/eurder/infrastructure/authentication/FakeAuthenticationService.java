package com.eurder.infrastructure.authentication;

import com.eurder.infrastructure.eurderRoles.EurderRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FakeAuthenticationService {

    private final List<ExternalAuthentication> externalAuthentications;

    @Autowired
    public FakeAuthenticationService(List<ExternalAuthentication> externalAuthentications) {
        this.externalAuthentications = new ArrayList<>();
        this.externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("customer").withPassword("customer").withRoles(List.of(EurderRole.CUSTOMER)));
        this.externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername("admin").withPassword("admin").withRoles(List.of(EurderRole.ADMIN)));
    }

    public ExternalAuthentication getUser(String username, String password) {
        return externalAuthentications.stream()
                .filter(externalAuthentication -> externalAuthentication.getUsername().equals(username))
                .filter(externalAuthentication -> externalAuthentication.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }

    public void addUser(String name, String password, List<EurderRole> roles) {
        if (externalAuthentications.stream().map(ExternalAuthentication::getUsername).collect(Collectors.toList()).contains(name)) {
            throw new IllegalArgumentException("username already exists");
        }
        externalAuthentications.add(ExternalAuthentication.externalAuthentication().withUsername(name).withPassword(password).withRoles(roles));
    }
}
