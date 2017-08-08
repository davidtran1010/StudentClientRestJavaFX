package sample.Model;

import javax.persistence.*;
import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
@Table(name = "Student", schema = "KTPM", catalog = "")
public class StudentEntity implements Serializable {
    @SerializedName("ID")
    @Expose
    private long id;
    @SerializedName("FullName")
    @Expose
    private String fullName;

    @SerializedName("Birthday")
    @Expose
    private String birthday;
    @SerializedName("IDNumber")
    @Expose
    private String idNumber;

    @SerializedName("AcaYear")
    @Expose
    private Integer acaYear;

    @SerializedName("Major")
    @Expose
    private String major;

    @Id
    @Column(name = "ID", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "FullName", nullable = true, length = -1)
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Basic
    @Column(name = "Birthday", nullable = true, length = -1)
    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "IDNumber", nullable = true, length = -1)
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    @Basic
    @Column(name = "AcaYear", nullable = true)
    public Integer getAcaYear() {
        return acaYear;
    }

    public void setAcaYear(Integer acaYear) {
        this.acaYear = acaYear;
    }

    @Basic
    @Column(name = "Major", nullable = true, length = -1)
    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (id != that.id) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        if (idNumber != null ? !idNumber.equals(that.idNumber) : that.idNumber != null) return false;
        if (acaYear != null ? !acaYear.equals(that.acaYear) : that.acaYear != null) return false;
        if (major != null ? !major.equals(that.major) : that.major != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (idNumber != null ? idNumber.hashCode() : 0);
        result = 31 * result + (acaYear != null ? acaYear.hashCode() : 0);
        result = 31 * result + (major != null ? major.hashCode() : 0);
        return result;
    }
}
