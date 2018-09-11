package com.du.transactional.transactional.repos;


import com.du.transactional.transactional.domain.UserImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImgRepos extends JpaRepository<UserImg,Integer> {
}
