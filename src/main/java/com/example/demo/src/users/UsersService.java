package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.users.model.GetUsersRes;
import com.example.demo.src.users.model.PatchUsersReq;
import com.example.demo.src.users.model.PostUsersReq;
import com.example.demo.src.users.model.PostUsersRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UsersService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UsersDao usersDao;

    private final UsersProvider usersProvider;

    private JwtService jwtService;


    @Autowired
    public UsersService(UsersDao usersDao,UsersProvider usersProvider,JwtService jwtService){
        this.usersDao=usersDao;
        this.usersProvider=usersProvider;
        this.jwtService=jwtService;
    }

    public PostUsersRes createUser(PostUsersReq postUsersReq) throws BaseException {
        if (usersProvider.checkEmail(postUsersReq.getEmail()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }
        try{
            int userId = usersDao.createUser(postUsersReq);
            return new PostUsersRes(userId);
        }catch (Exception exception){
            logger.error("createUser Service Error", exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyUser(PatchUsersReq patchUsersReq) throws BaseException{
        try{
            int result = usersDao.modifyUser(patchUsersReq);
            if (result ==0)
                throw new BaseException(MODIFY_FAIL_USERNAME);
        }catch (Exception exception){
            logger.error("modifyUser Service Error",exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void DeleteUser(int userId) throws BaseException{
        try {
            usersDao.deleteUser(userId);
        }catch (Exception exception){
            logger.error("DeleteUser Service Error", exception);
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }
}
