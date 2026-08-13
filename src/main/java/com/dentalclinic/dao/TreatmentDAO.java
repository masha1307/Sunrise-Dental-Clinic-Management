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
        String sql = "INSERT INTO treatments (treatment_name) VALUES (?)";
        
        try {
            Connection connection = DBConnection.getConnection();
            
            if (connection == null) {
                System.err.println("ERROR: Database connection is null");
                return false;
            }

            PreparedStatement statement = connection.prepareStatement(sql);
            
            statement.setString(1, treatment.getTreatmentName());

            int rowsInserted = statement.executeUpdate();
            
            System.out.println("Treatment added successfully, rows affected: " + rowsInserted);
            
            return rowsInserted > 0;

        } catch (Exception e) {
            System.err.println("ERROR adding treatment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


}