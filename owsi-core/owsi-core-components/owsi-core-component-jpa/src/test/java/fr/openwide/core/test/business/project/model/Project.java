package fr.openwide.core.test.business.project.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.spring.util.StringUtils;
import fr.openwide.core.test.business.company.model.Company;
import fr.openwide.core.test.business.person.model.Person;

@Entity
public class Project extends GenericEntity<Long, Project> {
	
	private static final long serialVersionUID = -5735420033376178883L;

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@ManyToOne
	private Company company;
	
	@ManyToOne(fetch = FetchType.LAZY) // WARNING: this "laziness" is important to some tests
	private Person leader;

	@ManyToMany(mappedBy = "workedProjects")
	private List<Person> team = new LinkedList<Person>();

	public Project() {
	}

	public Project(String name) {
		this.name = name;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Person getLeader() {
		return leader;
	}

	public void setLeader(Person leader) {
		this.leader = leader;
	}

	public List<Person> getTeam() {
		return team;
	}

	public void setTeam(List<Person> team) {
		this.team = team;
	}

	@Override
	public String getNameForToString() {
		return getName();
	}
	
	@Override
	public int compareTo(Project project) {
		if (this == project) {
			return 0;
		}
		return StringUtils.removeAccents(this.getName()).compareToIgnoreCase(StringUtils.removeAccents(project.getName()));
	}

	@Override
	public String getDisplayName() {
		return getName();
	}
}
