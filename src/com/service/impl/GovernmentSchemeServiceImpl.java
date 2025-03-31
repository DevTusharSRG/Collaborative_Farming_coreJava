package com.service.impl;

import com.dao.GovernmentSchemeDAO;
import com.dao.impl.GovernmentSchemeDAOImpl;
import com.service.GovernmentSchemeService;
import java.util.List;
import java.util.Map;

public class GovernmentSchemeServiceImpl implements GovernmentSchemeService {
    private final GovernmentSchemeDAO schemeDAO = new GovernmentSchemeDAOImpl();

    @Override
    public void addGovernmentScheme(String title, double benefitAmount, String startDate, String lastDate, String description) {
        schemeDAO.insertScheme(title, benefitAmount, startDate, lastDate, description);
    }

    @Override
    public void applyForScheme(int farmerId, int schemeId) {
        schemeDAO.insertApplication(farmerId, schemeId);
    }

    @Override
    public void updateApplicationStatus(int applicationId, String status) {
        schemeDAO.updateApplicationStatus(applicationId, status);
    }

    @Override
    public List<Map<String, Object>> viewApplicationsByStatus(String status) {
        return schemeDAO.getApplicationsByStatus(status);
    }

    @Override
    public List<Map<String, Object>> viewAllSchemes() {
        return schemeDAO.getAllSchemes();
    }
}
