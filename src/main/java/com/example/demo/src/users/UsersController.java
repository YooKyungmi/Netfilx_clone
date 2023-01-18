package com.example.demo.src.users;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.users.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/users")
public class UsersController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UsersProvider usersProvider;
    @Autowired
    private final UsersService usersService;

    @Autowired
    private final JwtService jwtService;

    public UsersController(UsersProvider usersProvider,UsersService usersService,JwtService jwtService){
        this.usersProvider=usersProvider;
        this.usersService=usersService;
        this.jwtService=jwtService;

    }

    //회원 조회
    @ResponseBody
    @GetMapping("/{userId}")
    public BaseResponse<GetUsersRes> getUser(@PathVariable("userId") int userId){
        try{
            GetUsersRes getUsersRes = usersProvider.getUser(userId);
            return new BaseResponse<>(getUsersRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //회원가입
    @ResponseBody
    @PostMapping("")
    public BaseResponse createUser(@RequestBody PostUsersReq postUsersReq){
        if(postUsersReq.getEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        //이메일 정규표현
        if(!isRegexEmail(postUsersReq.getEmail())){
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        try{
            PostUsersRes postUsersRes = usersService.createUser(postUsersReq);
            return new BaseResponse<>(postUsersRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    List<String> patchList = Arrays.asList("email","pwd","phoneNum");
    //회원 정보 수정
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyUser(@RequestBody PatchUsersReq patchUsersReq) {
        try {
            if (!(patchList.contains(patchUsersReq.getModItem()))) {
                return new BaseResponse<>(REQUEST_ERROR);
            }
            usersService.modifyUser(patchUsersReq);
            String result="";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/{userId}")
    public BaseResponse<String> DeleteUser(@PathVariable("userId") int userId){
        try{
            usersService.DeleteUser(userId);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
