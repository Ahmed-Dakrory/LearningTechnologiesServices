/**
 * 
 */
package main.com.zc.services.presentation.forms.emails.model;

import java.util.Date;

import main.com.zc.services.applicationService.shared.service.ISharedNotifyService;
import main.com.zc.services.presentation.forms.emails.service.impl.CheckNewEmails;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SimpleTriggerBean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class MailJob extends QuartzJobBean {

	@Override
	protected void executeInternal(JobExecutionContext jobContext)
			throws JobExecutionException {
		try {
			CheckNewEmails checkNewEmails = new CheckNewEmails();
			MailJobDetail mailJobDetail = (MailJobDetail) jobContext.getJobDetail();
			boolean status = checkNewEmails.sendEmail(mailJobDetail
					.getRecipentList(), checkNewEmails.formateMail(
					mailJobDetail.getContent(), mailJobDetail.getName()),
					mailJobDetail.getTitle());
			ApplicationContext springContext = WebApplicationContextUtils
					.getWebApplicationContext(ContextLoaderListener
							.getCurrentWebApplicationContext()
							.getServletContext());
			ISharedNotifyService sharedNotifyService = (ISharedNotifyService) springContext
					.getBean("sharedNotifyServiceImpl");
			sharedNotifyService.updateStatus(mailJobDetail.getFormName(),
					mailJobDetail.getFormId(), status);
			if (!status) {
				OnErrorScheduleJob(jobContext);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			OnErrorScheduleJob(jobContext);
		}
	}

	private void OnErrorScheduleJob(JobExecutionContext context) {
		try {
			MailJobDetail mailJobDetail = (MailJobDetail) context
					.getJobDetail();
			SimpleTriggerBean trigger = new SimpleTriggerBean();
			trigger.setName("TR" + mailJobDetail.getFormName()
					+ mailJobDetail.getFormId());
			trigger.setGroup("TR" + mailJobDetail.getFormName());
			// Tell quartz to schedule the job
			trigger.setStartDelay(20000);
			trigger.setStartTime(new Date(
					mailJobDetail.getNotifyAt().getTime() + 60000));
			trigger.setRepeatCount(0);
			trigger.setRepeatInterval(0);
			trigger.setNextFireTime(new Date(mailJobDetail.getNotifyAt()
					.getTime() + 60000));
			trigger.setJobDetail(mailJobDetail);
			trigger.setJobName("J" + mailJobDetail.getFormName()
					+ mailJobDetail.getFormId());
			trigger.setJobGroup("J" + mailJobDetail.getFormName());
			Trigger oldTrig = context.getScheduler().getTrigger(
					"TR" + mailJobDetail.getFormName()
							+ mailJobDetail.getFormId(),
					"TR" + mailJobDetail.getFormName());

			
			if (oldTrig != null) {
				context.getScheduler().rescheduleJob(
						"TR" + mailJobDetail.getFormName()
								+ mailJobDetail.getFormId(),
						"TR" + mailJobDetail.getFormName(), trigger);
			} else {
				context.getScheduler().scheduleJob(trigger);
			}

		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}