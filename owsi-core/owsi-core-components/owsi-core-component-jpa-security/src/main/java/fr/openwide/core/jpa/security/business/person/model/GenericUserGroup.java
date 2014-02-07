package fr.openwide.core.jpa.security.business.person.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;

import org.bindgen.Bindable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.SortComparator;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.springframework.security.acls.model.Permission;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import com.mysema.query.annotations.PropertyType;
import com.mysema.query.annotations.QueryType;

import fr.openwide.core.commons.util.collections.CollectionUtils;
import fr.openwide.core.jpa.business.generic.model.GenericEntity;
import fr.openwide.core.jpa.search.util.HibernateSearchAnalyzer;
import fr.openwide.core.jpa.security.business.authority.model.Authority;
import fr.openwide.core.jpa.security.business.person.util.AbstractUserComparator;

@MappedSuperclass
@Bindable
public abstract class GenericUserGroup<G extends GenericUserGroup<G, P>, P extends GenericUser<P, G>>
		extends GenericEntity<Long, G>
		implements IUserGroup {

	private static final long serialVersionUID = 2156717229285615454L;
	
	@Id
	@DocumentId
	@GeneratedValue
	private Long id;

	@Field(analyzer = @Analyzer(definition = HibernateSearchAnalyzer.TEXT))
	private String name;
	
	@JsonIgnore
	@org.codehaus.jackson.annotate.JsonIgnore
	@ManyToMany(mappedBy = "groups")
	@Cascade({CascadeType.SAVE_UPDATE})
	@SortComparator(AbstractUserComparator.class)
	private Set<P> persons = Sets.newTreeSet(AbstractUserComparator.get());
	
	@JsonIgnore
	@org.codehaus.jackson.annotate.JsonIgnore
	@ManyToMany
	@Cascade({CascadeType.SAVE_UPDATE})
	@OrderBy("name")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Authority> authorities = new LinkedHashSet<Authority>();
	
	@Type(type = "org.hibernate.type.StringClobType")
	private String description;
	
	@Column(nullable = false)
	private boolean locked = false;
	
	public GenericUserGroup() {
	}

	public GenericUserGroup(String name) {
		this.name = name;
	}
	
	protected abstract G thisAsConcreteType();

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getDisplayName() {
		return this.getName();
	}
	
	public Set<P> getPersons() {
		return Collections.unmodifiableSet(persons);
	}

	public void setPersons(Set<P> persons) {
		CollectionUtils.replaceAll(this.persons, persons);
	}
	
	public void addPerson(P person) {
		this.persons.add(person);
		person.addGroup(thisAsConcreteType());
	}
	
	public void removePerson(P person) {
		this.persons.remove(person);
		person.removeGroup(thisAsConcreteType());
	}
	
	@Override
	public Set<Authority> getAuthorities() {
		return Collections.unmodifiableSet(authorities);
	}

	public void setAuthorities(Set<Authority> authorities) {
		CollectionUtils.replaceAll(this.authorities, authorities);
	}
	
	public void addAuthority(Authority authority) {
		this.authorities.add(authority);
	}
	
	public void removeAuthority(Authority authority) {
		this.authorities.remove(authority);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
	@Override
	@QueryType(PropertyType.NONE)
	public Set<? extends Permission> getPermissions() {
		return Sets.newHashSetWithExpectedSize(0);
	}

	@Override
	public int compareTo(G group) {
		if(this == group) {
			return 0;
		}
		return DEFAULT_STRING_COLLATOR.compare(this.getName(), group.getName());
	}

	@Override
	public String getNameForToString() {
		return getName();
	}

}