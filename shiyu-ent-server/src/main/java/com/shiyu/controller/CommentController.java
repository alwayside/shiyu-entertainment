package com.shiyu.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.shiyu.authority.AuthorityCenter;
import com.shiyu.entity.model.CommentDto;
import com.shiyu.entity.repository.CommentDo;
import com.shiyu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private AuthorityCenter authorityCenter;

    @Autowired
    private CommentService commentService;

    @GetMapping("/article/{articleId}")
    public ResponseEntity<?> getCommentArticle(@PathVariable("articleId") Long articleId,
                                            @RequestParam Integer pageNum,
                                            @RequestParam Integer pageSize,
                                            HttpServletRequest request) throws Throwable {
        PageInfo<CommentDto> results = commentService.searchArticleComment(articleId, new Page(pageNum, pageSize));
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @PostMapping("/article/{articleId}")
    public ResponseEntity<?> commentArticle(@PathVariable("articleId") Long articleId,
                                            @RequestBody CommentDo commentDo,
                                            HttpServletRequest request) throws Throwable {
        try {
            authorityCenter.check(request);
        } catch (Throwable e) {
            return new ResponseEntity<>(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED);
        }
        Long userId = authorityCenter.getUserId(request);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        commentDo.setUserId(userId);
        commentService.saveArticleComment(articleId, commentDo);
        return new ResponseEntity<>(commentDo, HttpStatus.OK);
    }
}
