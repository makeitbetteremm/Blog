package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.entity.BlogComment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-12
 */
public interface BlogCommentService extends IService<BlogComment> {

    /**
     * 查询评论
     *
     * @param commentType
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    /**
     * 添加评论
     * @param comment
     * @return
     */
    ResponseResult addComment(BlogComment comment);
}
