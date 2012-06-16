// $Header$

package org.exist.xmldb;

import static org.junit.Assert.*;
import junit.framework.TestCase;
import org.xmldb.api.*;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;
import org.exist.AbstractDBTest;
import org.exist.storage.DBBroker;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ResourceSetTest extends AbstractDBTest {

	String XPathPrefix;
	String query1;
	String query2;
	int expected;
	private final static String URI = "xmldb:exist://"
			+ DBBroker.ROOT_COLLECTION;
	private final static String DRIVER = "org.exist.xmldb.DatabaseImpl";

	@Before
	public void setUp() throws Exception {
		try {
			cleanUp();
			// initialize driver
			Class cl = Class.forName(DRIVER);
			Database database = (Database) cl.newInstance();
			database.setProperty("create-database", "true");
			DatabaseManager.registerDatabase(database);
		} catch (ClassNotFoundException e) {
		} catch (InstantiationException e) {
		} catch (IllegalAccessException e) {
		}

		// Currently (2003-04-02) fires an exception in FunPosition:
		XPathPrefix = "xmldb:document('" + DBBroker.ROOT_COLLECTION
				+ "/test/shakes.xsl')/*/*"; // "xmldb:document('" +
											// DBBroker.ROOT_COLLECTION +
											// "/test/macbeth.xml')/*/*";
		query1 = XPathPrefix + "[position()>=5 ]";
		query2 = XPathPrefix + "[position()<=10]";
		expected = 87;
	}

	@Test
	@Ignore("Missing Setup for loading data into db")
	public void testIntersection() throws Exception {
		// try to get collection
		Collection testCollection = DatabaseManager
				.getCollection(URI + "/test");
		assertNotNull(testCollection);
		XPathQueryService service = (XPathQueryService) testCollection
				.getService("XPathQueryService", "1.0");

		System.out.println("query1: " + query1);
		ResourceSet result1 = service.query(query1);
		System.out.println("query1: getSize()=" + result1.getSize());

		System.out.println("query2: " + query2);
		ResourceSet result2 = service.query(query2);
		System.out.println("query2: getSize()=" + result2.getSize());

		assertEquals("size of intersection of " + query1 + " and " + query2
				+ " yields ", expected,
				(ResourceSetHelper.intersection(result1, result2)).getSize());
	}
}
