package com.du.tools.service;

import com.du.tools.domain.SysLog;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {
    private List<SysLog> logList;

    public List<SysLog> getLogList() {
        System.out.println("getLogList");
        logList = new ArrayList<>();
        SysLog s1;
        for(int x=0;x<20;x++){
            s1 = new SysLog();
            s1.setLogId(""+x+1);
            s1.setMethodName("getLogList"+x);
            s1.setTypeName("正常");
            s1.setTime("2018-02-02 12:30:21");
            s1.setType(1);
            logList.add(s1);
        }
        logList.get(2).setType(2);
        logList.get(2).setTypeName("错误");
        logList.get(5).setType(2);
        logList.get(5).setTypeName("错误");
        logList.get(8).setType(3);
        logList.get(8).setTypeName("异常");
        return logList;
    }

    public SysLog getLogById(String id) {
        for (SysLog sysLog:logList){
            if(sysLog.getLogId().equals(id)){
                sysLog.setUid(1);
                sysLog.setCostMs(20000L);
                sysLog.setParam("{id:1}");
                sysLog.setPath("getLogById");
                sysLog.setErrorMsg("服务器异常");
                sysLog.setRequestMethod("GET");
                sysLog.setStackTrace("sssjsjsjsshsiuiusah是几点回家睡觉撒到金沙酒店设计师大奖啊");
                sysLog.setReturnData(sysLog.toString());
                return sysLog;
            }
        }
        return null;
    }
}
