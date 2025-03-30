package com.dao;

import java.util.*;

public interface PropertyLeasingDao {
    List<Object> getAllProperties();
    Map<String, Object> getPropertyById(int propertyId);
    boolean requestProperty(int propertyId, int ownerId, int userId, String startDate, String endDate, double rentAmount);
    List<Object> getAgreementDetails(String status,int userID);
}
