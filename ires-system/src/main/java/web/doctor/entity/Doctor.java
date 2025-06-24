package web.doctor.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import core.util.ListToJsonConverter;
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
@Table(name = "doctor")
public class Doctor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "doctor_id", updatable = false)
	private Integer doctorId;
	
	@Column(name = "doctor_name")
	private String doctorName;
	
	//FK多位醫師對一間診所
	@ManyToOne
	@JoinColumn(name = "clinic_id", nullable = false, updatable = false) //insertable不可以false因為我要手動填入?
	private Clinic clinic;
	
	@Convert(converter = ListToJsonConverter.class)
	@Column(name = "education", columnDefinition = "json")
	private List<String> education = new ArrayList<>();

	@Convert(converter = ListToJsonConverter.class)
	@Column(name = "experience", columnDefinition = "json")
	private List<String> experience = new ArrayList<>();

	@Convert(converter = ListToJsonConverter.class)
	@Column(name = "memo", columnDefinition = "json")
	private List<String> memo = new ArrayList<>();
	
	@Lob
	@Column(name = "profile_picture")
	private byte[] profilePicture;
	
	@Column(name = "create_id")
	private String createId;
	
	@Column(name = "create_time")
	private Timestamp createTime;
	
	@Column(name = "update_id")
	private String updateId;

	@Column(name = "update_time")
	private Timestamp updateTime;

}
