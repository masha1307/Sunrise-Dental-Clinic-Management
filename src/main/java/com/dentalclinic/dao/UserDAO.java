package com.dentalclinic.dao;

import com.dentalclinic.model.User;

public interface UserDAO {

    boolean login(String username, String password);

    User getUserByUsername(String username);

}