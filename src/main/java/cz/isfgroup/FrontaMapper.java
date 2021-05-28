package cz.isfgroup;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontaMapper {

    @Select("SELECT * FROM fronta")
    List<FrontaMember> getAll();

    @Select("SELECT * FROM fronta WHERE edid IN (SELECT edid FROM fronta ORDER BY ts ASC LIMIT 1) ORDER BY ts ASC")
    List<FrontaMember> getAllHead();

    @Select("SELECT * FROM fronta WHERE edid IN (SELECT edid FROM fronta ORDER BY ts ASC LIMIT 1) ORDER BY ts ASC LIMIT 1")
    FrontaMember getOldestOfHead();

    @Select("SELECT * FROM fronta WHERE edid = #{edid}")
    List<FrontaMember> getAllByEdid(String edid);

    @Insert("INSERT INTO fronta (noderef, edid, davkaid, status, ts) VALUES(#{noderef}, #{edid}, #{davkaid}, #{status}, #{ts})")
    void insert(FrontaMember frontaMember);
    

}
