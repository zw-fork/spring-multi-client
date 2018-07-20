package com.github.service;


import com.github.mapper.BaseMapper;
import io.shardingsphere.core.api.HintManager;

public abstract class BaseService<T> {
    public int deleteByPrimaryKey(Integer id) {
        if(id != null){
            return  getBaseDao().deleteByPrimaryKey(id);
        }
        return 0;
    }

    public int insert(T record) {
        if(record != null){
            HintManager hintManager = HintManager.getInstance();
            hintManager.setMasterRouteOnly();
            return getBaseDao().insert(record);
        }
        return 0;
    }

    public int insertSelective(T record) {
        if(record != null){
            return getBaseDao().insertSelective(record);
        }
        return 0;
    }

    public T selectByPrimaryKey(Integer id) {
        if(id != null){
            return getBaseDao().selectByPrimaryKey(id);
        }
        return null;
    }

    public int updateByPrimaryKeySelective(T record) {
        if(record != null){
            return getBaseDao().updateByPrimaryKeySelective(record);
        }
        return 0;
    }

    public int updateByPrimaryKey(T record) {
        if(record != null){
            return getBaseDao().updateByPrimaryKey(record);
        }
        return 0;
    }

    protected abstract BaseMapper<T> getBaseDao();
}
