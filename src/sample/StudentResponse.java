package sample;

import com.google.gson.annotations.SerializedName;
import sample.Entity.StudentEntity;

import java.util.List;

public class StudentResponse {
    @SerializedName("result")
    private List<StudentEntity> students;

    public List<StudentEntity> getStudents(){
        return students;
    }

}
