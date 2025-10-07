package com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI;

import com.omarhammad.kdg_backend.restaurants.adapters.in.dto.JwtDTO;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI.requests.LoginRequest;
import com.omarhammad.kdg_backend.restaurants.adapters.in.webAdapter.AuthRestAPI.requests.RegisterOwnerRequest;
import com.omarhammad.kdg_backend.restaurants.ports.in.LoginCmd;
import com.omarhammad.kdg_backend.restaurants.ports.in.LoginUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.RegisterNewOwnerUseCase;
import com.omarhammad.kdg_backend.restaurants.ports.in.RegisterOwnerCmd;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {

    private LoginUseCase loginUseCase;
    private RegisterNewOwnerUseCase registerNewOwnerUseCase;
    private AuthMapper mapper;


    @PostMapping("/login")
    public ResponseEntity<JwtDTO> login(@RequestBody LoginRequest request) {

        LoginCmd cmd = new LoginCmd(request.username(), request.password());
        Jwt jwt = loginUseCase.login(cmd);
        JwtDTO dto = mapper.toJwtDTO(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @PostMapping("/register")
    public ResponseEntity<JwtDTO> register(@RequestBody RegisterOwnerRequest request) {

        RegisterOwnerCmd cmd = mapper.toRegisterOwnerCmd(request);
        Jwt jwt = registerNewOwnerUseCase.register(cmd);
        JwtDTO dto = mapper.toJwtDTO(jwt);

        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


}
