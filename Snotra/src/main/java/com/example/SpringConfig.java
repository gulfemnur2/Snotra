package com.example;

import com.example.models.BookModel;
import com.example.services.BookInterface;
import com.example.services.BooksServiceForRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Autowired
    DataSource dataSource;

    @Bean(name="ordersDAO")
    @RequestScope
    public BookInterface<BookModel> getDataService() {
        return new BooksServiceForRepository(dataSource);
    }
}