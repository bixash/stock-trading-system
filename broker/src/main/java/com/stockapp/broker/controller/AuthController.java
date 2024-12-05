package com.stockapp.broker.controller;


import com.stockapp.broker.dto.AuthResponse;

import com.stockapp.broker.dto.LoginRequest;
import com.stockapp.broker.dto.BaseResponse;
import com.stockapp.broker.dto.SignupRequest;
import com.stockapp.broker.models.Client;
import com.stockapp.broker.service.ClientService;
import com.stockapp.broker.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("broker/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {

    private String authToken;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    private final ClientService clientService;

    public AuthController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("register")
    public BaseResponse register(@RequestBody SignupRequest signupRequest) {
         if(!clientService.isClientExists(signupRequest.getUsername())){
             Client newClient = clientService.registerClient(signupRequest);
             return new BaseResponse(
                     false,
                     "Registered successful!",
                     true,
                     newClient);
         }
        return new BaseResponse(
                true,
                "Username already exists!",
                false,
                null);

    }



    @PostMapping("login")
    public BaseResponse login(@RequestBody LoginRequest loginRequest) {

        AuthResponse authResponse = clientService.loginClient(loginRequest);


        if (authResponse != null) {
            return new BaseResponse(false,
                    "Login successful!",
                    true,
                    authResponse);
        }

        return new BaseResponse(true,
                "Invalid credentials!",
                false,
                null);

    }
    @GetMapping("validate")
    public BaseResponse validateAuthToken(@CookieValue(name = "token") String token) {

        if(clientService.validateToken(token)){

            String username = jwtTokenUtil.extractUsername(token);
            return new BaseResponse(false,
                    "Authenticated user!",
                    true,
                    new AuthResponse(token, username));
        }
        return new BaseResponse(true,
                "Unauthorized user!",
                false,
                null);
    }


    @GetMapping("logout")
    public ResponseEntity<?> logout() {
    ResponseCookie cookie = ResponseCookie.from("token", "")
                .domain("localhost")
                .path("/")
                .maxAge(0)
                .build();
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString()).body("ok");
    }







}





