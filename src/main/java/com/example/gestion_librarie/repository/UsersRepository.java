package com.example.gestion_librarie.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.gestion_librarie.model.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    //boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    /*boolean existsByTelephone(String telephone);

    Users findByEmail(String email);*/

    Users findByUsername(String username);

    //Users findByTelephone(String telephone);

    //List<Users> findAllByOrderByNomAsc();
}
