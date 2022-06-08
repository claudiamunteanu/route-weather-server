package main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendSubscribeMailScheduler {
    @Autowired
    SubscriptionService subscriptionService;

    @Scheduled(cron = "0 30 10 * * *")
    public void cronJobSch(){
        subscriptionService.sendEmails();
        System.out.println("Cronjob done!");
    }
}
