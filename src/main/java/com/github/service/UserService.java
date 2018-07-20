/*
 * ............................................. 
 * 
 * 				    _ooOoo_ 
 * 		  	       o8888888o 
 * 	  	  	       88" . "88 
 *                 (| -_- |) 
 *                  O\ = /O 
 *              ____/`---*\____ 
 *               . * \\| |// `. 
 *             / \\||| : |||// \ 
 *           / _||||| -:- |||||- \ 
 *             | | \\\ - /// | | 
 *            | \_| **\---/** | | 
 *           \  .-\__ `-` ___/-. / 
 *            ___`. .* /--.--\ `. . __ 
 *        ."" *< `.___\_<|>_/___.* >*"". 
 *      | | : `- \`.;`\ _ /`;.`/ - ` : | | 
 *         \ \ `-. \_ __\ /__ _/ .-` / / 
 *======`-.____`-.___\_____/___.-`____.-*====== 
 * 
 * ............................................. 
 *              佛祖保佑 永无BUG 
 *
 * 佛曰: 
 * 写字楼里写字间，写字间里程序员； 
 * 程序人员写程序，又拿程序换酒钱。 
 * 酒醒只在网上坐，酒醉还来网下眠； 
 * 酒醉酒醒日复日，网上网下年复年。 
 * 但愿老死电脑间，不愿鞠躬老板前； 
 * 奔驰宝马贵者趣，公交自行程序员。 
 * 别人笑我忒疯癫，我笑自己命太贱； 
 * 不见满街漂亮妹，哪个归得程序员？
 *
 * 北纬30.√  154518484@qq.com
 */
package com.github.service;

import com.github.mapper.UserMapper;
import com.github.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Resource private UserMapper userMapper;

    /** 添加 */
    public void add(User user) {
        this.userMapper.add(user);
    }
    /** 删除 */
    @CacheEvict(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)")
    public void delete(Integer id) {
        this.userMapper.delete(id);
    }
    /** 修改 */
    @CachePut(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#user.id)")
    public User update(User user) {
        this.userMapper.update(user);
        return user;
    }
    /** 查看 - 从Cache中获取对象 */
    @Cacheable(value="objectCache", key="(#root.targetClass.getSimpleName()).concat(':id:').concat(#id)", unless="#result eq null")
    public User get(Integer id) {
        return this.userMapper.get(id);
    }
    /** 获取列表 */
    public List<User> getList() {
        return this.userMapper.getList();
    }
    /** 获取条件列表 */
    public List<User> getUserList(User user) {
        return this.userMapper.getUserList(user);
    }
    /* --------------------------------------------------- */

    /** 查看 - 从Cache中获取对象 */
    public User getByPhone(String phone) {
        return this.userMapper.getByPhone(phone);
    }



    /** 获取用户的递归上级 */
    public List<User> getParentList(String phone) {

        User user = this.getByPhone(phone);
        String parentPhone = user.getParentPhone();
        List<User> parentList = new ArrayList<>();

        while (StringUtils.isNotEmpty(parentPhone)) {

            User parent = this.getByPhone(parentPhone);
            if (parent != null) {
                parentList.add(parent);
                parentPhone = parent.getParentPhone();
            } else {
                parentPhone = null;
            }
        }

        return parentList;
    }

    /** 获取用户的直属下级 */
    public List<User> getChildList(String phone) {
        User query = new User();
        query.setParentPhone(phone);
        return this.getUserList(query);
    }

    /** 获取用户的所有直属下级 */
    public void getAllChildList(String phone, List<User> childList) {
        List<User> childList0 = this.getChildList(phone);
        for (User user : childList0) {
            childList.add(user);
            getAllChildList(user.getPhone(), childList);
        }
    }

    public List<User> getShareListByCode(String areaCode) {
        return userMapper.getShareListByCode(areaCode);
    }

    /**
     * 近90天发展的下级用户，所产生的所有订单的总金额分数
     * @param parentUserPhone
     * @return
     */
    public Integer getChildList90Money(String parentUserPhone) {
        return userMapper.getChildList90Money(parentUserPhone);
    }

    public Integer getChildDirectors180(String parentPhone) {
        return userMapper.getChildDirectors180(parentPhone);
    }

    public List<User> getManagerDownList() {
        return userMapper.getManagerDownList();
    }
}

