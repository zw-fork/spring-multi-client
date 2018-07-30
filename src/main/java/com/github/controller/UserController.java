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
package com.github.controller;

import com.github.model.User;
import com.github.pagehelper.PageHelper;
import com.github.service.UserService;
import com.github.util.CurrentUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.*;


@Controller
@RequestMapping("user")
public class UserController {

	@Resource private UserService userService;

    @ResponseBody
    @RequestMapping("get")
	public User getUser(String name){

        User user = new User();
        user.setNickName(name);
        user.setPhone("15612341234");
        return user;
    }


    @RequestMapping("userList")
    public String userList(ModelMap modelMap){
        List<User> list = new ArrayList<>();

        User user1 = new User();
        user1.setAreaCode(1111);
        list.add(user1);

        User user2 = new User();
        user2.setAreaCode(2222);
        list.add(user2);

        modelMap.addAttribute("name","测试");
        modelMap.addAttribute("list",list);
        return "userList";
    }


    @RequestMapping("deal")
    public String deal(String areaCode,ModelMap modelMap){
        modelMap.addAttribute("areaCode",areaCode);
        return "deal";
    }
}

