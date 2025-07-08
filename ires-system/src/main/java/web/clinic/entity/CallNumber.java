package web.clinic.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import web.doctor.entity.Doctor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "callNumber")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CallNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "callNumber_id")
    private Integer callNumberId;

    @Column(name = "clinic_id")
    private Integer clinicId;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", insertable = false, updatable = false)
    private Doctor doctor;

    @Column(name = "consultation_status")
    private Integer consultationStatus;

    @Column(name = "number")
    private Integer number;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "time_period")
    private Integer timePeriod;

    @Column(name = "create_id")
    private String createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "update_id")
    private String updateId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    private LocalDateTime updateTime;
}
