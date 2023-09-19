package com.tipsy.domain.vo;

import com.tipsy.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lys
 * @Date 2023/9/11
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginVo {
    private String token;
    private UserInfo userInfo;
}
