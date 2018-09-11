package com.du.scheduled.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 0 0 12 * * ? 每天的12点触发扫描
 * 0 0 1 * * ? 每天的01点触发扫描
 *
 */
@Component
public class ScanningComponent {
    @Scheduled(cron = "0 13 1 * * ?") // 每天01：13触发
    //@Scheduled(cron = "0 15 10 ? * *") // 每天早上10：15触发
    void scanningBill(){

    }
}
