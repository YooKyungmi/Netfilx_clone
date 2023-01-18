package com.example.demo.src.content;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.content.model.GetContentRes;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.REQUEST_ERROR;

@RestController
@RequestMapping("/content")
public class ContentController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ContentProvider contentProvider;
    @Autowired
    private final ContentService contentService;
    @Autowired
    private final JwtService jwtService;

    public ContentController(ContentProvider contentProvider,ContentService contentService,JwtService jwtService){
        this.contentProvider=contentProvider;
        this.contentService=contentService;
        this.jwtService=jwtService;
    }

    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetContentRes>> getContents(){
        try{
            List<GetContentRes> getContentRes = contentProvider.getContents();
            return new BaseResponse<>(getContentRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/{contentId}")
    public BaseResponse<GetContentRes> getContent(@PathVariable("contentId") int contentId){
        try{
            GetContentRes getContentRes = contentProvider.getContent(contentId);
            return new BaseResponse<>(getContentRes);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("")
    public BaseResponse createCont(@RequestBody PostContentReq postContentReq){
        try{
            PostContentRes postContentRes = contentService.createCont(postContentReq);
            return new BaseResponse<>(postContentRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @DeleteMapping("/{contentId}")
    public BaseResponse<String> deleteCont (@PathVariable("contentId")int contentId){
        try{
            contentService.deleteCont(contentId);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    List<String> patchList = Arrays.asList("conName","releseYear","screenTime","conPoster","conDescript","director","actor","writer","series","viewLimit","nation");
    @ResponseBody
    @PatchMapping("")
    public BaseResponse<String> modifyCont(@RequestBody PatchContentReq patchContentReq){
        try{
            if (!(patchList.contains(patchContentReq.getModItem())))
                return new BaseResponse<>(REQUEST_ERROR);
            contentService.modifyCont(patchContentReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @PostMapping("/genre")
    public BaseResponse createGenre(@RequestBody PostGenreReq postGenreReq){
        try{
            PostGenreRes postGenreRes = contentService.createGenre(postGenreReq);
            return new BaseResponse<>(postGenreRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PatchMapping("/genre")
    public BaseResponse<String> modifyGenre(@RequestBody PatchGenreReq patchGenreReq){
        try{
            contentService.modifyGenre(patchGenreReq);
            String result = "";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    @ResponseBody
    @DeleteMapping("/genre/{genreId}")
    public BaseResponse<String> deleteGenre (@PathVariable("genreId")int genreId){
        try{
            contentService.deleteGenre(genreId);
            String result="";
            return new BaseResponse<>(result);
        }catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
