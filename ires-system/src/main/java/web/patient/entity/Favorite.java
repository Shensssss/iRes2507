package web.patient.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "favorites", uniqueConstraints = @UniqueConstraint(columnNames = { "patient_id", "clinic_id" }))
public class Favorite {

	@Id
	@Column(name = "favorites_id")
	private String favoritesId;

	@Column(name = "patient_id", nullable = false)
	private Integer patientId;

	@Column(name = "clinic_id", nullable = false)
	private Integer clinicId;

	@Column(name = "create_time")
	private Timestamp createTime;

	// Getter / Setter

	public String getFavoritesId() {
		return favoritesId;
	}

	public void setFavoritesId(String favoritesId) {
		this.favoritesId = favoritesId;
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

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}