package web.appointment.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @Column(name = "appointment_id", nullable = false, length = 36)
    private String appointmentId;

    @Column(name = "patient_id", nullable = false)
    private Integer patientId;

    @Column(name = "clinic_id", nullable = false)
    private Integer clinicId;

    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    @Column(name = "reserve_no", nullable = false)
    private Integer reserveNo;

    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;

    @Column(name = "time_period", nullable = false)
    private Integer timePeriod;

    @Column(name = "first_visit", nullable = false)
    private Boolean firstVisit;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_time", nullable = false, updatable = false, insertable = false)
    private Timestamp createTime;

    @Column(name = "update_time", insertable = false)
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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getClinicId() {
        return clinicId;
    }

    public void setClinicId(Integer clinicId) {
        this.clinicId = clinicId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
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

    public Boolean getFirstVisit() {
        return firstVisit;
    }

    public void setFirstVisit(Boolean firstVisit) {
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