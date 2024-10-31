package com.pelmeshka.Cakes.repositories;

import com.pelmeshka.Cakes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
