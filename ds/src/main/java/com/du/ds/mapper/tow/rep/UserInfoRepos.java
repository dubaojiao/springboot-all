package com.du.ds.mapper.tow.rep;

import com.du.ds.mapper.one.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "towUserInfoRepos")
public interface UserInfoRepos extends JpaRepository<UserInfo,Integer>{
}
