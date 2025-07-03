package web.doctor.entity;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.clinic.entity.Clinic;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule-doctor")
public class Schedule implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "scheduleDoctor_id", updatable = false)
	private Integer scheduleDoctorId;
	
	@ManyToOne
	@JoinColumn(name = "clinic_id", nullable = false, updatable = false)
	private Clinic clinic;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false, updatable = false)
	private Doctor doctor;
	
	@Column(name= "vacation")
	private String vacation;
	
	@Column(name = "create_id")
	private Integer createId;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "update_time")
	private Timestamp updateTime;
}