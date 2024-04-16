package com.luv2code.cruddemo;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.luv2code.cruddemo.dao.AppDAO;
import com.luv2code.cruddemo.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CruddemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CruddemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AppDAO appDAO){
		return runner ->{
			//createCourseAndStudents(appDAO);
			//findCourseAndStudents(appDAO);
			//findStudentCoursesByStudentId(appDAO);
			//addMoreCoursesForStudent(appDAO);
			//deleteCourse(appDAO);   //iliskili oludugu dıger tablo elemankarını silmez sadece kursu ve ılıskılerını sıler
			deleteStudent(appDAO);
		};
	}

	private void deleteStudent(AppDAO appDAO) {
		int theId=1;
		appDAO.deleteStudentById(theId);
		System.out.println("Deleted Student: ");

	}

	private void addMoreCoursesForStudent(AppDAO appDAO) {
		int theId =2;
		Student student = appDAO.findStudentAndCoursesByStudentId(theId);

		Course tempCourse = new Course("Rubik's cube!");
		Course tempCourse1 = new Course("Rubik's cube111!");

		student.addCourse(tempCourse);
		student.addCourse(tempCourse1);

		System.out.println("Finded the student: "+student);
		System.out.println("associated courses: "+student.getCourses());

		appDAO.update(student);

	}

	private void findStudentCoursesByStudentId(AppDAO appDAO) {

		int theId = 1;
		Student tempStudent = appDAO.findStudentAndCoursesByStudentId(theId);
		System.out.println("Finded the student: "+tempStudent);
		System.out.println("Finded the courses of student: "+tempStudent.getCourses());
	}

	private void findCourseAndStudents(AppDAO appDAO) {
		int courseId = 12;
		Course tempCourse = appDAO.findCourseAndStudentsByCourseId(courseId);
		System.out.println("Finded the course: "+tempCourse);
		System.out.println("Finded the course students: "+tempCourse.getStudents());
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		Course tempCourse = new Course("Pacman- How to play the game");

		Student tempStudent = new Student("inci","kucuk","ikucuk98@gmail.com");
		Student tempStudent2 = new Student("emros","yilmaz","emrossa@gmail.com");

		tempCourse.addStudent(tempStudent);
		tempCourse.addStudent(tempStudent2);
		System.out.println("Saving the course: "+tempCourse);
		System.out.println("associated students: "+ tempCourse.getStudents());

		appDAO.save(tempCourse);
	}

	private void deleteCourseAndReviews(AppDAO appDAO) {
		int theId=10;
		appDAO.deleteCourseById(theId);
		System.out.println("Deleted!");
	}

	private void findCourseAndReviewsByCourseId(AppDAO appDAO) {
		int theId = 10;
		Course tempCourse = appDAO.findCourseAndReviewsByCourseId(theId);
		System.out.println(tempCourse);
		System.out.println(tempCourse.getReviews());


	}

	private void createCourseAndReviews(AppDAO appDAO) {
		Course tempCourse = new Course("Pacman - How to score one million points");
		tempCourse.addReview(new Review("Great course love it!"));
		tempCourse.addReview(new Review("Cool course!"));
		tempCourse.addReview(new Review("Good job!"));

		System.out.println("Saving the course: ");
		System.out.println("tempCourse"+tempCourse);
		System.out.println("tempCourse"+tempCourse.getReviews());
		appDAO.save(tempCourse);

	}

	private void deleteCourse(AppDAO appDAO) {
		int theId  =12;
		System.out.println("Finding course: "+ theId);
		appDAO.deleteCourseById(theId);

		System.out.println("Deleted course: "+ theId);
	}

	private void updateCourse(AppDAO appDAO){
		int theId = 10;
		System.out.println("Finding instructor: "+ theId);
		Course tempCourse =appDAO.findCourseById(theId);

		System.out.println("Updating course: "+ theId);
		tempCourse.setTitle("Enjoy the simple things");

		appDAO.update(tempCourse);
	}

	private void updateInstructor(AppDAO appDAO){
		int theId=1;

		System.out.println("Finding instructor: "+ theId);
		Instructor tempInstructor = appDAO.findInstructorById(theId);

		//update the instructor
		System.out.println("Updating the instructor: "+ theId);
		tempInstructor.setLastName("AAAA");
		appDAO.update(tempInstructor);

		//System.out.println("tempInstructor updated: "+ tempInstructor.getCourses());

	}

	private void findInstructorByJoinFetch(AppDAO appDAO){  //if you need Instructor and courses you need call thıs method.
		int theId =1;

		System.out.println("Finding instructor: "+ theId);

		Instructor tempInstructor = appDAO.findInstructorByJoinFetch(theId);

		System.out.println("tempInstructor: "+ theId);
		System.out.println("Finding tempInstructor courses: "+ tempInstructor.getCourses());
		System.out.println("Done!");
	}

	private void findCoursesForInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: "+ theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		List<Course> courses = appDAO.findCoursesByInstructorId(theId);

		System.out.println("Courses: "+ theId);
	}

	private void findInstructorWithCourses(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Finding instructor id: "+ theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("tempInstructor: "+ tempInstructor);
		System.out.println("the associated courses: "+ tempInstructor.getCourses());

		System.out.println("Done!");
	}



	private void createInstructorWithCourses(AppDAO appDAO){

		//create the instructor
		Instructor tempInstructor = new Instructor("inci","kucuk","ikucuk98@gmail.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.ikucuk.com/youtube","Coding");

		//associate the objects
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//create some courses
		Course tempCourse1 = new Course("Air Guitar - The Ultimate Guide");
		Course tempCourse2 = new Course("The Pinball Masterclass");

		//add courses to instructor
		tempInstructor.add(tempCourse1);
		tempInstructor.add(tempCourse2);

		//save the instructor
		//
		//NOTE: this will ALSO save the courses because of CascadeType.PERSIST
		System.out.println("Saving instructor: "+ tempInstructor);
		System.out.println("The courses:" + tempInstructor.getCourses());

		appDAO.save(tempInstructor);

		System.out.println("Done!");
	}

	private void deleteInstructorDetail(AppDAO appDAO){
		int theId = 1;
		System.out.println("Deleting instructor detail id:"+theId);

		appDAO.deleteInstructorDetailById(theId);

		System.out.println("Done!");
	}
	private void findInstructorDetail(AppDAO appDAO){
		//get instructor detail object
		int theId = 1;

		System.out.println("Finding instructor id: "+ theId);

		InstructorDetail tempInstructorDetail = appDAO.findInstructorDetailById(theId);

		System.out.println("tempInstructorDetail: "+ tempInstructorDetail);

	}

	private void deleteInstructor(AppDAO appDAO) {
		int theId = 1;
		System.out.println("Deleting instructor id: " + theId);
		appDAO.deleteInstructorById(theId);
		System.out.println(theId);

	}

	private void findInstructor(AppDAO appDAO){
		int theId = 1;
		System.out.println("Finding instructor id: "+ theId);

		Instructor tempInstructor = appDAO.findInstructorById(theId);

		System.out.println("TempInstructor: "+ tempInstructor);
		System.out.println("TempInstructor Detail: "+ tempInstructor.getInstructorDetail());

	}

	private void createInstructor(AppDAO appDAO) {

		//create the instructor
		Instructor tempInstructor = new Instructor("inci","kucuk","ikucuk98@gmail.com");

		//create the instructor detail
		InstructorDetail tempInstructorDetail = new InstructorDetail("http://www.ikucuk.com/youtube","Coding");

		//associate the objects!!!
		tempInstructor.setInstructorDetail(tempInstructorDetail);

		//save the instructor
		//
		//this will also save the details object
		//because of CscadeType.ALL
		//
		System.out.println("Saving instructor: " + tempInstructor);
		appDAO.save(tempInstructor);

		System.out.println("Saved!! ");

	}

}


