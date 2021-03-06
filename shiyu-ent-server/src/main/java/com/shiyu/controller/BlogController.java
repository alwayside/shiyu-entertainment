package com.shiyu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.authority.AuthorityCenter;
import com.shiyu.entity.model.ArticleDto;
import com.shiyu.entity.repository.AdminDo;
import com.shiyu.entity.repository.ArticleDo;
import com.shiyu.service.BlogService;
import com.shiyu.utils.TokenUtil;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private AuthorityCenter authorityCenter;


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody ArticleDo articleDo, HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        Long userId = authorityCenter.getUserId(request);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        articleDo.setUserId(userId);
        blogService.saveArticle(articleDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam(value = "title", required = false) String title,
                                   @RequestParam Integer pageNum,
                                   @RequestParam Integer pageSize) {
        PageInfo<ArticleDto> results = blogService.searchArticleByTitle(title, new Page(pageNum, pageSize));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchArticleById(@PathVariable(value = "id") Long id) {
        List<ArticleDto> list= blogService.searchArticleById(id);
        return new ResponseEntity<>(list.get(0), HttpStatus.OK);
    }

    @GetMapping("/search/management/{userId}")
    public ResponseEntity<?> searchByUser(@RequestParam(value = "albumName", required = false) String title,
                                          @PathVariable("userId") Long userId,
                                          @RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize) {
        PageInfo<ArticleDto> results = blogService.searchBlogByUser(title, userId, new Page(pageNum, pageSize));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id,
                                    HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        blogService.deleteArticleById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ArticleDo articleDo,
                                    HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        blogService.updateArticle(articleDo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
