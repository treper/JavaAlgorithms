package ataosky.example.schedule;
import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by ataosky on 14-8-1.
 */
public class QuartzTest {

    public static void main(String[] args) {
        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Starts the scheduler
            scheduler.start();

            // Define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            // Cron expression "0/5 * * * * ?" means trigger the job to run on the second 0 and repeat every 5 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(cronSchedule("0/5 * * * * ?"))
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
