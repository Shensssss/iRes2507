package web.appointment.entity;

import javax.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
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
public class Appointment {

	@Id
	@Column(name = "appointment_id", length = 36)
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

	@Column(name = "reserve_no")
	private Integer reserveNo;

	@Column(name = "appointment_date")
	private Date appointmentDate;

	@Column(name = "time_period")
	private Integer timePeriod;

	@Column(name = "first_visit")
	private Integer firstVisit;

	@Column(name = "status")
	private Integer status;

	@CreationTimestamp
	@Column(name = "create_time", updatable = false)
	private Timestamp createTime;

	@UpdateTimestamp
	@Column(name = "update_time", insertable = false)
	private Timestamp updateTime;

	@Column(name = "notes", columnDefinition = "TEXT")
	private String notes;

	@Transient
	public String getDoctorName() {
		return (doctor != null) ? doctor.getDoctorName() : null;
	}
}