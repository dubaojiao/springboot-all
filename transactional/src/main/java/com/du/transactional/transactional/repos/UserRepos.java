package com.du.transactional.transactional.repos;

import com.du.transactional.transactional.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepos extends JpaRepository<User,Integer> {
}
