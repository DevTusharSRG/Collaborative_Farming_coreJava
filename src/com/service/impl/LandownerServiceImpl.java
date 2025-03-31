package com.service.impl;

import com.dao.LandownerDao;
import com.dao.impl.LandownerDaoImpl;
import com.service.LandownerService;
import java.util.List;
import java.util.Map;

public class LandownerServiceImpl implements LandownerService {
    private final LandownerDao landownerDao;

    public LandownerServiceImpl() {
        this.landownerDao = new LandownerDaoImpl();
    }

    
    public void getAllProperties(int userId) {

        for (Map<String, Object> property : landownerDao.getAllProperties(userId)) {
            System.out.println("Property ID: " + property.get("id") +
                    ", Village: " + property.get("village") +
                    ", Taluka: " + property.get("taluka") +
                    ", District: " + property.get("district") +
                    ", State: " + property.get("state") +
                    ", Type of Land: " + property.get("type_of_land") +
                    ", Land Image: " + property.get("land_image") +
                    ", Document Image: " + property.get("document_image") +
                    ", Area (Acre): " + property.get("area_acre") +
                    ", Lease Price: " + property.get("lease_price") +
                    ", Area (Guntha): " + property.get("area_guntha") +
                    ", Status: " + property.get("status"));
        }

    }

    
    public boolean insertProperty(int userId, String village, String taluka, String district, String state,
            String typeOfLand, String landImage, String documentImage,
            double areaAcre, double leasePrice, double areaGuntha,
            String status, String createDate) {
        if (userId <= 0 || areaAcre <= 0 || leasePrice <= 0 || areaGuntha <= 0) {
            System.out.println("Invalid property details. Please check your inputs.");
            return false;
        }
        return landownerDao.insertProperty(userId, village, taluka, district, state,
                typeOfLand, landImage, documentImage,
                areaAcre, leasePrice, areaGuntha,
                status, createDate);
    }

    
    public void getAllPropertyRequests(int userId) {
        List<Map<String, Object>> requests = landownerDao.getAllPropertyRequests(userId);
        if (requests.isEmpty()) {
            System.out.println("No property requests found.");
            // return requests;
        }

        for (Map<String, Object> request : requests) {
            System.out.println("Agreement ID: " + request.get("agreement_id") +
                    ", Property ID: " + request.get("property_id") +
                    ", Tenant Name: " + request.get("tenant_name") +
                    ", Tenant Address: " + request.get("tenant_address") +
                    ", Tenant Contact: " + request.get("tenant_contact") +
                    ", Start Date: " + request.get("start_date") +
                    ", End Date: " + request.get("end_date") +
                    ", Rent Amount: " + request.get("rent_amount"));

        }

    }

    
    public boolean updateRequestForProperty(int agreementId) {
        boolean isUpdated = landownerDao.updateRequestForProperty(agreementId);
        if (isUpdated) {
            System.out.println("Agreement accepted successfully.");
        } else {
            System.out.println("Failed to accept agreement.");
        }
        return isUpdated;
    }

    
    public void getAgreementsByUserAndStatus(int userId, String status) {
        List<Map<String, Object>> agreements = landownerDao.getAgreementsByUserAndStatus(userId, status);
        if (agreements.isEmpty()) {
            System.out.println("No agreements found for the given status.");

        }

        for (Map<String, Object> agreement : agreements) {
            System.out.println("Agreement ID: " + agreement.get("agreement_id") +
                               ", Property ID: " + agreement.get("property_id") +
                               ", Tenant Name: " + agreement.get("tenant_name") +
                               ", Tenant Address: " + agreement.get("tenant_address") +
                               ", Tenant Contact: " + agreement.get("tenant_contact") +
                               ", Start Date: " + agreement.get("start_date") +
                               ", End Date: " + agreement.get("end_date") +
                               ", Rent Amount: " + agreement.get("rent_amount") +
                               ", Payment Status: " + agreement.get("payment_status") +
                               ", Agreement Status: " + agreement.get("agreement_status"));
        }
        

    }
}
