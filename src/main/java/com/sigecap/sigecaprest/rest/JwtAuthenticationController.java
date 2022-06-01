package com.sigecap.sigecaprest.rest;



import com.sigecap.sigecaprest.config.JwtTokenUtil;
import com.sigecap.sigecaprest.exception.BusinessException;
import com.sigecap.sigecaprest.model.Usuario;
import com.sigecap.sigecaprest.model.jwt.JwtRequest;
import com.sigecap.sigecaprest.model.jwt.JwtResponse;
import com.sigecap.sigecaprest.repository.UsuarioRepository;
import com.sigecap.sigecaprest.util.BusinessMsgError;
import com.sigecap.sigecaprest.util.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
			throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		validateUser(authenticationRequest.getUsername());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());


		final String token = jwtTokenUtil.generateToken(userDetails);

		final String idUsuario = usuarioRepository.findIdUsuarioByUsername(authenticationRequest.getUsername());

		return ResponseEntity.ok(new JwtResponse(token,idUsuario));
	}

	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new BusinessException(BusinessMsgError.ERROR_USUARIO_INCORRECTO,e);
		}
	}

	private void validateUser(String username) throws Exception{
		Usuario u = usuarioRepository.findByUsername(username).orElse(null);
		if(!u.getIdRol().equals(Constantes.ROL_OK)){
			throw new BusinessException(BusinessMsgError.ERROR_USUARIO_ROL);
		}

		if(!u.getEstado().equals(Constantes.ESTADO_ACTIVO)){
			throw new BusinessException(BusinessMsgError.ERROR_USUARIO_INACTIVO);
		}
	}
}
