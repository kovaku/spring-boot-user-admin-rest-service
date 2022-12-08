package com.github.kovaku.user.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.kovaku.user.presentation.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByName(String name);
}
