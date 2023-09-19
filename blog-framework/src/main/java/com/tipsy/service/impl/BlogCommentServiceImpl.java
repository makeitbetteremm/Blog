package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.CommentVo;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.entity.BlogComment;
import com.tipsy.entity.SysUser;
import com.tipsy.repository.BlogCommentMapper;
import com.tipsy.repository.SysUserMapper;
import com.tipsy.service.BlogCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-12
 */
@Service
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements BlogCommentService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<BlogComment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(BlogConstant.ARTICLE_TYPE.equals(commentType),BlogComment::getArticleId,articleId);
        commentLambdaQueryWrapper.eq(BlogComment::getType,commentType);
        commentLambdaQueryWrapper.eq(BlogComment::getRootId,-1);

        Page<BlogComment> page = new Page<>(pageNum,pageSize);
        page(page,commentLambdaQueryWrapper);

        List<CommentVo> commentList = toCommentVoList(page.getRecords());

        getChildren(commentList);

        return ResponseResult.okResult(new PageVo(commentList,page.getTotal()));
    }

    @Override
    public ResponseResult addComment(BlogComment comment) {
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentVoList(List<BlogComment> list) {
        List<CommentVo> commentList = BeanCopyUtil.copyBeanList(list,CommentVo.class);
        if (CollectionUtils.isEmpty(commentList)) {
            return commentList;
        }
        List<Long> userIdList = commentList.stream().map(CommentVo::getCreateBy).collect(Collectors.toList());
        userIdList.addAll(commentList.stream().filter(c -> -1 != c.getToCommentUserId())
                .map(CommentVo::getToCommentUserId).collect(Collectors.toList()));

        LambdaQueryWrapper<SysUser> sysUserLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysUserLambdaQueryWrapper.in(SysUser::getId,userIdList);
        List<SysUser> sysUsers = sysUserMapper.selectList(sysUserLambdaQueryWrapper);
        Map<Long,String> userNickMap = sysUsers.stream().collect(Collectors.toMap(SysUser::getId,SysUser::getNickName,(k1,k2)->k1));

        commentList.forEach(commentVo -> {
            commentVo.setUsername(userNickMap.get(commentVo.getCreateBy()));
            commentVo.setToCommentUserName(userNickMap.get(commentVo.getToCommentUserId()));
        });

        return commentList;
    }

    private void getChildren(List<CommentVo> commentList) {
        if (CollectionUtils.isEmpty(commentList)) {
            return;
        }
        List<Long> rootIds = commentList.stream().map(CommentVo::getId).collect(Collectors.toList());
        LambdaQueryWrapper<BlogComment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.in(BlogComment::getRootId,rootIds);

        List<BlogComment> childrenComments = list(commentLambdaQueryWrapper);
        List<CommentVo> childrenCommentVoList = toCommentVoList(childrenComments);
        Map<Long,List<CommentVo>> childrenMap = childrenCommentVoList.stream().collect(Collectors.groupingBy(CommentVo::getRootId));
        commentList.forEach(comment -> comment.setChildren(childrenMap.get(comment.getId())));
    }
}
