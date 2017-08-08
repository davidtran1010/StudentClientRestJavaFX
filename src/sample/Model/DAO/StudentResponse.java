package sample.Model.DAO;

import com.google.gson.annotations.SerializedName;
import sample.Model.StudentEntity;

import java.util.List;

public class StudentResponse {
    @SerializedName("result")
    private List<StudentEntity> students;

    public List<StudentEntity> getStudents(){
        return students;
    }

}
