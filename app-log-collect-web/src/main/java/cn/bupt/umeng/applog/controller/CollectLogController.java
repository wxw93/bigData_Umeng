package cn.bupt.umeng.applog.controller;

import cn.bupt.umeng.common.*;
import cn.bupt.umeng.util.PropertiesUtil;

import cn.bupt.umeng.common.AppStartupLog;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller()
@RequestMapping("/coll")
public class CollectLogController {
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    @ResponseBody
    public AppLogEntity collect(@RequestBody AppLogEntity e, HttpServletRequest req) {

        System.out.println("=============================");
        //server时间
        long myTime = System.currentTimeMillis() ;
        //客户端时间
        long clientTime = Long.parseLong(req.getHeader("clientTime"));
        //时间校对
        long diff = myTime - clientTime ;

        e.getAppStartupLogs();
        String json = JSONObject.toJSONString(e);

        System.out.println(json);
        return e;
    }

    private void processLogs(AppLogEntity e){
        for(AppStartupLog log : e.getAppStartupLogs()){
            PropertiesUtil.copyProperties(e,log);
        }
        for(AppErrorLog log : e.getAppErrorLogs()){
            PropertiesUtil.copyProperties(e,log);
        }
        for(AppEventLog log : e.getAppEventLogs()){
            PropertiesUtil.copyProperties(e,log);
        }
        for(AppPageLog log : e.getAppPageLogs()){
            PropertiesUtil.copyProperties(e,log);
        }
        for(AppUsageLog log : e.getAppUsageLogs()){
            PropertiesUtil.copyProperties(e,log);
        }
    }
}
