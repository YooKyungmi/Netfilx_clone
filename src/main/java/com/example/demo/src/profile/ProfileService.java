package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.profile.model.Eval.PostEvalReq;
import com.example.demo.src.profile.model.Liked.PostLikedReq;
import com.example.demo.src.profile.model.PatchProfileReq;
import com.example.demo.src.profile.model.PostProfileReq;
import com.example.demo.src.profile.model.PostProfileRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;

@Service
public class ProfileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileDao profileDao;
    private final ProfileProvider profileProvider;
    private JwtService jwtService;

    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }

    public PostProfileRes CreateProfile(PostProfileReq postProfileReq) throws BaseException {
        try {
            int profileId = profileDao.createProfile(postProfileReq);
            return new PostProfileRes(profileId);
        } catch (Exception exception) {
            logger.error("Profile - CreateProfile Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void ModifyProfile(PatchProfileReq patchProfileReq) throws BaseException {
        try {
            int result = profileDao.modifyProfile(patchProfileReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_USERNAME);
        } catch (Exception exception) {
            logger.error("Profile - modifyProfile Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void DeleteProfile(int profileId) throws BaseException {
        try {
            profileDao.deleteProfile(profileId);
        } catch (Exception exception) {
            logger.error("Profile - DeleteProfile Service Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void createEval(PostEvalReq postEvalReq) throws BaseException {
        try {
            profileDao.createEval(postEvalReq);
        } catch (Exception exception) {
            logger.error("Profile - createEval Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createLiked (PostLikedReq postLikedReq) throws BaseException {
        try {
            profileDao.createLiked(postLikedReq);
        } catch (Exception exception) {
            logger.error("Profile - createLiked Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void DeleteLiked(int userLikeId) throws BaseException{
        try{
            profileDao.deleteLiked(userLikeId);
        }catch (Exception exception){
            logger.error("Profile - DeleteLiked Service Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
