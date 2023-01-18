package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.Eval.GetEvalRes;
import com.example.demo.src.profile.model.Eval.PostEvalReq;
import com.example.demo.src.profile.model.GetProfileRes;
import com.example.demo.src.profile.model.Liked.GetLikedRes;
import com.example.demo.src.profile.model.Liked.PostLikedReq;
import com.example.demo.src.profile.model.PatchProfileReq;
import com.example.demo.src.profile.model.PostProfileReq;
import com.example.demo.src.profile.model.PostProfileRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProfileProvider profileProvider;
    @Autowired
    private final ProfileService profileService;

    @Autowired
    private final JwtService jwtService;

    public ProfileController(ProfileProvider profileProvider,ProfileService profileService,JwtService jwtService){
        this.profileProvider=profileProvider;
        this.profileService=profileService;
        this.jwtService=jwtService;
    }

    @ResponseBody
    @GetMapping("/{profileId}")
    public BaseResponse<GetProfileRes> getProfile(@PathVariable("profileId") int profileId){
        try{
            GetProfileRes getProfileRes = profileProvider.getProfile(profileId);
            return new BaseResponse<>(getProfileRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @PostMapping("")
    public BaseResponse createProfile(@RequestBody PostProfileReq postProfileReq){
        try{
            PostProfileRes postProfileRes = profileService.CreateProfile(postProfileReq);
            return new BaseResponse<>(postProfileRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    List<String> patchList = Arrays.asList("userNick","proPicture","language","viewLimit");
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyProfile(@RequestBody PatchProfileReq patchProfileReq){
        try{
            if (!(patchList.contains(patchProfileReq.getModItem())))
                return new BaseResponse<>(REQUEST_ERROR);
            profileService.ModifyProfile(patchProfileReq);
            String result = "";
            return new BaseResponse<>(result);
            } catch (BaseException exception) {
                return new BaseResponse<>((exception.getStatus()));
            }
    }

    @ResponseBody
    @DeleteMapping("/{profileId}")
    public BaseResponse<String> DeleteProfile(@PathVariable("profileId") int profileId){
        try{
            profileService.DeleteProfile(profileId);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/eval/{profileId}")
    public BaseResponse<List<GetEvalRes>> getEval(@PathVariable("profileId")int profileId){
        try{
            List<GetEvalRes> getEvalRes = profileProvider.getEval(profileId);
            return new BaseResponse<>(getEvalRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/eval")
    public BaseResponse createEval (@RequestBody PostEvalReq postEvalReq){
        try{
         profileService.createEval(postEvalReq);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/liked")
    public BaseResponse createLiked (@RequestBody PostLikedReq postLikedReq){
        try{
            profileService.createLiked(postLikedReq);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/liked/{profileId}")
    public BaseResponse<List<GetLikedRes>> getLiked(@PathVariable("profileId")int profileId){
        try{
            List<GetLikedRes> getLikedRes = profileProvider.getLiked(profileId);
            return new BaseResponse<>(getLikedRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/liked/{userLikeId}")
    public BaseResponse<String> DeleteLiked(@PathVariable("userLikeId")int userLikeId){
        try{
            profileService.DeleteLiked(userLikeId);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
