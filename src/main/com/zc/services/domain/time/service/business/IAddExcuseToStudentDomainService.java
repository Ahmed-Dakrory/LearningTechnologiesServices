/**
 * 
 */
package main.com.zc.services.domain.time.service.business;

import main.com.zc.services.domain.time.model.SeminarTimes;
import main.com.zc.services.domain.time.model.Time;


/**
 * @author Omnya Alaa
 *
 */
public interface IAddExcuseToStudentDomainService {
public Time addExcuseToStudentInAttendance(Time time);
public SeminarTimes addExcuseToStudentInSeminar(SeminarTimes time);
}
