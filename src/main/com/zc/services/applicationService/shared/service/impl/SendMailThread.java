package main.com.zc.services.applicationService.shared.service.impl;

import java.util.List;

import main.com.zc.services.presentation.forms.emails.service.impl.CheckNewEmails;


public class SendMailThread extends Thread{

	public SendMailThread(List<String> recipentMail, String recipentName,
			String content, String title) {
		super();
		this.recipentMail = recipentMail;
		this.recipentName = recipentName;
		this.content = content;
		this.title = title;
	}
	private List<String> recipentMail;
	private String recipentName;
	private String content;
	private String title;
	@Override
	public void run() {
		
		CheckNewEmails checkNewMails=new CheckNewEmails();

		System.out.println("Dakrory:OKYa 3");
		checkNewMails.notifyNextStepOwner(this.recipentMail, this.recipentName, this.content, this.title);
		
	}
	public List<String> getRecipentMail() {
		return recipentMail;
	}
	public void setRecipentMail(List<String> recipentMail) {
		this.recipentMail = recipentMail;
	}
	public String getRecipentName() {
		return recipentName;
	}
	public void setRecipentName(String recipentName) {
		this.recipentName = recipentName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
