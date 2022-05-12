package com.zr.test.demo.service;

import com.zr.test.demo.model.entity.UserEntity;

public interface IUserService {

   void insert() throws Exception;

    UserEntity getUserByPass(String username, String password);
}
