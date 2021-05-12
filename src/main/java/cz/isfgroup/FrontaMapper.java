package cz.isfgroup;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontaMapper {

    @Select("SELECT * FROM fronta")
    List<Fronta> getAll();

    @Insert("INSERT INTO fronta (noderef, edid, davkaid, status) VALUES(#{noderef}, #{edid}, #{davkaid}, #{status})")
    void insert(Fronta fronta);


}
