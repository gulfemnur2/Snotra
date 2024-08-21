package com.example.services;

import com.example.models.UserModel;
import java.util.List;

public interface UserInterface<T> {
    long addUser(T newUser);
    T findById(Long id);
    List<T> getUsers();
    boolean deleteById(Long id);
    T update(long idToUpdate, UserModel updateOrder);
    List<T> searchUser(String searchTerm);
    UserModel authenticateUser(String email, String sifre);
    boolean check(UserModel user, String sifre);
}