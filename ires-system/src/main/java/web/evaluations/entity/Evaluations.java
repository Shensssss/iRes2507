package web.evaluations.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import core.pojo.Core;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.clinic.entity.Clinic;
import web.patient.entity.Patient;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "evaluations")
public class Evaluations extends Core {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "evaluate_id")
	private String evaluateId;
	@ManyToOne
	@JoinColumn(name = "patient_id")
	private Patient patient;
	@ManyToOne
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;
	@Column(name = "rating")
	private Integer rating;
	@Column(name="comment")
	private String comment;
	@Column(name = "create_time", updatable = false, insertable = false)
	private Timestamp createTime;
}
