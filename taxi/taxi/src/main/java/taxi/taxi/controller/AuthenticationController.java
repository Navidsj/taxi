package taxi.taxi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import taxi.taxi.dto.LoginResponseDto;
import taxi.taxi.dto.LoginUserDto;
import taxi.taxi.dto.RegisterUserDto;
import taxi.taxi.model.User;
import taxi.taxi.service.AuthenticationService;
import taxi.taxi.service.JwtService;

@RestController
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final ObjectMapper jacksonObjectMapper;


    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService, ObjectMapper jacksonObjectMapper) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) throws InterruptedException, JsonProcessingException, BadRequestException {
        return authenticationService.signup(registerUserDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginUserDto loginUserDto) throws JsonProcessingException {
        return authenticationService.authenticate(loginUserDto);
    }



}