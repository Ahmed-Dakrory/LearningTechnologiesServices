/**
 * 
 */
package main.com.zc.services.domain.data.model;

import java.util.List;

/**
 * @author omnya
 *
 */
public interface IMailSettingsRepository {

	public MailSetting add(MailSetting mailSetting);

	public boolean remove(MailSetting mailSetting);

	public MailSetting update(MailSetting mailSetting);

	public List<MailSetting> getAll();

	public MailSetting getById(Integer id);
	
}
