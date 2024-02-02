package com.boomshair.shedlocktest.config;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class IndexPriceTask {

    @Scheduled(cron = "0/30 * * * * ? ")
    @SchedulerLock(name = "indexPriceTask", lockAtMostFor = "20m", lockAtLeastFor = "5m")
    public void doTask(){
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over");
        }
    }
}
