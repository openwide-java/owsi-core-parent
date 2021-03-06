package fr.openwide.core.test.jpa.more.business.history.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.bindgen.Bindable;

import fr.openwide.core.jpa.more.business.history.model.AbstractHistoryDifference;
import fr.openwide.core.jpa.more.business.history.model.atomic.HistoryDifferenceEventType;
import fr.openwide.core.jpa.more.business.history.model.embeddable.HistoryDifferencePath;
import fr.openwide.core.jpa.more.business.history.model.embeddable.HistoryValue;

@Entity
@Bindable
@Cacheable
@Access(AccessType.FIELD)
@Table(
		indexes = {
				@Index(name="idx_HistoryDifference_parentLog", columnList = "parentLog_id"),
				@Index(name="idx_HistoryDifference_parentDifference", columnList = "parentDifference_id")
		}
)
public class TestHistoryDifference extends AbstractHistoryDifference<TestHistoryDifference, TestHistoryLog> {

	private static final long serialVersionUID = -8437788725042615126L;

	public TestHistoryDifference() {
		super();
	}

	public TestHistoryDifference(HistoryDifferencePath path, HistoryDifferenceEventType action, HistoryValue before,
			HistoryValue after) {
		super(path, action, before, after);
	}


}
