package ru.ekhart86.springboot.springboot2jpaaudit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ekhart86.springboot.springboot2jpaaudit.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
