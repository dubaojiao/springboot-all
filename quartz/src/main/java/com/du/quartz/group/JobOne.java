package com.du.quartz.group;

import org.quartz.*;
import org.springframework.stereotype.Component;

/**
 * @Title 每个定时任务都必须有一个分组，名称和corn表达式,corn表达式也就是定时任务的触发时间，关于corn表达式格式以及含义可以参考一些网络资源。每个定时任务都有一个入口类在这里我把类名当成定时任务的分组名称，例如：只要创建定时任务的分组是JobOne的都会执行JobOne这个任务类里面的逻辑。如果定时任务需要额外的参数可以使用JobDataMap传递参数，当然也可以从数据库中获取需要的数据。@PersistJobDataAfterExecution和@DisallowConcurrentExecution注解是不让某个定时任务并发执行，只有等当前任务完成下一个任务才会去执行。
 * @ClassName JonOne
 * @Author jsb_pbk
 * @Date 2019/2/26
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@Component
public class JobOne implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap data=context.getTrigger().getJobDataMap();
        String invokeParam =(String) data.get("invokeParam");
        //在这里实现业务逻辑
        System.out.println("JobOne任务启动了:"+invokeParam);
    }
}
