package com.du.quartz.controller;

import com.du.quartz.domain.AppQuartz;
import com.du.quartz.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Title
 * @ClassName JobController
 * @Author jsb_pbk
 * @Date 2019/2/26
 */
@RestController
@RequestMapping(value = "job")
public class JobController {
    @Autowired
    JobService jobService;

    //添加一个job
    @RequestMapping(value="/addJob",method= RequestMethod.POST)
    public String addJob(@RequestBody AppQuartz appQuartz) throws Exception {
        return jobService.addJob(appQuartz);
    }

    //暂停job
    @RequestMapping(value="/pauseJob",method=RequestMethod.POST)
    public String pauseJob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        if(quartzIds.length>0){
            for(Integer quartzId:quartzIds) {
                jobService.pauseJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            }
            return "success pauseJob";
        }else {
            return "fail pauseJob";
        }
    }

    //恢复job
    @RequestMapping(value="/resumeJob",method=RequestMethod.POST)
    public String resumeJob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        if(quartzIds.length>0) {
            for(Integer quartzId:quartzIds) {
                //appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
                jobService.resumeJob(appQuartz.getJobName(), appQuartz.getJobGroup());
            }
            return "success resumeJob";
        }else {
            return "fail resumeJob";
        }
    }


    //删除job
    @RequestMapping(value="/deletJob",method=RequestMethod.POST)
    public String deletJob(@RequestBody Integer[]quartzIds) throws Exception {
        AppQuartz appQuartz=null;
        for(Integer quartzId:quartzIds) {
            //appQuartz=appQuartzService.selectAppQuartzByIdSer(quartzId).get(0);
            String ret=jobService.deleteJob(appQuartz);
            if("success".equals(ret)) {
                //appQuartzService.deleteAppQuartzByIdSer(quartzId);
            }
        }
        return "success deleteJob";
    }

    //修改
    @RequestMapping(value="/updateJob",method=RequestMethod.POST)
    public String modifyJob(@RequestBody AppQuartz appQuartz) throws Exception {
        String ret= jobService.modifyJob(appQuartz);
        if("success".equals(ret)) {
            //appQuartzService.updateAppQuartzSer(appQuartz);
            return "success updateJob";
        }else {
            return "fail updateJob";
        }
    }

    //暂停所有
    @RequestMapping(value="/pauseAll",method=RequestMethod.GET)
    public String pauseAllJob() throws Exception {
        jobService.pauseAllJob();
        return  "success pauseAll";
    }

    //恢复所有
    @RequestMapping(value="/repauseAll",method=RequestMethod.GET)
    public String repauseAllJob() throws Exception {
        jobService.resumeAllJob();
        return "success";
    }

}
