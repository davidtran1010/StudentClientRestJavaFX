package sample.Controller;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sample.Model.DAO.ApiUtils;
import sample.Model.StudentEntity;
import sample.Model.DAO.SOService;
import sample.Model.DAO.StudentResponse;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends Application implements Initializable {
    //private static final SessionFactory ourSessionFactory;

    private SOService mService = ApiUtils.getSOService();
    private int selectedIndex = -1;
    List<StudentEntity> studentList = new ArrayList<>();

    @FXML
    Label txtNotify;

    @FXML
    TextField txtStudentID, txtStudentName, txtBdate, txtIdNum, txtMajor, txtAcaYear, host;
    @FXML
    javafx.scene.control.TableView<StudentEntity> studentTableView;
    @FXML
    TableColumn<StudentEntity, Long> idColumn;
    @FXML
    TableColumn<StudentEntity, String> nameColumn, bDayColumn, idNumColumn, majorColumn;
    @FXML
    TableColumn<StudentEntity, Integer> acaYearColumn;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../View/UI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtStudentID.setEditable(false);

        try {
            //Do something when select row from table
            studentTableView.getSelectionModel().selectedItemProperty().addListener(
                    new ChangeListener<StudentEntity>() {
                        @Override
                        public void changed(ObservableValue<? extends StudentEntity> observable, StudentEntity oldValue, StudentEntity newValue) {
                            //System.out.println();

                            selectedIndex = studentTableView.getSelectionModel().getSelectedIndex();

                            try {

                                txtStudentID.setText(String.valueOf(observable.getValue().getId()));
                                txtStudentName.setText(observable.getValue().getFullName());
                                txtBdate.setText(observable.getValue().getBirthday());
                                txtIdNum.setText(observable.getValue().getIdNumber());
                                txtAcaYear.setText(observable.getValue().getAcaYear() + "");
                                txtMajor.setText(observable.getValue().getMajor());
                            } catch (Exception e) {
                                System.out.println("No row selected. No data to bind to text boxes");
                            }


                        }
                    });
        } catch (Exception e) {
            System.out.println("No row selected");
        }
        loadData(studentTableView);

        setupTable();


    }

    public void setupTable() {
        idColumn.setMinWidth(200);
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));

        bDayColumn.setMinWidth(100);
        bDayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));

        idNumColumn.setMinWidth(100);
        idNumColumn.setCellValueFactory(new PropertyValueFactory<>("idNumber"));

        acaYearColumn.setMinWidth(100);
        acaYearColumn.setCellValueFactory(new PropertyValueFactory<>("acaYear"));

        majorColumn.setMinWidth(100);
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
    }

    public void loadStudentList() {
        txtNotify.setText("");
        loadData(studentTableView);
    }

    private void loadData(TableView<StudentEntity> studentTableView) {

        mService.getStudents().enqueue(new Callback<StudentResponse>() {
            @Override
            public void onResponse(Call<StudentResponse> call, Response<StudentResponse> response) {
                clearTextBoxes();
                //System.out.println(response.body()+"\n");
                System.out.println("Load data:" + response.message());
                List<StudentEntity> studentList = response.body().getStudents();
                studentTableView.getItems().clear();
                bindDataToTableView(studentList);
            }

            @Override
            public void onFailure(Call<StudentResponse> call, Throwable throwable) {

                System.out.println("Failed to load data\n" + throwable.getMessage());
                txtNotify.setText("Connection has problem. Please refesh!");
            }
        });
    }

    private void clearTextBoxes() {
        txtStudentID.clear();
        txtIdNum.clear();
        txtStudentName.clear();
        txtBdate.clear();
        txtIdNum.clear();
        txtAcaYear.clear();
        txtMajor.clear();
        System.out.println("Log: All text boxes cleared");
    }

    public void deleteDataFromDB(String id) {
        mService.deleteStudent(id).enqueue(new Callback<StudentEntity>() {
            @Override
            public void onResponse(Call<StudentEntity> call, Response<StudentEntity> response) {
                clearTextBoxes();
                System.out.println("Response delete student\n" + response.message());
                txtNotify.setText("Delete Student successfully");
            }

            @Override
            public void onFailure(Call<StudentEntity> call, Throwable throwable) {
                System.out.println("Failed delete student\n" + throwable.getMessage());
                txtNotify.setText("Connection has problem.Please refesh and do again!");
            }
        });
    }

    public void deleteStudent() {
        deleteDataFromDB(txtStudentID.getText());
        studentTableView.getItems().remove(selectedIndex);

    }

    public void createStudent() {
        List<StudentEntity> studentEntityList = new ArrayList<>();
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setFullName(txtStudentName.getText());
        studentEntity.setBirthday(txtBdate.getText());
        studentEntity.setAcaYear(Integer.valueOf(txtAcaYear.getText()));
        studentEntity.setIdNumber(txtIdNum.getText());
        studentEntity.setMajor(txtMajor.getText());


        insertDatatoDB(studentEntity);
        clearTextBoxes();

        loadStudentList();


    }

    public void updateStudent() {

        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setId(Long.parseLong(txtStudentID.getText()));
        studentEntity.setFullName(txtStudentName.getText());
        studentEntity.setBirthday(txtBdate.getText());
        studentEntity.setAcaYear(Integer.valueOf(txtAcaYear.getText()));
        studentEntity.setIdNumber(txtIdNum.getText());
        studentEntity.setMajor(txtMajor.getText());

        updateDataToDB(studentEntity);

        clearTextBoxes();

        loadStudentList();

    }

    void updateDataToDB(StudentEntity studentEntity) {
        mService.updateStudent(studentEntity.getId(), studentEntity.getFullName(), studentEntity.getBirthday(), studentEntity.getIdNumber()
                , studentEntity.getAcaYear(), studentEntity.getMajor())
                .enqueue(new Callback<StudentEntity>() {
                    @Override
                    public void onResponse(Call<StudentEntity> call, Response<StudentEntity> response) {
                        clearTextBoxes();
                        System.out.println("Response update Student:" + response.message());
                    }

                    @Override
                    public void onFailure(Call<StudentEntity> call, Throwable throwable) {
                        System.out.println("Failed to update student\n" + throwable.getMessage());
                        txtNotify.setText("Connection has problem.Please refesh and do again!");
                    }
                });
    }

    void insertDatatoDB(StudentEntity studentEntity) {
        mService.addStudent(studentEntity.getFullName(), studentEntity.getBirthday(), studentEntity.getIdNumber()
                , studentEntity.getAcaYear(), studentEntity.getMajor()).enqueue(new Callback<StudentEntity>() {
            @Override
            public void onResponse(Call<StudentEntity> call, Response<StudentEntity> response) {
                clearTextBoxes();
                System.out.println("Response insert Student:" + response.message());
            }

            @Override
            public void onFailure(Call<StudentEntity> call, Throwable throwable) {
                System.out.println("Failed to insert student\n" + throwable.getMessage());
                txtNotify.setText("Connection has problem.Please refesh and do again!");
            }
        });
    }

    void bindDataToTableView(List<StudentEntity> list) {

        studentTableView.getItems().addAll(list);

    }

}
