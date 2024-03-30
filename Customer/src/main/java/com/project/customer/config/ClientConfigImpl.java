package com.project.customer.config;

import com.project.library.model.Customer;
import com.project.library.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.stream.Collectors;

public class ClientConfigImpl implements UserDetailsService {
    @Autowired
    private CustomerRepository clientRepository;
    @Override
    public UserDetails loadUserByUsername(String clientname) throws UsernameNotFoundException {
        Customer client = clientRepository.findByUsername(clientname);
        if(client == null){
            throw new UsernameNotFoundException("This clientname does not exist");
        }

        return new User(client.getUsername(),client.getPassword(),client.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }
}
