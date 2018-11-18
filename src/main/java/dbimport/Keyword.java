package dbimport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="keywords")
public class Keyword {
	@Id
	@Column(name="id")
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="incrementator", strategy ="increment")
	private int id;
	
	@Column(name="fkProteinEntryId")
	private int fkProteinEntryId;
	
	@Column(name="name")
	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFkProteinEntryId() {
		return fkProteinEntryId;
	}

	public void setFkProteinEntryId(int fkProteinEntryId) {
		this.fkProteinEntryId = fkProteinEntryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Keyword(int fkProteinEntryId, String name) {
		super();
		this.fkProteinEntryId = fkProteinEntryId;
		this.name = name;
	}

	public Keyword() {
		super();
	}
	
}
