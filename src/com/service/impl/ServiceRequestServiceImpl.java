package com.service.impl;

import com.dao.ServiceRequestDao;
import com.dao.impl.ServiceRequestDaoImpl;
import com.service.ServiceRequestService;
import java.util.List;
import java.util.Map;

public class ServiceRequestServiceImpl implements ServiceRequestService {
    private final ServiceRequestDao serviceRequestDao;

    public static List<Map<String, Object>> serviceProviders;

    public ServiceRequestServiceImpl() {
        this.serviceRequestDao = new ServiceRequestDaoImpl();
    }

    
    public boolean requestService(int userId, int serviceId,int serviceProviderId, int propertyId, String startDate, String endDate, int duration, double cost) {
        if (duration <= 0 || cost <= 0) {
            System.out.println("Invalid duration or cost.");
            return false;
        }

        return serviceRequestDao.insertServiceRequest(userId, serviceId,serviceProviderId, propertyId, startDate, endDate, duration, cost);
    }

    
    public void viewServiceRequests(int userId) {
		
        List<Map<String, Object>> requests = serviceRequestDao.getAllServiceRequests(userId);
        if (requests.isEmpty()) {
            System.out.println("No service requests found.");
            return;
        }

        for (Map<String, Object> request : requests) {
            System.out.println("Request ID: " + request.get("agreement_id") +
                               ", User ID: " + request.get("user_id") +
                               ", Service ID: " + request.get("service_id") +
                               ", Property ID: " + request.get("property_id") +
                               ", Status: " + request.get("status") +
                               ", Start Date: " + request.get("start_date") +
                               ", End Date: " + request.get("end_date") +
                               ", Duration: " + request.get("time_duration") +
                               ", Cost: " + request.get("cost"));
        }
    }
	
	
	public void viewAllServices() {
		List<Map<String, Object>> services = serviceRequestDao.getAllServices();

		if (services.isEmpty()) {
			System.out.println("No services found.");
			return;
		}

		for (Map<String, Object> service : services) {
			System.out.println("Service ID: " + service.get("service_id") +
							   ", Service Name: " + service.get("service_name"));
		}
	}
	
	
	public void getServiceProviderByLocation(int serviceId, String state, String district, String taluka, String village) {
     serviceProviders = serviceRequestDao.getServiceProviderByLocation(serviceId, state, district, taluka, village);

    if (serviceProviders.isEmpty()) {
        System.out.println("No service providers found.");
        return;
    }

    for (Map<String, Object> provider : serviceProviders) {
        System.out.println("Service Provider ID: " + provider.get("serviceProviderId") +
                           ", Provider Name: " + provider.get("providerName") +
                           ", Service ID: " + provider.get("serviceId") +
                           ", Service Name: " + provider.get("serviceName") +
                           ", Price: " + provider.get("price") +
                           ", Description: " + provider.get("description") +
                           ", Location: " + provider.get("state") + ", " + provider.get("district") + 
                           ", " + provider.get("taluka") + ", " + provider.get("village"));
    }
}


    
    public void getPropertiesById(int userId) {
        List<Map<String, Object>> properties = serviceRequestDao.getPropertiesById(userId);
        if (properties.isEmpty()) {
            System.out.println("No properties found for user ID: " + userId);
            return;
        }

        for (Map<String, Object> property : properties) {
            System.out.print(", Property ID: " + property.get("propertyId"));
             System.out.print(", Village: " + property.get("village"));
            System.out.print(", Type of Land: " + property.get("typeOfLand"));
            System.out.print(", Area (Acre): " + property.get("areaAcre"));
            System.out.print(", Area (Guntha): " + property.get("areaGuntha"));

            //System.out.println("Farmer ID     : " + property.get("farmerId"));
            //System.out.println("Farmer ID     : " + property.get("farmerId"));
            // System.out.println("Taluka        : " + property.get("taluka"));
            // System.out.println("District      : " + property.get("district"));
            // System.out.println("State         : " + property.get("state"));
           // System.out.println("Status        : " + property.get("status"));
           // System.out.println("Created Date  : " + property.get("createDate"));
        }
    }

	

}
