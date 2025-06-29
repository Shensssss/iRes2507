package web.appointment.entity;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.UpdateTimestamp;
import web.clinic.entity.Clinic;
import web.doctor.entity.Doctor;
import web.patient.entity.Patient;

@Entity
@Table(name = "appointment")
public class Appointment {

	@Id
	@Column(name = "appointment_id", nullable = false, length = 36)
	private String appointmentId;

	@Column(name = "clinic_id")
	private Integer clinicId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", insertable = false, updatable = false)
	private Clinic clinic;

	@Column(name = "doctor_id")
	private Integer doctorId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	private Doctor doctor;

	@Column(name = "patient_id")
	private Integer patientId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", insertable = false, updatable = false)
	private Patient patient;

	@Column(name = "reserve_no", nullable = false)
	private Integer reserveNo;

	@Column(name = "appointment_date", nullable = false)
	private Date appointmentDate;

	@Column(name = "time_period", nullable = false)
	private Integer timePeriod;

	@Column(name = "first_visit", nullable = false)
	private Integer firstVisit;

	@Column(name = "status", nullable = false)
	private Integer status;

	@Column(name = "create_time", nullable = false, updatable = false, insertable = false)
	private Timestamp createTime;

	@Column(name = "update_time", insertable = false, updatable = false)
	private Timestamp updateTime;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	// Getters and Setters

	public String getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(String appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Integer getClinicId() {
		return clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}

	public Clinic getClinic() {
		return clinic;
	}

	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}

	public Integer getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(Integer doctorId) {
		this.doctorId = doctorId;
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}

	public Integer getPatientId() {
		return patientId;
	}

	public void setPatientId(Integer patientId) {
		this.patientId = patientId;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public Integer getReserveNo() {
		return reserveNo;
	}

	public void setReserveNo(Integer reserveNo) {
		this.reserveNo = reserveNo;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public Integer getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(Integer timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Integer getFirstVisit() {
		return firstVisit;
	}

	public void setFirstVisit(Integer firstVisit) {
		this.firstVisit = firstVisit;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}