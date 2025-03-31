package com.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.service.UserService;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    public UserServiceImpl(){
        this.userDao=new UserDaoImpl();
    }

    
    public Map<String, Object> Login(String contact, String password) {

        Map<String,Object> user=userDao.Login(contact, password);
  
        return user;
    }


    
    public boolean userRegister(String name, String contact, String email, String address, String password,String typeOfUser) {
        if(userDao.Register(name, contact, email, address, password, typeOfUser)>0){
            return true; 
        }
            
        return false;
    }

    
    
    
}
