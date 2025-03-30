package com.service;

import java.util.*;

public interface PropertyLeasingService {
    List<Object> viewAllProperties();
    List<Object> fetchAgreementsByStatus(String status,int userId);
    Map<String, Object> getPropertyDetails(int propertyId);
    boolean applyForProperty(int propertyId, int ownerId, int userId, String startDate, String endDate, double rentAmount);
}
