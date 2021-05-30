package cz.isfgroup;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FrontaMapper {

    @Insert({"<script>", "INSERT INTO fronta(noderef, edid, davkaid, status, ts) VALUES",
        "<foreach collection=\"list\" item=\"item\" separator=\",\"> (#{item.noderef}, #{item.edid}, #{item.davkaid}, #{item.status}, #{item.ts}) </foreach>",
        "</script>"})
    void insertList(@Param("list") List<FrontaMember> list);

    @Select("SELECT * FROM fronta")
    List<FrontaMember> getAll();

    @Select("" +
        "SELECT edid\n" +
        "FROM  (\n" +
        "    SELECT DISTINCT ON (edid) *\n" +
        "    FROM fronta\n" +
        "    ORDER  BY edid,ts ASC \n" +
        "    ) p\n" +
        "ORDER  BY ts ASC ; " +
        "")
    List<String> getAllEdids();

    @Select("SELECT * FROM fronta WHERE edid IN (SELECT edid FROM fronta ORDER BY ts ASC LIMIT 1) ORDER BY ts ASC")
    List<FrontaMember> getAllHead();

    @Select("SELECT * FROM fronta WHERE edid IN (SELECT edid FROM fronta ORDER BY ts ASC LIMIT 1) ORDER BY ts ASC LIMIT 1")
    FrontaMember getOldestOfHead();

    @Select("SELECT * FROM fronta WHERE edid = #{edid} ORDER BY ts ASC")
    List<FrontaMember> getAllByEdid(String edid);

    @Insert("INSERT INTO fronta (noderef, edid, davkaid, status, ts) VALUES(#{noderef}, #{edid}, #{davkaid}, #{status}, #{ts})")
    void insert(FrontaMember frontaMember);
    

}
