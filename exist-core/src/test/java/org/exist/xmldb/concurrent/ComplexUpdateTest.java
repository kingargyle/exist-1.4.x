package org.exist.xmldb.concurrent;

import org.exist.storage.DBBroker;
import org.exist.xmldb.concurrent.action.ComplexUpdateAction;
import org.junit.Before;
import org.xmldb.api.modules.XMLResource;
import static org.junit.Assert.*;

/**
 * @author wolf
 */
public class ComplexUpdateTest extends ConcurrentTestBase {

	private final static String URI = "xmldb:exist://" + DBBroker.ROOT_COLLECTION;
	
	private final static String XML =
		"<TEST><USER-SESSION-DATA version=\"0\"/></TEST>";
	
	
	/**
     * 
     * 
     * @param name 
     */
	public ComplexUpdateTest(String name) {
		super(name, URI, "complex");
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xmldb.test.concurrent.ConcurrentTestBase#setUp()
	 */
	@Before
	public void setUp() {
		try {
			super.setUp();			
			XMLResource res = (XMLResource)getTestCollection().createResource("R01.xml", "XMLResource");
			res.setContent(XML);
			getTestCollection().storeResource(res);
			getTestCollection().close();			
			addAction(new ComplexUpdateAction(URI + "/complex", "R01.xml", 200), 1, 0, 0);
		} catch (Exception e) {            
            fail(e.getMessage()); 
        }				
	}
	
	/* (non-Javadoc)
	 * @see org.exist.xmldb.test.concurrent.ConcurrentTestBase#tearDown()
	 */
	@Before
	public void tearDown() {
		try {
			DBUtils.shutdownDB(rootColURI);
		} catch (Exception e) {            
            fail(e.getMessage()); 
        }				
	}

}
