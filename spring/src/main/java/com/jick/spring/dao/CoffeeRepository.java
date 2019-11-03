package com.jick.spring.dao;

import com.jick.spring.pojo.Coffee;
import org.springframework.data.repository.CrudRepository;

public interface CoffeeRepository extends CrudRepository<Coffee, Long> {
}
