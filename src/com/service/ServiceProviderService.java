package com.service;



public interface ServiceProviderService {
    void getServices();
    void getAllMyServices(int serviceProviderId);
    boolean insertService(int serviceId, int userId, double price, double area, String available, String village,
                                 String taluka, String district, String state, String description, String serviceType, String createDate);
    void getAllServiceRequest(int serviceProviderId,int serviceId);
    boolean updateServiceRequestById(int id,int serviceProviderId);;
    void getAllServicesByStatus(int serviceProviderId,String status);
    
}
