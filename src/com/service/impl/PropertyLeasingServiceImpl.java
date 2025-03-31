package com.service.impl;

import com.dao.PropertyLeasingDao;
import com.dao.impl.PropertyLeasingDaoImpl;
import com.service.PropertyLeasingService;
import java.util.*;

public class PropertyLeasingServiceImpl implements PropertyLeasingService {
    private PropertyLeasingDao propertyLeasingDao;

    public PropertyLeasingServiceImpl() {
        this.propertyLeasingDao = new PropertyLeasingDaoImpl();
    }

    public List<Object> viewAllProperties() {
        return propertyLeasingDao.getAllProperties();
    }
    
   public Map<String, Object> getPropertyDetails(int propertyId) {
           return propertyLeasingDao.getPropertyById(propertyId);
       }
   
    public boolean applyForProperty(int propertyId, int ownerId, int userId, String startDate, String endDate, double rentAmount) {
    return propertyLeasingDao.requestProperty(propertyId, ownerId, userId, startDate, endDate, rentAmount);
}


    public List<Object> fetchAgreementsByStatus(String status,int userId) {
        return propertyLeasingDao.getAgreementDetails(status,userId);
    }
}
