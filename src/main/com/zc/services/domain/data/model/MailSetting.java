/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * 
 * @author heba
 * @since Feb 10, 2015
 * @lastUpdated Feb 10, 2015
 */
@NamedQueries({
	@NamedQuery(name = "MailSetting.getAll", query = "SELECT ci FROM MailSetting ci"),
	@NamedQuery(name = "MailSetting.getById", query = "from MailSetting ci where ci.id = :id")})

@Entity
@Table(name = "mail_setting")
public class MailSetting {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "SEND_NOTIFICATION")
	private Boolean notifyMe;
	
	@Column(name = "EVERY_DAY")
	private Integer everyDays;
		
	@Column(name = "LAST_NOTIFICATION")
	private Date lastNotify;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getNotifyMe() {
		return notifyMe;
	}

	public void setNotifyMe(Boolean notifyMe) {
		this.notifyMe = notifyMe;
	}

	public Integer getEveryDays() {
		return everyDays;
	}

	public void setEveryDays(Integer everyDays) {
		this.everyDays = everyDays;
	}

	public Date getLastNotify() {
		return lastNotify;
	}

	public void setLastNotify(Date lastNotify) {
		this.lastNotify = lastNotify;
	}

}