package com.dao;

import java.util.List;
import java.util.Map;

public interface ServiceProviderDao {
    List<Map<String,Object>> getServices();
    List<Map<String,Object>> getAllMyServices(int serviceProviderId);
    boolean insertService(int serviceId, int userId, double price, double area, String available, String village,
                                 String taluka, String district, String state, String description, String serviceType, String createDate);
    List<Map<String,Object>> getAllServiceRequest(int serviceProviderId,int serviceId);
    boolean updateServiceRequestById(int id,int serviceProviderId);
    List<Map<String,Object>> getAllServicesByStatus(int serviceProviderId,String status);
}
