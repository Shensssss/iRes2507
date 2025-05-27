package web.clinic.entity;

import java.sql.Timestamp;

// Q1:不用ALL,就無法讀 table 
//import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "clinic")
public class Clinic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "clinic_id")
	private Integer clinicId;

	@Column(name = "clinic_name", nullable = false, length = 50)
	private String clinicName;

	@Column(name = "agency_id", nullable = false, length = 20)
	private String agencyId;

	@Column(name = "account", nullable = false, length = 50)
	private String account;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Column(name = "phone", nullable = false, length = 12)
	private String phone;

	@Column(name = "address_city", nullable = false, length = 25)
	private String addressCity;

	@Column(name = "address_town", nullable = false, length = 25)
	private String addressTown;

	@Column(name = "address_road", nullable = false, length = 25)
	private String addressRoad;

	@Column(name = "web", length = 255)
	private String web;

	@Column(name = "morning", nullable = false, length = 50)
	private String morning;

	@Column(name = "afternoon", nullable = false, length = 50)
	private String afternoon;

	@Column(name = "night", nullable = false, length = 50)
	private String night;

	@Column(name = "week_morning", nullable = false, length = 50)
	private String weekMorning;

	@Column(name = "week_afternoon", nullable = false, length = 50)
	private String weekAfternoon;

	@Column(name = "week_night", nullable = false, length = 50)
	private String weekNight;

	@Column(name = "registration_fee", nullable = false)
	private Integer registrationFee;

	@Column(name = "memo", length = 1000)
	private String memo;

	@Column(name = "create_id", length = 25)
	private String createId;

	@Column(name = "create_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createTime;

	@Column(name = "update_id", length = 25)
	private String updateId;

	@Column(name = "update_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updateTime;

	// Getters and Setters
	public Integer getClinicId() {
		return clinicId;
	}

	public void setClinicId(Integer clinicId) {
		this.clinicId = clinicId;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddressCity() {
		return addressCity;
	}

	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}

	public String getAddressTown() {
		return addressTown;
	}

	public void setAddressTown(String addressTown) {
		this.addressTown = addressTown;
	}

	public String getAddressRoad() {
		return addressRoad;
	}

	public void setAddressRoad(String addressRoad) {
		this.addressRoad = addressRoad;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getMorning() {
		return morning;
	}

	public void setMorning(String morning) {
		this.morning = morning;
	}

	public String getAfternoon() {
		return afternoon;
	}

	public void setAfternoon(String afternoon) {
		this.afternoon = afternoon;
	}

	public String getNight() {
		return night;
	}

	public void setNight(String night) {
		this.night = night;
	}

	public String getWeekMorning() {
		return weekMorning;
	}

	public void setWeekMorning(String weekMorning) {
		this.weekMorning = weekMorning;
	}

	public String getWeekAfternoon() {
		return weekAfternoon;
	}

	public void setWeekAfternoon(String weekAfternoon) {
		this.weekAfternoon = weekAfternoon;
	}

	public String getWeekNight() {
		return weekNight;
	}

	public void setWeekNight(String weekNight) {
		this.weekNight = weekNight;
	}

	public Integer getRegistrationFee() {
		return registrationFee;
	}

	public void setRegistrationFee(Integer registrationFee) {
		this.registrationFee = registrationFee;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
