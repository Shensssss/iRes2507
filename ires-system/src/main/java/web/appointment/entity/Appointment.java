package web.appointment.entity;

import jakarta.persistence.*;
import web.patient.entity.Patient;
import web.clinic.entity.Clinic;

import java.sql.Timestamp;
import java.util.Date;

@Entity(name = "Appointment")
@Table(name = "appointment")
public class Appointment {

    @Id
    @Column(name = "appointment_id", length = 36, nullable = false)
    private String appointmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id", nullable = false)
    private Clinic clinic;

    @Column(name = "doctor_id", nullable = false)
    private Integer doctorId;

    @Column(name = "reserve_no", nullable = false)
    private Integer reserveNo;

    @Temporal(TemporalType.DATE)
    @Column(name = "appointment_date", nullable = false)
    private Date appointmentDate;

    @Column(name = "time_period", nullable = false)
    private Integer timePeriod;

    @Column(name = "first_visit", nullable = false)
    private Boolean firstVisit;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "create_time", nullable = false, insertable = false, updatable = false)
    private Timestamp createTime;

    @Column(name = "update_time", insertable = false, updatable = false)
    private Timestamp updateTime;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}