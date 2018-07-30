package com.github.service;

import com.alibaba.fastjson.JSONObject;
import com.github.model.OpenOrg;
import com.github.util.AreaJsonUtils;
import com.github.util.HttpRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.HttpClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by leolin on 7/20/2018.
 */
//@Component
public class UpdateOrgCityJob {

    @Resource
    private OpenOrgService openOrgService;

//    @Scheduled(fixedDelay = 60 * 60 * 1000)
    public void userookPageRecords(){
        List<OpenOrg> list = openOrgService.getList();
        AreaJsonUtils areaJsonUtils = new AreaJsonUtils();
        String getOrgInfoUrl = "http://book.jieyueji.cn/server/apis?cmd=getSchoolByfid&fid=";
        for(OpenOrg openOrg : list){
            String fid = openOrg.getFid();
            String url = getOrgInfoUrl+fid;

            try {
                String response = new HttpRequest().doGet(url);
                JSONObject jsonObject = JSONObject.parseObject(response);
                String location = jsonObject.getJSONObject("data").getString("location");
                String areaString = location.substring(location.lastIndexOf("-")+1);
                String cityCode = areaJsonUtils.getAreaCode(areaString);

                if(StringUtils.isNotBlank(cityCode)){
                    openOrg.setCityCode(cityCode);
                    openOrgService.update(openOrg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
