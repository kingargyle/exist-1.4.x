package org.exist.xmldb;

import static org.junit.Assert.*;

import org.exist.storage.DBBroker;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.*;
import org.xmldb.api.modules.*;

import junit.framework.TestCase;
import junit.textui.TestRunner;

public class CopyMoveTest {

	private final static String URI = "xmldb:exist://"
			+ DBBroker.ROOT_COLLECTION;
	private final static String DRIVER = "org.exist.xmldb.DatabaseImpl";

	@Test
	public void testCopyResourceChangeName() throws Exception {
		Collection c = null;
		try {
			c = setupTestCollection();
			XMLResource original = (XMLResource) c.createResource("original",
					XMLResource.RESOURCE_TYPE);
			original.setContent("<sample/>");
			c.storeResource(original);
			CollectionManagementServiceImpl cms = (CollectionManagementServiceImpl) c
					.getService("CollectionManagementService", "1.0");
			cms.copyResource("original", "", "duplicate");
			assertEquals(2, c.getResourceCount());
			XMLResource duplicate = (XMLResource) c.getResource("duplicate");
			assertNotNull(duplicate);
			System.out.println(duplicate.getContent());
		} finally {
			closeCollection(c);
		}
	}

	@Test
	@Ignore("Has issues running as part of suite.")
	public void testQueryCopiedResource() throws Exception {
		Collection c = null;
		try {
			c = setupTestCollection();
			XMLResource original = (XMLResource) c.createResource("original",
					XMLResource.RESOURCE_TYPE);
			original.setContent("<sample/>");
			c.storeResource(original);
			CollectionManagementServiceImpl cms = (CollectionManagementServiceImpl) c
					.getService("CollectionManagementService", "1.0");
			cms.copyResource("original", "", "duplicate");
			XMLResource duplicate = (XMLResource) c.getResource("duplicate");
			assertNotNull(duplicate);
			XPathQueryService xq = (XPathQueryService) c.getService(
					"XPathQueryService", "1.0");
			ResourceSet rs = xq.queryResource("duplicate", "/sample");
			assertEquals(1, rs.getSize());
		} finally {
			closeCollection(c);
		}
	}

	private Collection setupTestCollection() throws Exception {
		Collection root = DatabaseManager.getCollection(URI);
		CollectionManagementService rootcms = (CollectionManagementService) root
				.getService("CollectionManagementService", "1.0");
		Collection c = root.getChildCollection("test");
		if (c != null)
			rootcms.removeCollection("test");
		rootcms.createCollection("test");
		c = DatabaseManager.getCollection(URI + "/test");
		assertNotNull(c);
		return c;
	}

	@Before
	public void setUp() throws Exception {
		// initialize driver
		Database database = (Database) Class.forName(DRIVER).newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
	}

	private void closeCollection(Collection collection) throws Exception {
		if (null != collection) {
			collection.close();
		}
	}

}
