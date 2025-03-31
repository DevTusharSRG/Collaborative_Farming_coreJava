package com.service;

import java.util.List;
import java.util.Map;

public interface GovernmentSchemeService {
    void addGovernmentScheme(String title, double benefitAmount, String startDate, String lastDate, String description);
    void applyForScheme(int farmerId, int schemeId);
    void updateApplicationStatus(int applicationId, String status);
    List<Map<String, Object>> viewApplicationsByStatus(String status);
    List<Map<String, Object>> viewAllSchemes();
}
