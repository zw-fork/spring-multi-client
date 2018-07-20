package com.github.service;

import com.github.mapper.BaseMapper;
import com.github.mapper.CourseTypeMapper;
import com.github.model.CourseType;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseTypeService extends BaseService<CourseType>{

    @Autowired
    private CourseTypeMapper courseTypeDao;

    @Override
    protected BaseMapper getBaseDao() {
        return courseTypeDao;
    }


    /**
     * 查找顶级类别
     * @param courseType
     * @param page
     * @param pageSize
     * @return
     */
    public List<CourseType> selectTopType(CourseType courseType,Integer page,Integer pageSize){
         if(courseType == null){
             courseType = new CourseType();
         }
        return selectTypeByCondition(courseType,page,pageSize);
    }

//    @Cacheable(value= Constants.FRONT_CACHE_NAME , key = "('CourseTypeService:').concat(#root.method.name).concat('：').concat(#page)")
    public List<CourseType> selectTypeByCondition(CourseType courseType,Integer page,Integer pageSize){
        if(courseType != null){
            courseType.setStatus(225);
            PageHelper.startPage(page, pageSize);
            return courseTypeDao.selectByCondition(courseType);
        }
        return null;
    }

    public List<CourseType> selectTypeByCondition2(CourseType courseType,Integer page,Integer pageSize){
        if(courseType != null){
            courseType.setStatus(0);
            PageHelper.startPage(page, pageSize);
            return courseTypeDao.selectByCondition(courseType);
        }
        return null;
    }

    /**
     * 为树形结构提供查询，查询全部
     * @return
     */
//    public List<CourseType> selectAllType() {

//        CourseType courseType = new CourseType();
//        Integer page = 1;
//        Integer pageSize = Integer.MAX_VALUE;
//        courseType.setParentId(Constants.COURSE_TYPE_TOP_PARENT_ID);
//        List<CourseType> parents = selectTypeByCondition(courseType, page, pageSize);
//        if (parents != null) {
//            for (int index = 0, length = parents.size(); index < length; index++) {
//                courseType.setParentId(parents.get(index).getId());
//                List<CourseType> children = selectTypeByCondition(courseType, page, pageSize);
//                parents.get(index).setChildren(children);
//            }
//        }
//        return parents;
//    }


}
