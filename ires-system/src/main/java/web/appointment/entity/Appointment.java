package web.appointment.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import web.clinic.entity.Clinic;
import web.doctor.entity.Doctor;
import web.patient.entity.Patient;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "appointment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Appointment {

	@Id
	@Column(name = "appointment_id", length = 36)
	private String appointmentId;

	@Column(name = "clinic_id")
	private Integer clinicId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clinic_id", insertable = false, updatable = false)
	@JsonIgnore
	@ToString.Exclude
	private Clinic clinic;

	@Column(name = "doctor_id")
	private Integer doctorId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", insertable = false, updatable = false)
	@JsonIgnore
	@ToString.Exclude
	private Doctor doctor;

	@Column(name = "patient_id")
	private Integer patientId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", insertable = false, updatable = false)
	@JsonIgnore
	@ToString.Exclude
	private Patient patient;

	@Column(name = "reserve_no")
	private Integer reserveNo;

	@Column(name = "appointment_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date appointmentDate;

	@Column(name = "time_period")
	private Integer timePeriod;

	@Column(name = "first_visit")
	private Integer firstVisit;

	@Column(name = "status")
	private Integer status;

	@Column(name = "create_time", updatable = false, insertable = false)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp createTime;

	@UpdateTimestamp
	@Column(name = "update_time")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Timestamp updateTime;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Transient
	private String doctorName;

	public String getDoctorName() {
		return (doctor != null) ? doctor.getDoctorName() : null;
	}

	@Transient
	private String clinicName;

	public String getClinicName() {
		return (clinic != null) ? clinic.getClinicName() : null;
	}

	@Transient
	private String patientName;

	public String getPatientName() {
		return (patient != null) ? patient.getName(): null;
	}
}