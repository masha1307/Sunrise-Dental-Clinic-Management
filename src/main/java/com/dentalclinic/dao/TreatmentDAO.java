package com.dentalclinic.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


import com.dentalclinic.model.Treatment;
import com.dentalclinic.util.DBConnection;



public class TreatmentDAO {



    public List<Treatment> getAllTreatments(){


        List<Treatment> treatments =
                new ArrayList<>();


        String sql =
                "SELECT * FROM treatments";



        try{


            Connection connection =
                    DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return treatments;
            }



            PreparedStatement statement =
                    connection.prepareStatement(sql);



            ResultSet resultSet =
                    statement.executeQuery();



            while(resultSet.next()){


                Treatment treatment =
                        new Treatment();



                treatment.setTreatmentId(
                        resultSet.getInt("treatment_id")
                );



                treatment.setTreatmentName(
                        resultSet.getString("treatment_name")
                );

                // price (nullable)
                try {
                    Object obj = resultSet.getObject("price");
                    if (obj != null) {
                        treatment.setPrice(resultSet.getDouble("price"));
                    }
                } catch (Exception ignore) {}

                // duration (nullable)
                try {
                    treatment.setDurationMinutes(resultSet.getString("duration_minutes"));
                } catch (Exception ignore) {}

                treatments.add(treatment);


            }
            
            System.out.println("Retrieved " + treatments.size() + " treatments from database");



        }catch(Exception e){

            System.err.println("ERROR retrieving treatments: " + e.getMessage());
            e.printStackTrace();

        }


        return treatments;


    }

    public boolean addTreatment(Treatment treatment) {
        String sql = "INSERT INTO treatments (treatment_name, price, duration_minutes) VALUES (?, ?, ?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, treatment.getTreatmentName());
            if (treatment.getPrice() != null) {
                statement.setDouble(2, treatment.getPrice());
            } else {
                statement.setNull(2, java.sql.Types.DECIMAL);
            }
            statement.setString(3, treatment.getDurationMinutes());

            int rowsInserted = statement.executeUpdate();
            
            System.out.println("Treatment added successfully, rows affected: " + rowsInserted);
            
            return rowsInserted > 0;

        } catch (Exception e) {
            System.err.println("ERROR adding treatment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean updateTreatment(Treatment treatment) {
        String sql = "UPDATE treatments SET treatment_name = ? WHERE treatment_id = ?";
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, treatment.getTreatmentName());
            statement.setInt(2, treatment.getTreatmentId());
            
            int rowsUpdated = statement.executeUpdate();
            System.out.println("Treatment updated, rows affected: " + rowsUpdated);
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.err.println("ERROR updating treatment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteTreatment(int id) {
        String sql = "DELETE FROM treatments WHERE treatment_id = ?";
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }
            
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            
            int rowsDeleted = statement.executeUpdate();
            System.out.println("Treatment deleted, rows affected: " + rowsDeleted);
            return rowsDeleted > 0;
        } catch (Exception e) {
            System.err.println("ERROR deleting treatment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}