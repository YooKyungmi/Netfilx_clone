package com.example.demo.src.profile;

import com.example.demo.src.profile.model.Eval.GetEvalRes;
import com.example.demo.src.profile.model.Eval.PostEvalReq;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.Liked.GetLikedRes;
import com.example.demo.src.profile.model.Liked.PostLikedReq;
import com.example.demo.src.profile.model.PatchProfileReq;
import com.example.demo.src.profile.model.PostProfileReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProfileDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){this.jdbcTemplate = new JdbcTemplate(dataSource);}

    public GetProfileRes getProfile(int profileId){
        String getProfileQuery = "select profileId, viewLimit, userNick, proPicture, language from Profile where profileId = ?";
        int getProfileParams = profileId;
        return this.jdbcTemplate.queryForObject(getProfileQuery,
                (rs,rowNum)  -> new GetProfileRes(
                        rs.getInt("profileId"),
                        rs.getString("viewLimit"),
                        rs.getString("userNick"),
                        rs.getString("proPicture"),
                        rs.getString("language")), getProfileParams);

    }

    public int createProfile(PostProfileReq postProfileReq){
        String createQuery = "insert into Profile VALUES (null,100001,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,1,?)";
        Object[] createParams = new Object[]{postProfileReq.getViewLimit(),postProfileReq.getUserNick(),postProfileReq.getProPicture(),postProfileReq.getLanguage()};
        this.jdbcTemplate.update(createQuery,createParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyProfile(PatchProfileReq patchProfileReq){
        String modifyQuery="update Profile set "+patchProfileReq.getModItem()+" = ?, updateAt=CURRENT_TIMESTAMP where profileId = ?";
        Object[] modifyParams=new Object[]{patchProfileReq.getValue(),patchProfileReq.getProfileId()};

        return this.jdbcTemplate.update(modifyQuery,modifyParams);
    }

    public int deleteProfile(int profileId){
        String deleteQuery = "delete from Profile where profileId=?";
        int deleteParams = profileId;
        this.jdbcTemplate.update("SET foreign_key_checks = 0 ");
        int returnVal = this.jdbcTemplate.update(deleteQuery,deleteParams);
        this.jdbcTemplate.update("SET foreign_key_checks = 1 ");
        return returnVal;
    }

    public List<GetEvalRes> getEval(int profileId){
        String getEvalQuery = "select evalContId,profileId,userId,evalCont,score from EvaluatedContent where profileId=?";
        int getEvalParam = profileId;
        return this.jdbcTemplate.query(getEvalQuery,
                (rs,rowNum) -> new GetEvalRes(
                        rs.getInt("evalContId"),
                        rs.getInt("profileId"),
                        rs.getInt("userId"),
                        rs.getString("evalCont"),
                        rs.getBoolean("score")
                ),getEvalParam);
    }

    public void createEval(PostEvalReq postEvalReq){
        String createEvalQuery = "insert into EvaluatedContent VALUES (null,?,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,1)";
        Object[] createEvalParams = new Object[]{postEvalReq.getProfileId(),postEvalReq.getUserId(),postEvalReq.getEvalCont(),postEvalReq.getScore()};
        this.jdbcTemplate.update(createEvalQuery,createEvalParams);
    }

    public void createLiked(PostLikedReq postLikedReq){
        String createLikedQuery = "insert into UserLikeList VALUES (null,?,?,?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,1)";
        Object[] createLikedParams = new Object[]{postLikedReq.getProfileId(),postLikedReq.getUserId(),postLikedReq.getLikeCont()};
        this.jdbcTemplate.update(createLikedQuery,createLikedParams);
    }

    public List<GetLikedRes> getLiked(int profileId){
        String getLikedQuery = "select userLikeId,profileId,userId,likeCont from UserLikeList where profileId=?";
        int getLikedParam = profileId;
        return this.jdbcTemplate.query(getLikedQuery,
                (rs,rowNum) -> new GetLikedRes(
                        rs.getInt("userLikeId"),
                        rs.getInt("profileId"),
                        rs.getInt("userId"),
                        rs.getString("likeCont")
                ),getLikedParam);
    }

    public void deleteLiked(int userLikeId){
        String deleteLikedQuery = "delete from UserLikeList where userLikeId=?";
        int deleteLikedParams=userLikeId;
        this.jdbcTemplate.update(deleteLikedQuery,deleteLikedParams);
    }

}
