package com.example.demo.src.content;

import com.example.demo.src.content.model.GetContentRes;
import com.example.demo.src.content.model.PatchContentReq;
import com.example.demo.src.content.model.PostContentReq;
import com.example.demo.src.content.model.genre.PatchGenreReq;
import com.example.demo.src.content.model.genre.PostGenreReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ContentDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetContentRes> getContents(){
        String getContQuery = "select contentId,conName,releseYear,screenTime,conPoster,conDescript,hits,recentHits,director,actor,writer, series,viewLimit,nation from Content;";
        return this.jdbcTemplate.query(getContQuery,
                (rs,rowNum)-> new GetContentRes(
                        rs.getInt("contentId"),
                        rs.getString("conName"),
                        rs.getString("releseYear"),
                        rs.getString("screenTime"),
                        rs.getString("conPoster"),
                        rs.getString("conDescript"),
                        rs.getString("hits"),
                        rs.getString("recentHits"),
                        rs.getString("director"),
                        rs.getString("actor"),
                        rs.getString("writer"),
                        rs.getString("series"),
                        rs.getString("viewLimit"),
                        rs.getString("nation")
                ));
    }
    public GetContentRes getContent(int contentId){
        String getContQuery = "select contentId,conName,releseYear,screenTime,conPoster,conDescript,hits,recentHits,director,actor,writer, series,viewLimit,nation from Content where contentId=?;";
        int getContParams=contentId;
        return this.jdbcTemplate.queryForObject(getContQuery,
                (rs,rowNum)-> new GetContentRes(
                        rs.getInt("contentId"),
                        rs.getString("conName"),
                        rs.getString("releseYear"),
                        rs.getString("screenTime"),
                        rs.getString("conPoster"),
                        rs.getString("conDescript"),
                        rs.getString("hits"),
                        rs.getString("recentHits"),
                        rs.getString("director"),
                        rs.getString("actor"),
                        rs.getString("writer"),
                        rs.getString("series"),
                        rs.getString("viewLimit"),
                        rs.getString("nation")
                ),getContParams);
    }

    public int createCont(PostContentReq postContentReq){
        String createContQuery = "insert into Content VALUES (null,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,?,?,?,?,?,?,1,?,?,?,?,?,?)";
        Object[] createContParams = new Object[]{postContentReq.getConName(),postContentReq.getReleseYear(),postContentReq.getScreenTime(),postContentReq.getConPoster(),postContentReq.getConDescript(),postContentReq.getHits(),postContentReq.getRecentHits(),postContentReq.getDirector(),postContentReq.getActor(),postContentReq.getWriter(),postContentReq.getSeries(),postContentReq.getViewLimit(),postContentReq.getNation()};
        this.jdbcTemplate.update(createContQuery,createContParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public void deleteCont (int contentId){
        String deleteQuery = "delete from Content where contentId=?";
        int deleteParams = contentId;
        this.jdbcTemplate.update(deleteQuery,deleteParams);
    }

    public int modifyCont(PatchContentReq patchContentReq){
        String modifyQuery = "update Content set "+ patchContentReq.getModItem()+" = ?, updateAt=CURRENT_TIMESTAMP where contentId = ?";
        Object[] modifyParams = new Object[]{patchContentReq.getValue(), patchContentReq.getContentId()};

        return this.jdbcTemplate.update(modifyQuery,modifyParams);
    }

    public int createGenre(PostGenreReq postGenreReq){
        String createGenQuery = "insert into Genre VALUES (null, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,?,1)";
        String createGenParams = postGenreReq.getGenreTag();
        this.jdbcTemplate.update(createGenQuery,createGenParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyGenre(PatchGenreReq patchGenreReq){
        String modifyQuery = "update Genre set genreTag = ?, updateAt=CURRENT_TIMESTAMP where genreId = ?";
        String modifyParams = patchGenreReq.getGenreTag();

        return this.jdbcTemplate.update(modifyQuery,modifyParams);
    }

    public void deleteGenre (int genreId){
        String deleteQuery = "delete from Content where genreId=?";
        int deleteParams = genreId;
        this.jdbcTemplate.update(deleteQuery,deleteParams);
    }
}
