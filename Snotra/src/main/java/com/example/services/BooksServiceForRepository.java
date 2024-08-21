
package com.example.services;

import com.example.models.BookEntity;
import com.example.models.BookModel;
import com.example.models.CoverModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BooksServiceForRepository implements BookInterface {

    @Autowired
    BooksRepository booksRepository;
    private JdbcTemplate jdbcTemplate;

    public BooksServiceForRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    ModelMapper modelMapper = new ModelMapper();

    @Override
    public BookModel getById(long id) {
        BookEntity entity = booksRepository.findById(id).orElse(null);
        if (entity == null) {
            throw new RuntimeException("Kitap bulunamadı");
        }
        return modelMapper.map(entity, BookModel.class);
    }

    @Override
    public List<BookModel> getOrders() {
        Iterable<BookEntity> orderEntities = booksRepository.findAll();
        List<BookModel> models = new ArrayList<BookModel>();
        for (BookEntity item : orderEntities) {
            models.add(modelMapper.map(item, BookModel.class));
        }
        return models;
    }

    @Override
    public List<BookModel> searchOrders(String searchTerm) {
        Iterable<BookEntity> entities = booksRepository.findByBaslikContainingIgnoreCase(searchTerm);
        List<BookModel> orders = new ArrayList<BookModel>();
        for (BookEntity entity : entities) {
            orders.add(modelMapper.map(entity, BookModel.class));
        }
        return orders;
    }

    @Override
    public long addOne(Object newOrder) {
        BookEntity entity = modelMapper.map(newOrder, BookEntity.class);
        BookEntity result = booksRepository.save(entity);
        if (result == null) {
            return 0;
        } else {
            return result.getId();
        }
    }

    @Override
    public boolean deleteOne(long id) {
        booksRepository.deleteById(id);
        return true;
    }

    @Override
    public BookModel updateOne(long idToUpdate, BookModel updateOrder) {
        BookEntity entity = modelMapper.map(updateOrder, BookEntity.class);
        BookEntity result = booksRepository.save(entity);
        BookModel order = modelMapper.map(result, BookModel.class);
        return order;
    }

    @Override
    public List<BookModel> sortOrders(String sortField, boolean ascending) {
        List<BookEntity> sortedEntities;
        switch (sortField) {
            case "baslik":
                sortedEntities = ascending ? booksRepository.findAllByOrderByBaslikAsc() : booksRepository.findAllByOrderByBaslikDesc();
                break;
            case "basimYili":
                sortedEntities = ascending ? booksRepository.findAllByOrderByBasimYiliAsc() : booksRepository.findAllByOrderByBasimYiliDesc();
                break;
            case "ort":
                sortedEntities = ascending ? booksRepository.findAllByOrderByOrtAsc() : booksRepository.findAllByOrderByOrtDesc();
                break;
            case "sayfa":
                sortedEntities = ascending ? booksRepository.findAllByOrderBySayfaAsc() : booksRepository.findAllByOrderBySayfaDesc();
                break;
            default:
                sortedEntities = (List<BookEntity>) booksRepository.findAll();
        }
        List<BookModel> sortedModels = new ArrayList<>();
        for (BookEntity entity : sortedEntities) {
            sortedModels.add(modelMapper.map(entity, BookModel.class));
        }
        return sortedModels;
    }

    @Override
    public boolean isOduncSuresiGecti(BookModel kitap) {
        if (kitap.getOduncAlmaTarihi() == null) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(kitap.getOduncAlmaTarihi().plusDays(14));
    }

    @Override
    public List<BookModel> getByKategori(String kategori) {
        Iterable<BookEntity> orderEntities = booksRepository.findAllByKategori(kategori);
        List<BookModel> models = new ArrayList<>();
        for (BookEntity item : orderEntities) {
            models.add(modelMapper.map(item, BookModel.class));
        }
        return models;
    }

    @Override
    public List<BookModel> getBySayfa(int min, int max) {
        List<BookEntity> entities = booksRepository.findAllBySayfaBetween(min, max);
        List<BookModel> models = new ArrayList<>();
        for (BookEntity entity : entities) {
            models.add(modelMapper.map(entity, BookModel.class));
        }
        return models;
    }

    @Override
    public List<BookModel> getByMinSayfa(int min) {
        List<BookEntity> entities = booksRepository.findAllBySayfaGreaterThan(min);
        List<BookModel> models = new ArrayList<>();
        for (BookEntity entity : entities) {
            models.add(modelMapper.map(entity, BookModel.class));
        }
        return models;
    }

    @Override
    public List<BookModel> getByMaxSayfa(int max) {
        List<BookEntity> entities = booksRepository.findAllBySayfaIsLessThan(max);
        List<BookModel> models = new ArrayList<>();
        for (BookEntity entity : entities) {
            models.add(modelMapper.map(entity, BookModel.class));
        }
        return models;
    }
}