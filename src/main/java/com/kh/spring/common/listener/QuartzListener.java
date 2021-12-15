package com.kh.spring.common.listener;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


/**
 * Application Lifecycle Listener implementation class QuartzListener
 *
 */
@WebListener
public class QuartzListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public QuartzListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
    	try {
			// Grab the Scheduler instance from the Factory
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

			Calendar calendar = Calendar.getInstance();
			
			calendar.set(Calendar.HOUR, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			
			Date date = calendar.getTime();
			System.out.println(date);
			// define the job and tie it to our HelloJob class
			JobDetail job = newJob(JobDelete.class).withIdentity("deleteFromDB", "group1").build();

			// Trigger the job to run now, and then repeat every 40 seconds
			Trigger trigger = newTrigger()
						.withIdentity("deleteTriger", "group1").startNow()
						.withSchedule(simpleSchedule()
						.withIntervalInHours(24)
						.repeatForever())
						.startAt(date)
						.build();

			// Tell quartz to schedule the job using our trigger
			scheduler.scheduleJob(job, trigger);
			// and start it off
			scheduler.start();
			

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
    }
	
}
