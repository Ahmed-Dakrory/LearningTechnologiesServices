/**
 * 
 */
package main.com.zc.services.applicationService.forms.academicPetition.services;

import java.util.List;

import main.com.zc.services.presentation.forms.academicPetition.dto.CoursePetitionDTO;

/**
 * @author Omnya Alaa
 *
 */
public interface INotifyPeopleWithNewPetitonsAPPService {

public boolean createMail(CoursePetitionDTO dao);
public boolean notifyDean(CoursePetitionDTO dao);
boolean notifyUser(List<String> mail, String subject);
public boolean notifyAdmissionHead(CoursePetitionDTO dao);
public boolean notifyRegistrar(CoursePetitionDTO dao);
}
