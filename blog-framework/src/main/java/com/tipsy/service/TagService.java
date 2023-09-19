package com.tipsy.service;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.domain.vo.TagVo;
import com.tipsy.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
public interface TagService extends IService<Tag> {

    /**
     * 获取标签列表
     * @param pageNum
     * @param pageSize
     * @param tagVo
     * @return
     */
    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagVo tagVo);

    /**
     * 新增标签
     * @param tagVo
     * @return
     */
    ResponseResult addTage(TagVo tagVo);

    /**
     * 删除标签
     * @param id
     * @return
     */
    ResponseResult deleteTag(Long id);

    /**
     * 获取所有标签
     * @return
     */
    List<TagVo> listAllTag();

    /**
     * 修改标签
     * @param tagVo
     * @return
     */
    ResponseResult editTag(TagVo tagVo);
}
