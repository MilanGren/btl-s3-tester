package cz.isfgroup;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DavkaMapper {

    @Insert({"<script>", "INSERT INTO davky(davkaid, status) VALUES",
            "<foreach collection=\"list\" item=\"item\" separator=\",\"> (#{item.davkaid}, #{item.status}) </foreach>",
            "</script>"})
    void insertMany(@Param("list") List<DavkaMember> members);

    @Select("SELECT * FROM davky")
    List<DavkaMember> getAll();

    @Insert("INSERT INTO davky (davkaid, status) VALUES(#{davkaid}, #{status})")
    void insert(DavkaMember davkaMember);

}
