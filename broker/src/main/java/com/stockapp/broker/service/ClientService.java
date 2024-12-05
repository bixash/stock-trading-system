package com.stockapp.broker.service;

import com.stockapp.broker.dto.*;
import com.stockapp.broker.models.Order;
import com.stockapp.broker.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

import com.stockapp.broker.models.Client;
import com.stockapp.broker.repository.ClientRepository;

import java.util.Objects;

@Service
public class ClientService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    private final ClientRepository clientRepository;

    private final FundsService fundsService;

    public ClientService(ClientRepository clientRepository, FundsService fundsService) {
        this.clientRepository = clientRepository;
        this.fundsService = fundsService;
    }

    public Boolean isClientExists(String clientId) {

     Optional<Client> client = clientRepository.findById(clientId);
        return client.isPresent();

    }


   private String getClientPasswordById(String clientId){
        Optional<Client> optionalClient= clientRepository.findById(clientId);
        if(optionalClient.isPresent()){
            Client client = optionalClient.get();
            return client.getPassword();

        }
        return null;

    }


    public boolean isClientAuthenticated(LoginRequest loginRequest){
        if( isClientExists(loginRequest.getUsername())){
            return Objects.equals(loginRequest.getPassword(), getClientPasswordById(loginRequest.getUsername()));
        }
        return false;
    }


    public Optional<Client> getClient(String username) {
        return clientRepository.findById(username);
    }



    public Client registerClient(SignupRequest signupRequest) {
        Client client = new Client(signupRequest.getUsername(),
                82,
                signupRequest.getPassword(),
                new String[]{
                        "MSFT",
                        "META",
                        "GOOGL",
                        "NICA",
                        "NABIL",
                        "NCELL"});
        client.setPassword(new BCryptPasswordEncoder().encode(client.getPassword()));
        fundsService.createFunds(client.getId());
        return clientRepository.save(client);
    }

    public AuthResponse loginClient(LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        if (authentication.isAuthenticated()) {
           return new AuthResponse(
                   jwtTokenUtil.generateToken(loginRequest.getUsername()),
                  loginRequest.getUsername());
        } else {
            return null;
        }

    }

    public Boolean validateToken(String token) {

        return jwtTokenUtil.validateToken(token, jwtTokenUtil.extractUsername(token));


    }

    public String[] getWatchList(String username) {
        return clientRepository.getWatchList(username);
    }



}




