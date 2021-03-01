package com.example.demo.service;

import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.dto.Role;
import com.example.demo.repository.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    /*loads a user by his username*/
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        User user = userRepository.findById(id).get();
        if(user == null){
            System.out.println("NULL CHECK: ");
            throw new UsernameNotFoundException("Account with such username doesn't exit");
        }
        List<GrantedAuthority> grantedAuthorities = new LinkedList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getRolename()));
        org.springframework.security.core.userdetails.User user1 = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true,true,true,true,grantedAuthorities);
        return user1;
    }


    public User getUserById(String id){return userRepository.findById(id).get();}
    /*Add books to the db*/
    public void addUser(User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setDeleted(false);
        Role role = new Role();
        roleRepository.save(role);
        user.setRole(role);
        userRepository.save(user);
    }

    /*Shows all users in the db**/
    public Iterable<User> allUsers(){
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    /*Returns a user by id*/
    public ArrayList<User> getId(String id){
        Optional<User> user = userRepository.findById(id);
        ArrayList<User> res = new ArrayList<>();
        user.ifPresent(res::add);
        return res;
    }
    /*Edits a user*/
    public void updateUser(String id, User user){
        User tempUser = userRepository.findById(id).orElseThrow();
        tempUser.setName(user.getName());
        tempUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        tempUser.setSurname(user.getSurname());
        userRepository.save(tempUser);
    }
    /*Deletes user*/
    public void deleteUser(String id){
        User user = userRepository.findById(id).orElseThrow();
        user.setDeleted(true);
        userRepository.save(user);
    }

}
