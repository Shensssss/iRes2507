package web.clinic.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "clinic")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clinic_id")
    private Integer clinicId;

    @Column(name = "clinic_name")
    private String clinicName;

    @Column(name = "agency_id")
    private String agencyId;

    @Column(name = "account")
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_town")
    private String addressTown;

    @Column(name = "address_road")
    private String addressRoad;

    @Column(name = "web")
    private String web;

    @Column(name = "morning")
    private String morning;

    @Column(name = "afternoon")
    private String afternoon;

    @Column(name = "night")
    private String night;

    @Column(name = "week_morning")
    private String weekMorning;

    @Column(name = "week_afternoon")
    private String weekAfternoon;

    @Column(name = "week_night")
    private String weekNight;

    @Column(name = "registration_fee")
    private Integer registrationFee;

    @Column(name = "memo")
    private String memo;

    @Column(name = "create_id")
    private String createId;

    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private Timestamp createTime;

    @Column(name = "update_id")
    private String updateId;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    @Column(name = "latitude")
    private BigDecimal latitude;

    @Column(name = "longitude")
    private BigDecimal longitude;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;
}
