package com.dao;

import java.util.List;
import java.util.Map;

public interface LandownerDao {

    boolean insertProperty(int farmerId, String village, String taluka, String district, String state, 
    String typeOfLand, String landImage, String documentImage, double areaAcre, 
    double leasePrice, double areaGuntha, String status,String createDate);
    List<Map<String,Object>> getAllProperties(int id);
    List<Map<String,Object>> getAllPropertyRequests(int id);
    boolean updateRequestForProperty(int agreementId);
    List<Map<String,Object>> getAgreementsByUserAndStatus(int userId, String status);
    
}
