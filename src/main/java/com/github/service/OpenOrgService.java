/*
 *
 */
package com.github.service;

import java.util.List;
import javax.annotation.Resource;

import com.github.mapper.OpenOrgMapper;
import com.github.model.OpenOrg;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class OpenOrgService {

    @Resource private OpenOrgMapper openOrgMapper;

    /** 添加 */
    public void add(OpenOrg openOrg) {
        this.openOrgMapper.add(openOrg);
    }
    /** 删除 */
    @CacheEvict(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)")
    public void delete(Integer id) {
        this.openOrgMapper.delete(id);
    }
    /** 修改 */
    @CachePut(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#openOrg.id)")
    public OpenOrg update(OpenOrg openOrg) {
        this.openOrgMapper.update(openOrg);
        return openOrg;
    }
    /** 查看 - 从Cache中获取对象 */
    @Cacheable(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)", unless="#result eq null")
    public OpenOrg get(Integer id) {
        return this.openOrgMapper.get(id);
    }
    /** 获取列表 */
    public List<OpenOrg> getList() {
        return this.openOrgMapper.getList();
    }
    /** 获取条件列表 */
    public List<OpenOrg> getOpenOrgList(OpenOrg openOrg) {
        return this.openOrgMapper.getOpenOrgList(openOrg);
    }

    public List<OpenOrg> getOpenOrgListQuery(Integer page,Integer pageSize,String cityCode) {
        OpenOrg openOrg = new OpenOrg();
        if(StringUtils.isNotBlank(cityCode)){
            openOrg.setCityCode(cityCode);
        }

        openOrg.setStatus(1);
        PageHelper.startPage(page, pageSize);
        return openOrgMapper.getOpenOrgList(openOrg);
    }
    /* --------------------------------------------------- */
}

