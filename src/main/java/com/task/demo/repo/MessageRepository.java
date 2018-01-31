package com.task.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.demo.domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>  {

}
