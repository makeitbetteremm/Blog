package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.common.constants.BlogConstant;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.LinkVo;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.entity.Category;
import com.tipsy.entity.Link;
import com.tipsy.repository.LinkMapper;
import com.tipsy.service.LinkService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-11
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(Link::getStatus, BlogConstant.NORMAL);

        List<Link> links = list(linkLambdaQueryWrapper);

        List<LinkVo> linkVoList = BeanCopyUtil.copyBeanList(links,LinkVo.class);

        return ResponseResult.okResult(linkVoList);
    }

    @Override
    public ResponseResult listLink(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(name),Link::getName,name);
        queryWrapper.eq(StringUtils.hasLength(status),Link::getStatus,status);

        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);

        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addLink(LinkVo linkVo) {
        Link link = BeanCopyUtil.copyBean(linkVo,Link.class);
        save(link);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getLink(Long id) {
        Link link = getById(id);
        LinkVo linkVo = BeanCopyUtil.copyBean(link,LinkVo.class);
        return ResponseResult.okResult(linkVo);
    }

    @Override
    public ResponseResult editLink(LinkVo linkVo) {
        Link link = BeanCopyUtil.copyBean(linkVo,Link.class);
        updateById(link);
        return ResponseResult.okResult();
    }
}
