package com.service;

public interface ServiceRequestService {
    boolean requestService(int userId, int serviceId,int serviceProviderId, int propertyId, String startDate, String endDate, int duration, double cost);
    void viewServiceRequests(int userId);
	void viewAllServices();
	void getServiceProviderByLocation(int serviceId, String state, String district, String taluka, String village);
    void getPropertiesById(int userId);
	
	
}
