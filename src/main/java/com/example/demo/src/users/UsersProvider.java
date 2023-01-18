package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.users.model.GetUsersRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class UsersProvider {
    private final UsersDao usersDao;

    final Logger logger = LoggerFactory.getLogger((this.getClass()));

    @Autowired
    public UsersProvider(UsersDao usersDao){
        this.usersDao=usersDao;
    }

    public GetUsersRes getUser(int userId) throws BaseException{

        try {
            GetUsersRes getUsersRes = usersDao.getUser(userId);
            return getUsersRes;
        }catch (Exception exception){
            logger.error("getUser Provider Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
    public int checkEmail(String email) throws BaseException{
        try{
            return usersDao.checkEmail(email);
        } catch (Exception exception){
            logger.error("App - checkEmail Provider Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
