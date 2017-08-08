package sample.Model.DAO;

import retrofit2.Call;
import retrofit2.http.*;
import sample.Model.StudentEntity;

public interface SOService {

    @GET("/select.php")
    Call<StudentResponse> getStudents();
    /*@POST("/insert.php")
    Call<StudentResponse> createStudent(@Body StudentEntity studentEntity);*/
    @FormUrlEncoded
    @POST("/insert.php")
    Call<StudentEntity> addStudent(@Field("FullName") String fname, @Field("Birthday") String bday,
                                   @Field("IDNumber") String idnumber,@Field("AcaYear") int acayear,
                                   @Field("Major") String major);
    @FormUrlEncoded
    @POST("/update.php")
    Call<StudentEntity> updateStudent(@Field("ID") Long id,@Field("FullName") String fname, @Field("Birthday") String bday,
                                   @Field("IDNumber") String idnumber,@Field("AcaYear") int acayear,
                                   @Field("Major") String major);
    @FormUrlEncoded
    @POST("/delete.php")
    Call<StudentEntity> deleteStudent(@Field("ID") String id);



}