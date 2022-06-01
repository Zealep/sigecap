package com.sigecap.sigecaprest.service;


import com.sigecap.sigecaprest.model.Usuario;
import com.sigecap.sigecaprest.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) {
		Usuario user = usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("usuario no encontrado. " + username));
		return this.userBuilder(user.getNombreUsuario(), user.getClave(), new String[]{"ADMIN"}, true);

	}

	private User userBuilder(String username, String password, String[] roles,
							 boolean active) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return new User(username, password, active, true,
				true, true, authorities);
	}
}