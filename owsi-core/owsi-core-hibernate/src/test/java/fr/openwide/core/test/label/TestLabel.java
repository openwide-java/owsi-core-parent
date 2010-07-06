package fr.openwide.core.test.label;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.openwide.core.hibernate.exception.SecurityServiceException;
import fr.openwide.core.hibernate.exception.ServiceException;
import fr.openwide.core.test.AbstractHibernateCoreTestCase;
import fr.openwide.core.test.hibernate.example.business.label.model.Label;

public class TestLabel extends AbstractHibernateCoreTestCase {
	
	@Test
	public void testLabel() throws ServiceException, SecurityServiceException {
		Label label1 = new Label("label1", "value1");
		labelService.create(label1);
		
		Label label2 = new Label("label2", "value2");
		labelService.create(label2);
		
		assertTrue(labelService.count() == 2);
		
		assertEquals(label1, labelService.getById("label1"));
		assertEquals(-1, label1.compareTo(label2));
		
		labelService.delete(label2);
		
		assertTrue(labelService.count() == 1);
	}
	
	@Before
	@Override
	public void init() throws ServiceException, SecurityServiceException {
		super.init();
	}
	
	@After
	@Override
	public void close() {
		super.close();
	}

}
