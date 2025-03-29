package com.dao;


import java.util.Map;

public interface UserDao {

    Map<String,Object> Login(String contact,String password);


    int Register(String name,String contact,String email,String address,String password,String typeOfUser);
    
}
