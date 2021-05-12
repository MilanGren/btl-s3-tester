package cz.isfgroup.mybatis;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontaMapper {

    @Select("SELECT * FROM fronta")
    List<Fronta> getAll();



/*    @Insert("INSERT INTO borec (id, surname) VALUES(#{id}, #{surname})")
    void insert(User user);*/


}
