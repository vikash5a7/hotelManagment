package com.hotelMangments.hotelMangments.servicesImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotelMangments.hotelMangments.entity.Role;
import com.hotelMangments.hotelMangments.entity.User;
import com.hotelMangments.hotelMangments.exception.NotFoundException;
import com.hotelMangments.hotelMangments.repository.UserRepository;
import com.hotelMangments.hotelMangments.request.SignUpRequest;
import com.hotelMangments.hotelMangments.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ObjectMapper mapper;
    private final HistoryEntryService historyEntryService;


    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, ObjectMapper mapper, HistoryEntryService historyEntryService, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.historyEntryService = historyEntryService;
        this.passwordEncoder = passwordEncoder1;
    }


    public void registerUser(SignUpRequest user) {
        User saveUser = mapper.convertValue(user, User.class);
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Role roles = new Role();
        roles.setName("USER");
        saveUser.setRoles(Collections.singleton(roles));
        User save = userRepository.save(saveUser);
        historyEntryService.saveHistory("New User Added ", "New User added, Name:  " + user.getFirstName() + " Id: " + save.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with username or email:" + email));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with id: " + id));
    }


    public User updateUser(Long id, UserRequest updatedUser) {
        User user = getUserById(id);
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        historyEntryService.saveHistory("Updated user details", "user updated, Name:  " + user.getFirstName());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        historyEntryService.saveHistory("Deleted User", "User deleted account:  " + id);
        userRepository.deleteById(id);
    }
}
