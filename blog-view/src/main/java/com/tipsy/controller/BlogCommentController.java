package com.tipsy.controller;

import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.ResponseResult;
import com.tipsy.entity.BlogComment;
import com.tipsy.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author lys
 * @since 2023-09-12
 */
@RestController
@RequestMapping("/comment")
public class BlogCommentController {
    @Autowired
    private BlogCommentService commentService;

    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        ResponseResult result = commentService.commentList(BlogConstant.ARTICLE_TYPE, articleId, pageNum, pageSize);
        return result;
    }

    @PostMapping
    public ResponseResult addComment(@RequestBody BlogComment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult linkCommentList(Integer pageNum, Integer pageSize) {
        ResponseResult result = commentService.commentList(BlogConstant.COMMENT_TYPE,null,pageNum,pageSize);
        return result;
    }

}
