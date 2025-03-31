package com.dao;

import java.util.List;
import java.util.Map;

public interface GovernmentSchemeDAO {
    void insertScheme(String title, double benefitAmount, String startDate, String lastDate, String description);
    void insertApplication(int farmerId, int schemeId);
    void updateApplicationStatus(int applicationId, String status);
    List<Map<String, Object>> getApplicationsByStatus(String status);
    List<Map<String, Object>> getAllSchemes();
}
