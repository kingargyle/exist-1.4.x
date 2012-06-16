/*
 * Created on 20 juil. 2004
$Id$
 */
package org.exist.xmldb;

import java.io.File;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Iterator;


import org.apache.commons.io.FileUtils;
import org.exist.StandaloneServer;
import org.exist.storage.DBBroker;
import org.exist.util.Configuration;
import org.exist.validation.service.RemoteValidationService;
import org.exist.xquery.util.URIUtils;
import org.mortbay.util.MultiException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.Service;

/**
 * A test case for accessing collections remotely
 * 
 * @author jmv
 * @author Pierrick Brihaye <pierrick.brihaye@free.fr>
 */
public class RemoteCollectionTest extends RemoteDBTest {

	private static StandaloneServer server = null;
	private final static String XML_CONTENT = "<xml/>";
	private final static String BINARY_CONTENT = "TEXT";

	public RemoteCollectionTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		initServer();
		setUpRemoteDatabase();
	}

	protected void tearDown() throws Exception {
		removeCollection();
	}

	private void initServer() throws Exception {
		if (server == null) {
			cleanUp();
			server = new StandaloneServer();
			if (!server.isStarted()) {
				try {
					System.out.println("Starting standalone server...");
					String[] args = {};
					server.run(args);
					while (!server.isStarted()) {
						Thread.sleep(1000);
					}
				} catch (MultiException e) {
					boolean rethrow = true;
					Iterator i = e.getExceptions().iterator();
					while (i.hasNext()) {
						Exception e0 = (Exception) i.next();
						if (e0 instanceof BindException) {
							System.out.println("A server is running already !");
							rethrow = false;
							break;
						}
					}
					if (rethrow)
						throw e;
				}
			}
		}
	}

	public void testGetServices() throws Exception {
		Service[] services = getCollection().getServices();
		assertEquals(7, services.length);
		assertEquals(RemoteXPathQueryService.class, services[0].getClass());
		assertEquals(RemoteCollectionManagementService.class,
				services[1].getClass());
		assertEquals(RemoteUserManagementService.class, services[2].getClass());
		assertEquals(RemoteDatabaseInstanceManager.class,
				services[3].getClass());
		assertEquals(RemoteIndexQueryService.class, services[4].getClass());
		assertEquals(RemoteXUpdateQueryService.class, services[5].getClass());
		assertEquals(RemoteValidationService.class, services[6].getClass());
	}

	public void testIsRemoteCollection() throws Exception {
		assertTrue(getCollection().isRemoteCollection());
	}

	public void testGetPath() throws Exception {
		assertEquals(DBBroker.ROOT_COLLECTION + "/" + getTestCollectionName(),
				URIUtils.urlDecodeUtf8(getCollection().getPath()));
	}

	public void testCreateResource() throws Exception {
		Collection collection = getCollection();
		{ // XML resource:
			Resource resource = collection.createResource("testresource",
					"XMLResource");
			assertNotNull(resource);
			assertEquals(collection, resource.getParentCollection());
			resource.setContent("<?xml version='1.0'?><xml/>");
			collection.storeResource(resource);
		}
		{ // binary resource:
			Resource resource = collection.createResource("testresource",
					"BinaryResource");
			assertNotNull(resource);
			assertEquals(collection, resource.getParentCollection());
			resource.setContent("some random binary data here :-)");
			collection.storeResource(resource);
		}
	}

	public void testGetNonExistentResource() throws Exception {
		System.out.println("Retrieving non-existing resource");
		Collection collection = getCollection();
		Resource resource = collection.getResource("unknown.xml");
		assertNull(resource);
	}

	public void testListResources() throws Exception {
		ArrayList xmlNames = new ArrayList();
		xmlNames.add("xml1");
		xmlNames.add("xml2");
		xmlNames.add("xml3");
		createResources(xmlNames, "XMLResource");

		ArrayList binaryNames = new ArrayList();
		binaryNames.add("b1");
		binaryNames.add("b2");
		createResources(binaryNames, "BinaryResource");

		String[] actualContents = getCollection().listResources();
		System.out.println("Resources found: " + actualContents.length);
		for (int i = 0; i < actualContents.length; i++) {
			xmlNames.remove(actualContents[i]);
			binaryNames.remove(actualContents[i]);
		}
		assertEquals(0, xmlNames.size());
		assertEquals(0, binaryNames.size());
	}

	/**
	 * Trying to access a collection where the parent collection does not exist
	 * caused NullPointerException on DatabaseManager.getCollection() method.
	 */
	public void testParent() throws Exception {
		Collection c = DatabaseManager.getCollection(URI
				+ DBBroker.ROOT_COLLECTION, "admin", null);
		assertNull(c.getChildCollection("b"));

		System.err.println("col=" + c.getName());
		String parentName = c.getName() + "/" + System.currentTimeMillis();
		String colName = parentName + "/a";
		c = DatabaseManager.getCollection(URI + parentName, "admin", null);
		assertNull(c);

		// following fails for XmlDb 20051203
		c = DatabaseManager.getCollection(URI + colName, "admin", null);
		assertNull(c);
	}

	private void createResources(ArrayList names, String type) throws Exception {
		for (Iterator i = names.iterator(); i.hasNext();) {
			Resource res = getCollection().createResource((String) i.next(),
					type);
			if (type.equals("XMLResource"))
				res.setContent(XML_CONTENT);
			else
				res.setContent(BINARY_CONTENT);
			getCollection().storeResource(res);
		}
	}
}
