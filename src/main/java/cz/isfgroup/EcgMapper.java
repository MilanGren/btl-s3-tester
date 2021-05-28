package cz.isfgroup;


import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EcgMapper {

    @Insert({"<script>", "INSERT INTO ecgs(id, ed_id, node_ref, site_node_ref) VALUES",
            "<foreach collection=\"list\" item=\"item\" separator=\",\"> (nextVal('sequence'), #{item.edId}, #{item.nodeRef}, #{item.siteNodeRef}) </foreach>",
            "</script>"})
    void insertMany(@Param("list") List<Ecg> ecgs);

    @Insert("INSERT INTO ecgs(id, ed_id, node_ref, site_node_ref) VALUES(nextVal('sequence'), #{edId}, #{nodeRef}, #{siteNodeRef})")
    void insert(Ecg ecg);

    @Select("SELECT * FROM ecgs")
    @Results({@Result(property = "edId", column = "ed_id"), @Result(property = "nodeRef", column = "node_ref")})
    List<Ecg> getAll();

    @Select("SELECT * FROM ecgs WHERE ed_id = #{edid}")
    @Results({@Result(property = "edId", column = "ed_id"), @Result(property = "nodeRef", column = "node_ref")})
    List<Ecg> getByEdid(String edid);

    @Select({"<script>", "SELECT", " * ", "FROM ecgs", "WHERE  ed_id IN  " + "<foreach item='item' index='index'" +
            " collection='list' open='(' separator=',' close=')'> #{item} </foreach>" + "</script>"})
    @Results({@Result(property = "edId", column = "ed_id"), @Result(property = "nodeRef", column = "node_ref"), @Result(property = "siteNodeRef", column = "site_node_ref")})
    List<Ecg> getByEdidList(List<String> edids);

    @Select({"<script>", "SELECT", " * ", "FROM ecgs", "WHERE site_node_ref=#{siteNodeRef} AND ed_id IN  " + "<foreach item='item' index='index'" +
            " collection='list' open='(' separator=',' close=')'> #{item.edId} </foreach>" + "</script>"
    })
    @Results({@Result(property = "edId", column = "ed_id"), @Result(property = "nodeRef", column = "node_ref"), @Result(property = "siteNodeRef", column = "site_node_ref")})
    List<Ecg> getByEcgList(@Param("list") List<Ecg> edids, @Param("siteNodeRef") String siteNodeRef);

}
