package org.sid.banquetechcodec.services;

import org.sid.banquetechcodec.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserServiceI userServiceI;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userServiceI.findUserByName(userName);
        if(user == null) throw new UsernameNotFoundException(userName);
        boolean enabled = !user.isAccountVerified();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(r->{
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(), enabled, true, true, true, authorities);
    }
}
