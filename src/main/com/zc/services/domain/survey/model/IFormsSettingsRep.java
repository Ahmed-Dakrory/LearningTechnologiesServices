package main.com.zc.services.domain.survey.model;

import java.util.List;

public interface IFormsSettingsRep {

	public FormsSettings add(FormsSettings form);
	public boolean remove(Integer id);
	public FormsSettings update(FormsSettings form);
	public List<FormsSettings> getAll();
	public FormsSettings getById(Integer id);
	public List<FormsSettings> getByFormId(Integer id);
	public List<FormsSettings> getByFormIdAndLevelId(Integer formID, Integer levelID);
}
