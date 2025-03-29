package com.dao;

import java.util.List;
import java.util.Map;

public interface LandownerDao {

    Boolean insertProperty();
    List<Map<String,Object>> getAllProperties(int id);
    List<Map<String,Object>> getAllPropertyRequests(int id);
    Boolean updateRequestForProperty(int agreementid);
    List<Map<String,Object>> getAllAgreements(int id);
    
}
