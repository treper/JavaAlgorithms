package ataosky.example.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloJob implements Job {

	@Override
    public void execute(JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("Hello World! " + new Date());
	}

}