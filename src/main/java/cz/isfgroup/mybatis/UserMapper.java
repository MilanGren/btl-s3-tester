package cz.isfgroup.mybatis;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT id, surname FROM borec")
    List<User> getAll();

    @Insert("INSERT INTO borec (id, surname) VALUES(#{id}, #{surname})")
    void insert(User user);


}
