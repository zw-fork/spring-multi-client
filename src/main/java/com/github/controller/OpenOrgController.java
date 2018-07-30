package com.github.controller;

import com.github.model.OpenOrg;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.service.OpenOrgService;
import com.github.util.PageUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by leolin on 7/20/2018.
 */
@RestController
@RequestMapping("org")
public class OpenOrgController {

    @Resource
    private OpenOrgService openOrgService;

    /**
     * 实现分页查询和分页信息返回。
     * @param pageNum
     * @param pageSize
     * @param areaCode
     * @return
     */
    @RequestMapping("list")
    public PageInfo list(Integer pageNum,Integer pageSize,String areaCode){
        if(StringUtils.isNotBlank(areaCode)){
            areaCode = areaCode.replace("00","__");
        }
        pageNum = PageUtils.defaultPage(pageNum);
        pageSize = PageUtils.defaultPage(pageSize);
        List<OpenOrg> list = openOrgService.getOpenOrgListQuery(pageNum,pageSize,areaCode);

        PageInfo<OpenOrg> pageObj = new PageInfo<>(list);
        return pageObj;
    }
}
