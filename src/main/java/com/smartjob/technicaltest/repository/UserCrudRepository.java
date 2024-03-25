package com.smartjob.technicaltest.repository;

import com.smartjob.technicaltest.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface UserCrudRepository extends CrudRepository<User, BigDecimal> {

    boolean existsByEmail(String email);

}
