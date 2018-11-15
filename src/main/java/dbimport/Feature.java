package dbimport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="feature")
public class Feature {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="incrementor")
	private int id;
	
	@Column(name="fkProteinEntry")
	private int fk_proteinEntry;
	
	@Column(name="featureType")
	private String featureType;
	
	@Column(name="description")
	private String description;
	
	@Column(name="seqSqc")
	private String seqSqc;
	
	@Column(name="status")
	private String status;
	
    public String getFeatureType() {
		return featureType;
	}

	public void setFeatureType(String featureType) {
		this.featureType = featureType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSeqSqc() {
		return seqSqc;
	}

	public void setSeqSqc(String seqSqc) {
		this.seqSqc = seqSqc;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Feature(String featureType, String description, String seqSqc, String status, int fkProteinEntry) {
		super();
		this.featureType = featureType;
		this.description = description;
		this.seqSqc = seqSqc;
		this.status = status;
		this.fk_proteinEntry = fkProteinEntry;
	}

	public Feature(int fkProteinEntry) {
		super();
		this.fk_proteinEntry = fkProteinEntry;
	}

	@Override
    public String toString() {
        return "featureType: " + getFeatureType() + " description " + getDescription() + " seqSqc " + getSeqSqc() + " status " +getStatus();
    }
}
