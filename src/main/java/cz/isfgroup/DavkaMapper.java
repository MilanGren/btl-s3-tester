package cz.isfgroup;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DavkaMapper {

    @Select("SELECT * FROM davky")
    List<DavkaMember> getAll();

    @Insert("INSERT INTO davky (davkaid, status) VALUES(#{davkaid}, #{status})")
    void insert(DavkaMember davkaMember);

}
