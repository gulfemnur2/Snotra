package com.example.services;

import com.example.models.BookModel;
import com.example.models.CoverEntity;
import com.example.models.CoverModel;

import java.util.List;

public interface BookInterface<T>{
    T getById(long id);
    List<T> getOrders();
    List<T> searchOrders(String searchTerm);
    long addOne(T newOrder);
    boolean deleteOne(long id);
    T updateOne(long idToUpdate, BookModel updateOrder);
    List<T> sortOrders(String sortField, boolean ascending);
    boolean isOduncSuresiGecti(BookModel kitap);
    List<T> getByKategori(String kategori);
    List<T> getBySayfa(int min, int max);
    List<T> getByMinSayfa(int min);
    List<T> getByMaxSayfa(int max);
}