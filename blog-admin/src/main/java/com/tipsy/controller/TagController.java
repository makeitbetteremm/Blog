package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.PageVo;
import com.tipsy.domain.vo.TagVo;
import com.tipsy.entity.Tag;
import com.tipsy.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 标签 前端控制器
 * </p>
 *
 * @author lys
 * @since 2023-09-15
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize, TagVo tagVo){
        return tagService.pageTagList(pageNum,pageSize,tagVo);
    }
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag() {
        List<TagVo> list = tagService.listAllTag();
        return ResponseResult.okResult(list);
    }

    @PostMapping
    public ResponseResult addTag(@RequestBody TagVo tagVo) {
        return tagService.addTage(tagVo);
    }

    @PutMapping
    public ResponseResult editTag(@RequestBody TagVo tagVo) {
        return tagService.editTag(tagVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable("id") Long id) {
        return tagService.deleteTag(id);
    }
}
