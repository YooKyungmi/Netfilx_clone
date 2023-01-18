package com.example.demo.src.content;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.content.model.PatchContentReq;
import com.example.demo.src.content.model.PostContentReq;
import com.example.demo.src.content.model.PostContentRes;
import com.example.demo.src.content.model.genre.PatchGenreReq;
import com.example.demo.src.content.model.genre.PostGenreReq;
import com.example.demo.src.content.model.genre.PostGenreRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.MODIFY_FAIL_USERNAME;

@Service
public class ContentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ContentDao contentDao;
    private final ContentProvider contentProvider;
    private JwtService jwtService;

    @Autowired
    public ContentService(ContentDao contentDao, ContentProvider contentProvider, JwtService jwtService) {
        this.contentDao = contentDao;
        this.contentProvider = contentProvider;
        this.jwtService = jwtService;
    }

    public PostContentRes createCont(PostContentReq postContentReq) throws BaseException {
        try {
            int contentId = contentDao.createCont(postContentReq);
            return new PostContentRes(contentId);
        } catch (Exception exception) {
            logger.error("Content - createCont Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteCont(int contentId) throws BaseException {
        try {
            contentDao.deleteCont(contentId);
        } catch (Exception exception) {
            logger.error("Content - deleteCont Service Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void modifyCont(PatchContentReq patchContentReq) throws BaseException{
        try{
            int result = contentDao.modifyCont(patchContentReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_USERNAME);
        } catch (Exception exception) {
            logger.error("Content - modifyCont Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostGenreRes createGenre(PostGenreReq postGenreReq) throws BaseException {
        try {
            int genreId = contentDao.createGenre(postGenreReq);
            return new PostGenreRes(genreId);
        } catch (Exception exception) {
            logger.error("Content - createGenre Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyGenre(PatchGenreReq patchGenreReq) throws BaseException{
        try{
            int result = contentDao.modifyGenre(patchGenreReq);
            if (result == 0)
                throw new BaseException(MODIFY_FAIL_USERNAME);
        } catch (Exception exception) {
            logger.error("Content - modifyGenre Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteGenre(int genreId) throws BaseException {
        try {
            contentDao.deleteGenre(genreId);
        } catch (Exception exception) {
            logger.error("Content - deleteGenre Service Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

}
