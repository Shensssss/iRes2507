package web.patient.entity;


import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient extends Core{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "patient_id")
	private Integer patientId;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "gender")
	private int gender;

	@Column(name = "birthday")
	private String birthday;

	@Column(name = "phone")
	private String phone;

	@Column(name = "address")
	private String address;

	@Column(name = "email")
	private String email;

	@Column(name = "emergency_content")
	private String emergencyContent;

	@Column(name = "emergency_name")
	private String emergencyName;

	@Column(name = "relation")
	private int relation;

	@Column(name = "blood_type")
	private int bloodType;

	@Column(name = "notes")
	private String notes;

	@Lob
	@Column(name = "profile_picture", columnDefinition = "LONGTEXT")
	private String profilePicture;

	@Column(name = "status")
	private int status;

	@Column(name = "create_time", updatable = false, insertable = false)
	private Timestamp createTime;
	
	@Column(name = "update_time",insertable = false)
	private String updateTime;
}