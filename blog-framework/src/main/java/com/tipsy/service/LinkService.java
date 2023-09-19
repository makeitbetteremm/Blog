package com.tipsy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.LinkVo;
import com.tipsy.entity.Link;

/**
 * <p>
 * 友链 服务类
 * </p>
 *
 * @author lys
 * @since 2023-09-11
 */
public interface LinkService extends IService<Link> {

    /**
     * 获取友链
     * @return
     */
    ResponseResult getAllLink();

    /**
     * 分页获取友链
     * @param pageNum
     * @param pageSize
     * @param name
     * @param status
     * @return
     */
    ResponseResult listLink(Integer pageNum, Integer pageSize, String name, String status);

    /**
     * 新增友链
     * @param linkVo
     * @return
     */
    ResponseResult addLink(LinkVo linkVo);

    /**
     * 获取友链
     * @param id
     * @return
     */
    ResponseResult getLink(Long id);

    /**
     * 编辑友链
     * @param linkVo
     * @return
     */
    ResponseResult editLink(LinkVo linkVo);
}
