package fr.openwide.core.jpa.more.business.history.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.bindgen.Bindable;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.SortableField;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import fr.openwide.core.commons.util.CloneUtils;
import fr.openwide.core.commons.util.collections.CollectionUtils;
import fr.openwide.core.commons.util.fieldpath.FieldPath;
import fr.openwide.core.jpa.more.business.history.model.embeddable.HistoryValue;
import fr.openwide.core.jpa.search.util.HibernateSearchAnalyzer;

@Bindable
@SuppressFBWarnings("squid:S00107")
@MappedSuperclass
public abstract class AbstractHistoryLog<
				HL extends AbstractHistoryLog<HL, HET, HD>,
				HET extends Enum<HET>,
				HD extends AbstractHistoryDifference<HD, HL>>
		extends AbstractHistoryElement<HL, HL> {

	private static final long serialVersionUID = -1146280203615151992L;

	public static final String DATE = "date";

	private static final String SUBJECT = "subject";
	private static final String SUBJECT_PREFIX = SUBJECT + ".";

	private static final String ALL_OBJECTS = "allObjects";
	private static final String ALL_OBJECTS_PREFIX = ALL_OBJECTS + ".";

	private static final String MAIN_OBJECT = "mainObject";
	private static final String MAIN_OBJECT_PREFIX = MAIN_OBJECT + ".";
	private static final String OBJECT1 = "object1";
	private static final String OBJECT1_PREFIX = OBJECT1 + ".";
	private static final String OBJECT2 = "object2";
	private static final String OBJECT2_PREFIX = OBJECT2 + ".";
	private static final String OBJECT3 = "object3";
	private static final String OBJECT3_PREFIX = OBJECT3 + ".";
	private static final String OBJECT4 = "object4";
	private static final String OBJECT4_PREFIX = OBJECT4 + ".";
	
	public static final String SUBJECT_REFERENCE = SUBJECT_PREFIX + HistoryValue.REFERENCE;
	public static final String ALL_OBJECTS_REFERENCE = ALL_OBJECTS_PREFIX + HistoryValue.REFERENCE;
	public static final String OBJECT1_REFERENCE = OBJECT1_PREFIX + HistoryValue.REFERENCE;
	public static final String OBJECT2_REFERENCE = OBJECT2_PREFIX + HistoryValue.REFERENCE;
	public static final String OBJECT3_REFERENCE = OBJECT3_PREFIX + HistoryValue.REFERENCE;
	public static final String OBJECT4_REFERENCE = OBJECT4_PREFIX + HistoryValue.REFERENCE;

	public static final String HAS_DIFFERENCES = "hasDifferences";
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Basic(optional = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Field(name = DATE)
	@SortableField(forField = DATE)
	private Date date;

	@Basic(optional = false)
	@Enumerated(EnumType.STRING)
	@Field(analyzer = @Analyzer(definition = HibernateSearchAnalyzer.KEYWORD))
	private HET eventType;
	
	@Embedded
	@IndexedEmbedded(prefix = SUBJECT_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue subject;
	
	@Embedded
	@IndexedEmbedded(prefix = MAIN_OBJECT_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue mainObject;
	
	@Embedded
	@IndexedEmbedded(prefix = OBJECT1_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue object1 = new HistoryValue();

	@Embedded
	@IndexedEmbedded(prefix = OBJECT2_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue object2 = new HistoryValue();

	@Embedded
	@IndexedEmbedded(prefix = OBJECT3_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue object3 = new HistoryValue();

	@Embedded
	@IndexedEmbedded(prefix = OBJECT4_PREFIX, includePaths = {HistoryValue.REFERENCE})
	private HistoryValue object4 = new HistoryValue();
	
	@Basic
	private String comment;

	@OneToMany(mappedBy = "parentLog", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderColumn
	private List<HD> differences = Lists.newArrayList();

	protected AbstractHistoryLog() {
		// nothing to do
	}
	
	protected AbstractHistoryLog(Date date, HET eventType, HistoryValue mainObject) {
		this.date = CloneUtils.clone(date);
		this.eventType = eventType;
		this.mainObject = mainObject;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	public String getNameForToString() {
		return object1 == null ? null : object1.getLabel();
	}
	
	@Override
	public String getDisplayName() {
		return toString();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	@Transient
	public HL getRootLog() {
		return (HL) this;
	}
	
	@Override
	@Transient
	protected HL getParent() {
		return null;
	}
	
	@Override
	@Transient
	public FieldPath getRelativePath() {
		return FieldPath.ROOT;
	}

	public Date getDate() {
		return CloneUtils.clone(date);
	}

	public void setDate(Date date) {
		this.date = CloneUtils.clone(date);
	}

	public HET getEventType() {
		return eventType;
	}

	public void setEventType(HET action) {
		this.eventType = action;
	}

	public HistoryValue getSubject() {
		return subject;
	}

	public void setSubject(HistoryValue subject) {
		this.subject = subject;
	}

	public HistoryValue getMainObject() {
		return mainObject;
	}

	public void setMainObject(HistoryValue mainObject) {
		this.mainObject = mainObject;
	}

	public HistoryValue getObject1() {
		return object1;
	}

	public void setObject1(HistoryValue object1) {
		this.object1 = object1;
	}

	public HistoryValue getObject2() {
		return object2;
	}

	public void setObject2(HistoryValue object2) {
		this.object2 = object2;
	}

	public HistoryValue getObject3() {
		return object3;
	}

	public void setObject3(HistoryValue object3) {
		this.object3 = object3;
	}

	public HistoryValue getObject4() {
		return object4;
	}

	public void setObject4(HistoryValue object4) {
		this.object4 = object4;
	}
	
	@IndexedEmbedded(prefix = ALL_OBJECTS_PREFIX, includePaths = {HistoryValue.REFERENCE})
	public Set<HistoryValue> getAllObjects() {
		Set<HistoryValue> result = Sets.newLinkedHashSet();
		for (HistoryValue value : new HistoryValue[] {mainObject, object1, object2, object3, object4}) {
			if (value != null) {
				result.add(value);
			}
		}
		return result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<HD> getDifferences() {
		return Collections.unmodifiableList(differences);
	}

	public void setDifferences(List<HD> differences) {
		CollectionUtils.replaceAll(this.differences, differences);
	}
	
	@Transient
	@Field(name = HAS_DIFFERENCES, analyze = Analyze.NO)
	public boolean isDifferencesNonEmpty() {
		return !differences.isEmpty();
	}

}
