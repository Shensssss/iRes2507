package web.doctor.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import web.clinic.entity.Clinic;


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
	
	//FK多位醫師對一間診所
	@ManyToOne
	@JoinColumn(name = "clinic_id", nullable = false, updatable = false) //insertable不可以false因為我要手動填入?
	private Clinic clinic;
	
	private String education;
	
	private String experience;
	
	private String memo;
	
	@Column(name = "create_id")
	private Integer createId;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "update_time")
	private Timestamp updateTime;
	
	

	public Doctor() {
		
	}

	public Doctor(int doctorId, String doctorName, Integer clinicId, String education, String experience,
			String memo, Integer createId, Timestamp createTime, Integer updateId, Timestamp updateTime) {
		super();
		this.doctorId = doctorId;
		this.doctorName = doctorName;
		this.education = education;
		this.experience = experience;
		this.memo = memo;
		this.createId = createId;
		this.createTime = createTime;
		this.updateId = updateId;
		this.updateTime = updateTime;
	}

	public int getDoctorId() {
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
	
	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
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

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
