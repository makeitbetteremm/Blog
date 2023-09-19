package com.tipsy.controller;

import com.tipsy.domain.ResponseResult;
import com.tipsy.domain.vo.LinkVo;
import com.tipsy.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lys
 * @Date 2023/9/19
 **/
@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult listLink(Integer pageNum, Integer pageSize,String name,String status) {
        return linkService.listLink(pageNum,pageSize,name,status);
    }

    @PostMapping
    public ResponseResult addLink(@RequestBody LinkVo linkVo) {
        return linkService.addLink(linkVo);
    }

    @GetMapping("/{id}")
    public ResponseResult getLink(@PathVariable("id") Long id) {
        return linkService.getLink(id);
    }

    @PutMapping
    public ResponseResult editLink(@RequestBody LinkVo linkVo) {
        return linkService.editLink(linkVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable("id") Long id) {
        linkService.removeById(id);
        return ResponseResult.okResult();
    }

    @PutMapping("/changeLinkStatus")
    public ResponseResult changeLinkStatus(@RequestBody LinkVo linkVo) {
        return linkService.editLink(linkVo);
    }
}
