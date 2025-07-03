package web.doctor.entity;

import java.io.Serializable;

import java.sql.Timestamp;
import java.time.LocalDate;
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
@Table(name = "scheduleDoctor")
public class Schedule implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_doctor_id")
    private Integer scheduleDoctorId;

	@ManyToOne
	@JoinColumn(name = "clinic_id", nullable = false, updatable = false)
	private Clinic clinic;
	
	@ManyToOne
	@JoinColumn(name = "doctor_id", nullable = false, updatable = false)
	private Doctor doctor;

    /**
     * 特定休假日期（如國定假日/請假）
     */
    @Column(name = "off_date")
    private LocalDate offDate;

    /**
     * 週期性排班用的欄位（1~7；1=週一，7=週日）
     */
    @Column(name = "day_of_week")
    private Integer dayOfWeek;

    /**
     * 時段：morning / afternoon / night（不可為 null）
     */
    @Column(name = "time_period", nullable = false)
    private List<String> timePeriod;

    /**
     * true 表示不上班
     */
    @Column(name = "offStatus", nullable = false)
    private Boolean off;

	@Column(name = "create_id")
	private Integer createId;
	
	@Column(name = "create_time", updatable = false)
	private Timestamp createTime;
	
	@Column(name = "update_id")
	private Integer updateId;

	@Column(name = "update_time")
	private Timestamp updateTime;
}