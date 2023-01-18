package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.profile.model.Eval.GetEvalRes;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.Liked.GetLikedRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ProfileProvider {
    private final ProfileDao profileDao;

    final Logger logger = LoggerFactory.getLogger((this.getClass()));

    @Autowired
    public ProfileProvider(ProfileDao profileDao){ this.profileDao=profileDao;}

    public GetProfileRes getProfile(int profileId) throws BaseException{
        try{
            GetProfileRes getProfileRes = profileDao.getProfile(profileId);
            return getProfileRes;
        }catch (Exception exception){
            logger.error("getProfile Provider Error",exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public List<GetEvalRes> getEval(int profileId) throws BaseException{
        try{
            List<GetEvalRes> getEvalRes = profileDao.getEval(profileId);
            return getEvalRes;
        }catch (Exception exception) {
            logger.error("GetEval Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetLikedRes> getLiked(int profileId) throws BaseException{
        try{
            List<GetLikedRes> getLikedRes = profileDao.getLiked(profileId);
            return getLikedRes;
        }catch (Exception exception) {
            logger.error("GetLiked Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
