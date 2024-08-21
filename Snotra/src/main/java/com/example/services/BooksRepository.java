package com.example.services;

import com.example.models.BookEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BooksRepository extends CrudRepository<BookEntity, Long> {

    List<BookEntity> findByBaslikContainingIgnoreCase(String searchTerm);

    List<BookEntity> findAllByOrderByBaslikAsc();

    List<BookEntity> findAllByOrderByBaslikDesc();

    List<BookEntity> findAllByOrderByBasimYiliAsc();

    List<BookEntity> findAllByOrderByBasimYiliDesc();

    List<BookEntity> findAllByOrderByOrtAsc();

    List<BookEntity> findAllByOrderByOrtDesc();

    List<BookEntity> findAllByOrderBySayfaAsc();
    List<BookEntity> findAllByOrderBySayfaDesc();

    List<BookEntity> findAllByKategori(String kategori);

    List<BookEntity> findAllBySayfaBetween(int min, int max);

    List<BookEntity> findAllBySayfaGreaterThan(int min);

    List<BookEntity> findAllBySayfaIsLessThan(int max);
}
