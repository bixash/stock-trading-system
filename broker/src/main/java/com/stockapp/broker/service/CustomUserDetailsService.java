package com.stockapp.broker.service;

import com.stockapp.broker.models.Client;
import com.stockapp.broker.dto.ClientPrincipal;
import com.stockapp.broker.repository.ClientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;

    public CustomUserDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Client> user = clientRepository.findById(username);
        if(user.isEmpty()){
            System.out.println("User Not Found");
            throw new UsernameNotFoundException("User not found");
        }
        return new ClientPrincipal(user.get());



    }
}
