/**
 * 
 */
package main.com.zc.services.domain.data.service.business;

import main.com.zc.services.domain.person.model.IStudentRepository;
import main.com.zc.services.domain.person.model.Student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author Omnya
 *
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AddStudentDataServiceImpl implements IAddStudentDataService{
/*@Autowired
IStudentRepository studentRep;*/
@Autowired
IStudentRepository perRep;
	@Override
	public Student addStudentData(Student student) {
		int id=perRep.add(student);
		return perRep.getPersonById(id); 
	}}
