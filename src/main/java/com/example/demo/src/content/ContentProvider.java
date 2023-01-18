package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.content.model.GetContentRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ContentProvider {

    private final ContentDao contentDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ContentProvider(ContentDao contentDao,JwtService jwtService){
        this.contentDao=contentDao;
        this.jwtService=jwtService;
    }

    public List<GetContentRes> getContents() throws BaseException{
        try{
            List<GetContentRes> getContentRes = contentDao.getContents();
            return getContentRes;
        }catch (Exception exception) {
            logger.error("Content - getContents Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetContentRes getContent(int contentId) throws BaseException{
        try{
            GetContentRes getContentRes = contentDao.getContent(contentId);
            return getContentRes;
        }catch (Exception exception){
            logger.error("Content- getContent Provider Error",exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
