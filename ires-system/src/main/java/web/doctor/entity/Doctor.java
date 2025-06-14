package web.doctor.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "doctor")
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doctor_id", updatable = false)
	private Integer doctorId;
	
	@Column(name = "doctor_name")
	private String doctorName;
	
	//需要FK至clinic, 還沒設定(用多對一?)
	@Column(name = "clinic_id")
	private Integer clinicId;
	
	private String education;
	private String experience;
	private String memo;


	public Doctor(Integer doctorId, String doctorName, Integer clinicId, String education, String experience,
			String memo) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.clinicId = clinicId;
		this.education = education;
		this.experience = experience;
		this.memo = memo;
	}


	public Integer getDoctorId() {
		return doctorId;
	}


	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public Integer getClinicId() {
		return clinicId;
	}


	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}


	public String getEducation() {
		return education;
	}


	public void setEducation(String education) {
		this.education = education;
	}


	public String getExperience() {
		return experience;
	}


	public void setExperience(String experience) {
		this.experience = experience;
	}


	public String getMemo() {
		return memo;
	}


	public void setMemo(String memo) {
		this.memo = memo;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
