package com.service;


import java.util.List;
import java.util.Map;

public interface LandownerService {
    boolean insertProperty(int farmerId, String village, String taluka, String district, String state, 
                           String typeOfLand, String landImage, String documentImage, double areaAcre, 
                           double leasePrice, double areaGuntha, String status, String createDate);

    void getAllProperties(int ownerId);

    void getAllPropertyRequests(int ownerId);

    boolean updateRequestForProperty(int agreementId);

    void getAgreementsByUserAndStatus(int userId, String status);
}

