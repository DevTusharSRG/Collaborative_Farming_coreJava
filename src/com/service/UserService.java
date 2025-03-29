package com.service;

import java.util.Map;

public interface UserService {
    Map<String,Object> Login(String contact,String password);
    boolean userRegister(String name, String contact, String email, String address, String password, String typeOfUser);
}
