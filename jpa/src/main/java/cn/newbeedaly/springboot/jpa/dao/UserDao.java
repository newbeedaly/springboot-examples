package cn.newbeedaly.springboot.jpa.dao;

import cn.newbeedaly.springboot.jpa.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface UserDao extends JpaRepository<User, Long>, Serializable {

}
