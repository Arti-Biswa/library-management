package com.java.librarymanagement.users.repository;

import com.java.librarymanagement.users.model.User;
import com.java.librarymanagement.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
