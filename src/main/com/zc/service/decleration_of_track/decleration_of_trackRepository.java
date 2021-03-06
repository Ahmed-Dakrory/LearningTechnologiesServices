/**
 * 
 */
package main.com.zc.service.decleration_of_track;

import java.util.List;


/**
 * 
 * @author Ahmed.Dakrory
 *
 */
public interface decleration_of_trackRepository {


	public List<decleration_of_track> getAll();
	public List<decleration_of_track> getAllByYearAndSemestar(int year,int semestar);
	public List<decleration_of_track> getAllByYearAndSemestarAndStudent(int year,int semestar,int studentId);
	public decleration_of_track adddecleration_of_track(decleration_of_track data);
	public decleration_of_track getById(int id);
	public boolean delete(decleration_of_track data)throws Exception;
}
