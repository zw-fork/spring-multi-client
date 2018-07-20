package com.github.service;

import com.github.Application;
import com.github.model.CourseType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 测试
 * Created by leolin on 12/27/2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)  // 指向到 根目录下的Application
public class CourseTypeTest {

    @Autowired
    private CourseTypeService courseTypeService;


    /**
     * 插入分片测试
     */
    @Test
    public void save(){
        Integer flag = 300;
        Integer status = 200;
        for(int i = 1 ;i< 20 ; i++) {
            CourseType courseType = new CourseType();
            courseType.setTypeName(i);
            courseType.setStatus(status ++);
            courseTypeService.insert(courseType);
            System.out.println(courseType.toString());

//            for(int j=0; j < 4;j++ ){
//                Course course = new Course();
//                course.setCourseId(courseType.getId());
//                course.setCourseName("course_"+i+"_"+j);
//                course.setIddb01(i);
//                course.setIdtb02(j);
//                courseService.add(course);
//                System.out.println(course.toString());
//            }

            CourseType courseType2 = new CourseType();
            courseType2.setTypeName(i);
            courseType2.setStatus(status ++);
            courseTypeService.insert(courseType2);

        }
    }


    /**
     * 查询分片测试
     */
    @Test
    public void testQueryPage(){
        CourseType courseType = new CourseType();

        List<CourseType> list1 = courseTypeService.selectTypeByCondition(courseType,1,50);
//        List<CourseType> list2 = courseTypeService.selectTypeByCondition(courseType,1,4);
//        CacheManager ;

        for(CourseType ct : list1) {
            System.out.println(ct.toString());
        }
//        System.out.println(list2.toString());

    }

    @Test
    public void testQueryPage2(){
        System.out.println(System.currentTimeMillis());
        CourseType courseType = new CourseType();
        List<CourseType> list1 = courseTypeService.selectTypeByCondition2(courseType, 1, 2);
        System.out.println(list1.toString());
        System.out.println(System.currentTimeMillis());

        List<CourseType> list2 = courseTypeService.selectTypeByCondition2(courseType, 1, 2);


        System.out.println(list2.toString());
        System.out.println(System.currentTimeMillis());
    }
}
