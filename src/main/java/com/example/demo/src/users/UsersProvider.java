package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.users.model.GetUsersRes;
import com.example.demo.src.users.model.PostLoginReq;
import com.example.demo.src.users.model.PostLoginRes;
import com.example.demo.src.users.model.Users;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class UsersProvider {
    private final UsersDao usersDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger((this.getClass()));

    @Autowired
    public UsersProvider(UsersDao usersDao, JwtService jwtService) {
        this.usersDao = usersDao;
        this.jwtService = jwtService;
    }

    public GetUsersRes getUser(int userId) throws BaseException {

            GetUsersRes getUsersRes = usersDao.getUser(userId);
            return getUsersRes;
        //return Optional.of(usersDao.getUser(userId)).orElseThrow(BaseException::new);

    }

    public int checkEmail(String email) throws BaseException {
            return usersDao.checkEmail(email);
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
            Users user = usersDao.getPwd(postLoginReq);
            String encryptPwd;
                encryptPwd = new SHA256().encrypt(postLoginReq.getPassword());


            if(user.getPassword().equals(encryptPwd)){
                int userId = user.getUserId();
                String jwt = jwtService.createJwt(userId);
                return new PostLoginRes(userId,jwt);
            }
            else throw new BaseException(FAILED_TO_LOGIN);

    }


}
