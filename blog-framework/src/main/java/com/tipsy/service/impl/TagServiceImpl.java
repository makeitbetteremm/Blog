package com.tipsy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tipsy.common.BeanCopyUtil;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.domain.vo.TagVo;
import com.tipsy.entity.Tag;
import com.tipsy.repository.TagMapper;
import com.tipsy.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagVo tagVo) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.hasLength(tagVo.getName()),Tag::getName,tagVo.getName());
        queryWrapper.like(StringUtils.hasLength(tagVo.getRemark()),Tag::getRemark,tagVo.getRemark());

        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,queryWrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult addTage(TagVo tagVo) {
        Tag tag = BeanCopyUtil.copyBean(tagVo,Tag.class);
        save(tag);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult deleteTag(Long id) {
        this.baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public List<TagVo> listAllTag() {
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(Tag::getId,Tag::getName);
        List<Tag> list = list(wrapper);
        List<TagVo> tagVos = BeanCopyUtil.copyBeanList(list, TagVo.class);
        return tagVos;
    }

    @Override
    public ResponseResult editTag(TagVo tagVo) {
        Tag tag = BeanCopyUtil.copyBean(tagVo,Tag.class);
        updateById(tag);
        return ResponseResult.okResult();
    }
}
