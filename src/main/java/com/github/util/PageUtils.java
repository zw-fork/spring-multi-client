package com.github.util;

/**
 * Created by leolin on 7/20/2018.
 */
public class PageUtils {

    public static int defaultPage(Integer page){
        if(page == null || page.intValue() == 0){
            return 1;
        }else{
            return page;
        }
    }
}
