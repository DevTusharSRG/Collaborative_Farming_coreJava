package com.dao.impl;

import com.dao.ServiceRequestDao;
import com.database.DBConnection;
import java.sql.*;
import java.util.*;

public class ServiceRequestDaoImpl implements ServiceRequestDao {
    private Connection connection;


    public ServiceRequestDaoImpl() {
        this.connection = DBConnection.getConnection();
    }

    //Insert the service request in teh agreement table 
    public boolean insertServiceRequest(int userId, int serviceId, int serviceProviderId, int propertyId, String startDate, String endDate, int timeDuration, double cost) {
		String sql = "INSERT INTO agreement (user_id, service_id, property_id, status, start_date, end_date, time_duration, cost, create_date, service_provider_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, NOW(),?)";

		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setInt(1, userId);
			stmt.setInt(2, serviceId);
			stmt.setInt(3, propertyId);
			stmt.setString(4, "pending"); // Default status
			stmt.setDate(5, java.sql.Date.valueOf(startDate));
			stmt.setDate(6, java.sql.Date.valueOf(endDate));
			stmt.setInt(7, timeDuration);
			stmt.setDouble(8, cost);
			stmt.setInt(9, serviceProviderId); 
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	//Get all service requests from the database wioth respect to the user id

    public List<Map<String, Object>> getAllServiceRequests(int userId) {
        List<Map<String, Object>> requests = new ArrayList<>();
        String sql = "SELECT * FROM agreement where user_id = ? and status='pending'";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
        pstmt.setInt(1, userId); 

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> request = new HashMap<>();
                request.put("agreement_id", rs.getInt("agreement_id"));
                request.put("user_id", rs.getInt("user_id"));
                request.put("service_id", rs.getInt("service_id"));
                request.put("property_id", rs.getInt("property_id"));
                request.put("status", rs.getString("status"));
                request.put("start_date", rs.getString("start_date"));
                request.put("end_date", rs.getString("end_date"));
                request.put("time_duration", rs.getInt("time_duration"));
                request.put("cost", rs.getDouble("cost"));
                requests.add(request);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        return requests;
    }
	
	// Get all services from the database
	public List<Map<String, Object>> getAllServices() {
    List<Map<String, Object>> servicesList = new ArrayList<>();
    String sql = "SELECT service_id, service_name FROM services"; // Select only valid columns

    try (PreparedStatement pst = connection.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

			while (rs.next()) {
				Map<String, Object> service = new HashMap<>();
				service.put("service_id", rs.getInt("service_id"));
				service.put("service_name", rs.getString("service_name"));
				servicesList.add(service);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return servicesList;
	}

	public List<Map<String, Object>> getServiceProviderByLocation(int serviceId, String state, String district, String taluka, String village) {
    List<Map<String, Object>> serviceProviders = new ArrayList<>();
    
    String sql = "SELECT serviceprovider.id AS serviceProviderId, users.name AS providerName, " +
                 "services.service_id AS serviceId, services.service_name AS serviceName, " +
                 "serviceprovider.price, serviceprovider.description, " +
                 "serviceprovider.state, serviceprovider.district, serviceprovider.taluka, serviceprovider.village " +
                 "FROM service_provider serviceprovider " +
                 "JOIN users users ON serviceprovider.user_id = users.user_id " +
                 "JOIN services services ON serviceprovider.service_id = services.service_id " +
                 "WHERE serviceprovider.service_id = ? " +
                 "AND serviceprovider.state = ? " +
                 "AND serviceprovider.district = ? " +
                 "AND serviceprovider.taluka = ? " +
                 "OR serviceprovider.village = ?";

    try (PreparedStatement pst = connection.prepareStatement(sql)) {
        pst.setInt(1, serviceId);
        pst.setString(2, state);
        pst.setString(3, district);
        pst.setString(4, taluka);
        pst.setString(5, village);
        
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> service = new HashMap<>();
                service.put("serviceProviderId", rs.getInt("serviceProviderId"));
                service.put("providerName", rs.getString("providerName"));
                service.put("serviceId", rs.getInt("serviceId"));
                service.put("serviceName", rs.getString("serviceName"));
                service.put("price", rs.getDouble("price"));
                service.put("description", rs.getString("description"));
                service.put("state", rs.getString("state"));
                service.put("district", rs.getString("district"));
                service.put("taluka", rs.getString("taluka"));
                service.put("village", rs.getString("village"));
                serviceProviders.add(service);
            }
            
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    // If no results found, return all records
    if (serviceProviders.isEmpty()) {
        return getAllServiceProviders();
    }

    return serviceProviders;
}

// Method to fetch all service providers if no specific location match is found
public List<Map<String, Object>> getAllServiceProviders() {
    List<Map<String, Object>> allProviders = new ArrayList<>();
    
    String sql = "SELECT serviceprovider.id AS serviceProviderId, users.name AS providerName, " +
                 "services.service_id AS serviceId, services.service_name AS serviceName, " +
                 "serviceprovider.price, serviceprovider.description, " +
                 "serviceprovider.state, serviceprovider.district, serviceprovider.taluka, serviceprovider.village " +
                 "FROM service_provider serviceprovider " +
                 "JOIN users users ON serviceprovider.user_id = users.user_id " +
                 "JOIN services services ON serviceprovider.service_id = services.service_id";

    try (PreparedStatement pst = connection.prepareStatement(sql);
         ResultSet rs = pst.executeQuery()) {

        while (rs.next()) {
            Map<String, Object> service = new HashMap<>();
            service.put("serviceProviderId", rs.getInt("serviceProviderId"));
            service.put("providerName", rs.getString("providerName"));
            service.put("serviceId", rs.getInt("serviceId"));
            service.put("serviceName", rs.getString("serviceName"));
            service.put("price", rs.getDouble("price"));
            service.put("description", rs.getString("description"));
            service.put("state", rs.getString("state"));
            service.put("district", rs.getString("district"));
            service.put("taluka", rs.getString("taluka"));
            service.put("village", rs.getString("village"));
            allProviders.add(service);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return allProviders;
}


	//Get all properties  from the database with respect to the user id

public List<Map<String, Object>> getPropertiesById(int userId) {

    List<Map<String,Object>> properties=new ArrayList<>();
    String sql="select * from property where farmer_id= ?";
    try (PreparedStatement pst = connection.prepareStatement(sql)) {
        pst.setInt(1, userId);
        try (ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                Map<String, Object> property = new HashMap<>();
                property.put("propertyId", rs.getInt("property_id"));
                property.put("farmerId", rs.getInt("farmer_id"));
                property.put("village", rs.getString("village"));
                property.put("taluka", rs.getString("taluka"));
                property.put("district", rs.getString("district"));
                property.put("state", rs.getString("state"));
                property.put("typeOfLand", rs.getString("type_of_land"));
                property.put("landImage", rs.getString("land_image"));
                property.put("documentImage", rs.getString("document_image"));
                property.put("areaAcre", rs.getDouble("area_acre"));
                property.put("leasePrice", rs.getDouble("lease_price"));
                property.put("areaGuntha", rs.getDouble("area_guntha"));
                property.put("status", rs.getString("status"));
                property.put("createDate", rs.getTimestamp("create_date"));

                properties.add(property);
            }
        }
    }catch(SQLException e){
        e.printStackTrace();
        
    }
    //throw new UnsupportedOperationException("Unimplemented method 'getMyProperties'");

    return properties;
}



}
