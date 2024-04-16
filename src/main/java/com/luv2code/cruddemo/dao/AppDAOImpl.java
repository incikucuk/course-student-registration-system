package com.luv2code.cruddemo.dao;

import com.luv2code.cruddemo.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AppDAOImpl implements AppDAO {
    private final EntityManager entityManager;

    @Autowired
    public AppDAOImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        entityManager.persist(theInstructor);  //save the details object because of CascadeType.ALL
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {

        //retrieve the instructor
        Instructor tempInstructor = entityManager.find(Instructor.class,theId);

        //get the courses
        List<Course> courses = tempInstructor.getCourses();

        //break association of all the instructor
        for(Course tempCourse : courses){
            tempCourse.setInstructor(null);
        }

        //delete the instructor
        entityManager.remove(tempInstructor);
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return entityManager.find(InstructorDetail.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetailById(int theId){

        //retrieve instructor detail
        InstructorDetail tempInstructorDetail = entityManager.find(InstructorDetail.class,theId);

        //remove the associated object reference
        //break bi-directional link
        tempInstructorDetail.getInstructor().setInstructorDetail(null);


        //delete the instructor detail
        entityManager.remove(tempInstructorDetail);
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = entityManager.createQuery(
                "from Course where instructor.id = :data", Course.class);
        query.setParameter("data",theId);

        //execute query
        List<Course> courses = query.getResultList();

        return courses;
    }

    @Override
    public Instructor findInstructorByJoinFetch(int theId) {
/*
        //create query
        TypedQuery<Instructor> query = entityManager.createQuery(
                                                            "select i from Instructor i"
                                                                    +" JOIN FETCH i.courses"
                                                                    +" JOIN FETCH i.instructorDetail"
                                                                    +" where i.id = :data", Instructor.class);
        query.setParameter("data",theId);
        Instructor instructor = query.getSingleResult();
        return instructor;
*/      return null;


    }

    @Override
    @Transactional
    public void update(Instructor tempInstructor) {
        entityManager.merge(tempInstructor); // will update an existing entity
    }

    @Override
    public Course findCourseById(int theId) {
        return entityManager.find(Course.class,theId);
    }

    @Override
    @Transactional
    public void update(Course tempCourse){
        entityManager.merge(tempCourse);
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {

        Course tempcourse = entityManager.find(Course.class,theId);
        entityManager.remove(tempcourse);

    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        entityManager.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {

        TypedQuery<Course> query = entityManager.createQuery("select c from Course c"+" JOIN FETCH c.reviews"+ " where c.id = :data", Course.class);

        query.setParameter("data",theId);

        Course course =query.getSingleResult();

        return course;
    }

    @Override
    public Course findCourseAndStudentsByCourseId(int theId) {

        TypedQuery<Course> query = entityManager.createQuery("select c from Course c "+"JOIN FETCH c.students "+ "where c.id = :data", Course.class);

        query.setParameter("data",theId);

        Course course =query.getSingleResult();

        return course;
    }

    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {

        TypedQuery<Student> query = entityManager.createQuery("select c from Student c "+"JOIN FETCH c.courses "+ "where c.id = :data", Student.class);

        query.setParameter("data",theId);

        Student student =query.getSingleResult();

        return student;
    }

    @Override
    @Transactional
    public void update(Student tempStudent) {
        entityManager.merge(tempStudent);

    }

    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        Student tempStudent = entityManager.find(Student.class,theId);
        entityManager.remove(tempStudent);
    }
}
