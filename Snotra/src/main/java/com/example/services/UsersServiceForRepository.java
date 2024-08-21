package com.example.services;

import com.example.models.UserEntity;
import com.example.models.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceForRepository implements UserInterface<UserModel> {

    @Autowired
    private UsersRepository userRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public long addUser(UserModel newUser) {
        UserEntity entity = modelMapper.map(newUser, UserEntity.class);
        UserEntity result = userRepository.save(entity);
        return result != null ? result.getId() : 0;
    }

    @Override
    public UserModel findById(Long id) {
        UserEntity entity = userRepository.findById(id).orElse(null);
        return modelMapper.map(entity, UserModel.class);
    }

    @Override
    public List<UserModel> getUsers() {
        Iterable<UserEntity> orderEntities = userRepository.findAll();
        List<UserModel> models = new ArrayList<>();
        for (UserEntity item : orderEntities) {
            models.add(modelMapper.map(item, UserModel.class));
        }
        return models;
    }

    @Override
    public boolean deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public UserModel update(long idToUpdate, UserModel updateUser) {
        if (userRepository.existsById(idToUpdate)) {
            UserEntity entity = modelMapper.map(updateUser, UserEntity.class);
            entity.setId(idToUpdate);
            UserEntity result = userRepository.save(entity);
            return modelMapper.map(result, UserModel.class);
        }
        return null;
    }

    @Override
    public List<UserModel> searchUser(String searchTerm) {
        List<UserEntity> entities = userRepository.findByKullaniciAdi(searchTerm);
        List<UserModel> models = new ArrayList<>();
        for (UserEntity entity : entities) {
            models.add(modelMapper.map(entity, UserModel.class));
        }
        return models;
    }

    @Override
    public UserModel authenticateUser(String email, String password) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) {
            return null;
        }
        if (userEntity.getSifre().equals(password)) {
            return new UserModel(
                    userEntity.getKullaniciAdi(),
                    userEntity.getSifre(),
                    userEntity.getAd(),
                    userEntity.getSoyad(),
                    userEntity.getEmail(),
                    userEntity.getId(),
                    userEntity.getOduncKitap(),
                    userEntity.getOduncId(),
                    userEntity.getOduncAlmaTarihi());
        }
        return null;
    }

    @Override
    public boolean check(UserModel user, String sifre) {
        UserEntity userEntity = userRepository.findById(user.getId()).orElse(null);

        if (userEntity == null || !userEntity.getSifre().equals(sifre)) {
            return false;
        }

        return true;
    }
}