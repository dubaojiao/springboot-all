package com.du.quartz.service;

import com.du.quartz.domain.AppQuartz;
import com.du.quartz.group.JobOne;
import com.du.quartz.group.JobTwo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Title
 * @ClassName JobService
 * @Author jsb_pbk
 * @Date 2019/2/26
 */
@Service
public class JobService {

    @Autowired(required = false)
   private SchedulerFactoryBean factory;

    /**
     * 新建一个任务
     *
     */
    public String addJob(AppQuartz appQuartz) throws Exception {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=df.parse(appQuartz.getStartTime());

        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            return "Illegal cron expression";  //表达式格式不正确
        }
        JobDetail jobDetail=null;
        //构建job信息
        if("JobOne".equals(appQuartz.getJobGroup())) {
            jobDetail = JobBuilder.newJob(JobOne.class).withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup()).build();
        }
        if("JobTwo".equals(appQuartz.getJobGroup())) {
            jobDetail = JobBuilder.newJob(JobTwo.class).withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup()).build();
        }

        //表达式调度构建器(即任务执行的时间,不立即执行)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(appQuartz.getJobName(), appQuartz.getJobGroup()).startAt(date)
                .withSchedule(scheduleBuilder).build();

        //传递参数
        if(appQuartz.getInvokeParam()!=null && !"".equals(appQuartz.getInvokeParam())) {
            trigger.getJobDataMap().put("invokeParam",appQuartz.getInvokeParam());
    }
        factory.getScheduler().scheduleJob(jobDetail, trigger);
    // pauseJob(appQuartz.getJobName(),appQuartz.getJobGroup());
        return "success";
    }

    /**
     * 获取Job状态
     * @param jobName
     * @param jobGroup
     * @return
     * @throws SchedulerException
     */
    public String getJobState(String jobName, String jobGroup) throws SchedulerException {
        TriggerKey triggerKey = new TriggerKey(jobName, jobGroup);
        return  factory.getScheduler().getTriggerState(triggerKey).name();
    }

    //暂停所有任务
    public void pauseAllJob() throws SchedulerException {
        factory.getScheduler().pauseAll();
    }

    //暂停任务
    public String pauseJob(String jobName, String jobGroup) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail =  factory.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        }else {
            factory.getScheduler().pauseJob(jobKey);
            return "success";
        }

    }

    //恢复所有任务
    public void resumeAllJob() throws SchedulerException {
        factory.getScheduler().resumeAll();
    }

    // 恢复某个任务
    public String resumeJob(String jobName, String jobGroup) throws SchedulerException {

        JobKey jobKey = new JobKey(jobName, jobGroup);
        JobDetail jobDetail =  factory.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null) {
            return "fail";
        }else {
            factory.getScheduler().resumeJob(jobKey);
            return "success";
        }
    }

    //删除某个任务
    public String deleteJob(AppQuartz appQuartz) throws SchedulerException {
        JobKey jobKey = new JobKey(appQuartz.getJobName(), appQuartz.getJobGroup());
        JobDetail jobDetail =  factory.getScheduler().getJobDetail(jobKey);
        if (jobDetail == null ) {
            return "jobDetail is null";
        }else if(! factory.getScheduler().checkExists(jobKey)) {
            return "jobKey is not exists";
        }else {
            factory.getScheduler().deleteJob(jobKey);
            return "success";
        }
    }

    //修改任务
    public String modifyJob(AppQuartz appQuartz) throws SchedulerException {
        if (!CronExpression.isValidExpression(appQuartz.getCronExpression())) {
            return "Illegal cron expression";
        }
        TriggerKey triggerKey = TriggerKey.triggerKey(appQuartz.getJobName(),appQuartz.getJobGroup());
        JobKey jobKey = new JobKey(appQuartz.getJobName(),appQuartz.getJobGroup());
        if ( factory.getScheduler().checkExists(jobKey) &&  factory.getScheduler().checkExists(triggerKey)) {
            CronTrigger trigger = (CronTrigger)  factory.getScheduler().getTrigger(triggerKey);
            //表达式调度构建器,不立即执行
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(appQuartz.getCronExpression()).withMisfireHandlingInstructionDoNothing();
            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    .withSchedule(scheduleBuilder).build();
            //修改参数
            if(!trigger.getJobDataMap().get("invokeParam").equals(appQuartz.getInvokeParam())) {
                trigger.getJobDataMap().put("invokeParam",appQuartz.getInvokeParam());
            }
            //按新的trigger重新设置job执行
            factory.getScheduler().rescheduleJob(triggerKey, trigger);
            return "success";
        }else {
            return "job or trigger not exists";
        }

    }

}

