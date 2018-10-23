package main.com.zc.services.presentation.coursesManagment.bean;

import java.util.ArrayList;
import java.util.List;

import main.com.zc.services.domain.courses.model.SO.SO;
import main.com.zc.services.domain.courses.model.books.CourseBooks;
import main.com.zc.services.domain.courses.model.clo.CLO;
import main.com.zc.services.domain.courses.model.corequisit.CoRequisites;
import main.com.zc.services.domain.courses.model.courseIns.CourseInSyllabus;
import main.com.zc.services.domain.courses.model.courseTa.CourseTa;
import main.com.zc.services.domain.courses.model.grades.Grade;
import main.com.zc.services.domain.courses.model.note.Note;
import main.com.zc.services.domain.courses.model.prerequisites.PreRequisites;
import main.com.zc.services.domain.courses.model.references.References;
import main.com.zc.services.domain.courses.model.relatedTopics.RelatedTopics;
import main.com.zc.services.domain.courses.model.schedule.Schedule;
import main.com.zc.services.domain.courses.model.topics.Topics;
import main.com.zc.services.presentation.survey.courseFeedback.dto.CoursesDTO;


public class CourseSyllabusCollection {
	
	private CoursesDTO courses;
	private List<SO> sos;
	private List<Note> notes;
	private List<CLO> clos;
	private List<PreRequisites> pre_Requisites;
	private List<RelatedTopics> relatedTopics;
	private List<References> references;
	private List<CoRequisites> coRequisites;
	private List<Grade> grades;
	private List<CourseTa> courseTas;
	private List<CourseInSyllabus> courseInstructor;
	private List<CourseBooks> books;
	private List<Topics> topics;
	private List<Schedule> schedules;
	
	public CourseSyllabusCollection() {
		// TODO Auto-generated constructor stub
		courses=new CoursesDTO();
		sos=new ArrayList<SO>();
		notes=new ArrayList<Note>();
		clos=new ArrayList<CLO>();
		pre_Requisites=new ArrayList<PreRequisites>();
		relatedTopics=new ArrayList<RelatedTopics>();
		references=new ArrayList<References>();
		coRequisites=new ArrayList<CoRequisites>();
		grades=new ArrayList<Grade>();
		courseTas=new ArrayList<CourseTa>();
		courseInstructor=new ArrayList<CourseInSyllabus>();
		books=new ArrayList<CourseBooks>();
		topics=new ArrayList<Topics>();
		schedules=new ArrayList<Schedule>();
	}
	public CoursesDTO getCourses() {
		return courses;
	}
	public void setCourses(CoursesDTO courses) {
		this.courses = courses;
	}
	public List<SO> getSos() {
		return sos;
	}
	public void setSos(List<SO> sos) {
		this.sos = sos;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public List<CLO> getClos() {
		return clos;
	}
	public void setClos(List<CLO> clos) {
		this.clos = clos;
	}
	public List<PreRequisites> getPre_Requisites() {
		return pre_Requisites;
	}
	public void setPre_Requisites(List<PreRequisites> pre_Requisites) {
		this.pre_Requisites = pre_Requisites;
	}
	public List<RelatedTopics> getRelatedTopics() {
		return relatedTopics;
	}
	public void setRelatedTopics(List<RelatedTopics> relatedTopics) {
		this.relatedTopics = relatedTopics;
	}
	public List<References> getReferences() {
		return references;
	}
	public void setReferences(List<References> references) {
		this.references = references;
	}
	public List<CoRequisites> getCoRequisites() {
		return coRequisites;
	}
	public void setCoRequisites(List<CoRequisites> coRequisites) {
		this.coRequisites = coRequisites;
	}
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	public List<CourseTa> getCourseTas() {
		return courseTas;
	}
	public void setCourseTas(List<CourseTa> courseTas) {
		this.courseTas = courseTas;
	}
	
	public List<CourseBooks> getBooks() {
		return books;
	}
	public void setBooks(List<CourseBooks> books) {
		this.books = books;
	}
	public List<CourseInSyllabus> getCourseInstructor() {
		return courseInstructor;
	}
	public void setCourseInstructor(List<CourseInSyllabus> courseInstructor) {
		this.courseInstructor = courseInstructor;
	}
	public List<Topics> getTopics() {
		return topics;
	}
	public void setTopics(List<Topics> topics) {
		this.topics = topics;
	}
	public List<Schedule> getSchedules() {
		return schedules;
	}
	public void setSchedules(List<Schedule> schedules) {
		this.schedules = schedules;
	}

	
	
	
}
