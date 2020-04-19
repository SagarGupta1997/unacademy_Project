package com.unacademy.repository;


import com.unacademy.pojos.UnacademyUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface UserRepository extends CrudRepository<UnacademyUser, Long>  {

    public List<UnacademyUser> findByName(String name) ;
    public List<UnacademyUser> findByNameAndSurname(String name , String surname) ;



}
