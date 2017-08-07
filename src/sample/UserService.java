package sample;

import sample.Entity.StudentEntity;


import java.util.List;

public interface UserService{
    List<StudentEntity> getStudentList();
    void createStudent(StudentEntity studentEntity);
    void deleteStudent(StudentEntity studentEntity);
    void updateStudent(StudentEntity studentEntity);

}
