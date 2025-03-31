package com.service.impl;

import com.dao.ServiceProviderDao;
import com.dao.impl.ServiceProviderDaoImpl;
import com.service.ServiceProviderService;
import java.util.List;
import java.util.Map;

public class ServiceProviderServiceImpl implements ServiceProviderService {

    private ServiceProviderDao serviceProviderDao;

    public ServiceProviderServiceImpl() {
        this.serviceProviderDao = new ServiceProviderDaoImpl();
    }

    @Override
    public void getServices() {
        List<Map<String, Object>> services = serviceProviderDao.getServices();
        if (services.isEmpty()) {
            System.out.println("No services found.");
            return;
        }
        for (Map<String, Object> service : services) {
            System.out.println("Service ID: " + service.get("service_id") +
                    ", Service Name: " + service.get("service_name"));
        }
    }

    @Override
    public void getAllMyServices(int serviceProviderId) {
        List<Map<String, Object>> serviceRequests = serviceProviderDao.getAllMyServices(serviceProviderId);
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
        }

        for (Map<String, Object> service : serviceRequests) {
            if (service.get("available").equals("t")) {
                service.put("available", "Available");
            } else {
                service.put("available", "Not Available");
            }
            System.out.println(" Service Id: " + service.get("service_id") +
                    " Service Name: " + service.get("service_name") +
                    ", Price: " + service.get("price") +
                    ", Area: " + service.get("area") +
                    ", Available: " + service.get("available"));
        }
    }

    @Override
    public boolean insertService(int serviceId, int userId, double price, double area, String available, String village,
            String taluka, String district, String state, String description, String serviceType, String createDate) {
        return serviceProviderDao.insertService(serviceId, userId, price, area, available, village, taluka, district,
                state, description, serviceType, createDate);
    }

    @Override
    public void getAllServiceRequest(int serviceProviderId, int serviceId) {
        List<Map<String, Object>> serviceRequests = serviceProviderDao.getAllServiceRequest(serviceProviderId,
                serviceId);
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            return;
        }
        for (Map<String, Object> request : serviceRequests) {
            System.out.println("Agreement ID: " + request.get("agreement_id") +
                    ", User Id: " + request.get("user_id") +
                    ", User Name: " + request.get("user_name") +
                    ", Contact: " + request.get("contact") +
                    ", Service Name: " + request.get("service_name") +
                    ", Status: " + request.get("status") +
                    ", Start Date: " + request.get("start_date") +
                    ", End Date: " + request.get("end_date") +
                    ", Time Duration: " + request.get("time_duration") +
                    ", Cost: " + request.get("cost"));
        }

    }

    @Override
    public boolean updateServiceRequestById(int id, int serviceProviderId) {
        return serviceProviderDao.updateServiceRequestById(id, serviceProviderId);
    }

    @Override
    public void getAllServicesByStatus(int serviceProviderId, String status) {

        List<Map<String, Object>> serviceRequests = serviceProviderDao.getAllServicesByStatus(serviceProviderId,
                status);
        if (serviceRequests.isEmpty()) {
            System.out.println("No service requests found.");
            return;
        }
        for (Map<String, Object> request : serviceRequests) {
            System.out.println("Agreement ID: " + request.get("agreement_id") +
                    ", User Name: " + request.get("user_name") +
                    ", Contact: " + request.get("contact") +
                    ", Service Name: " + request.get("service_name") +
                    ", Status: " + request.get("status") +
                    ", Start Date: " + request.get("start_date") +
                    ", End Date: " + request.get("end_date") +
                    ", Time Duration: " + request.get("time_duration") +
                    ", Cost: " + request.get("cost"));
        }
    }
}