package com.page5of4.codon.useful.spring;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class OsgiEntityManagerFactoryBean {
	private BundleContext bundleContext;
	private String filterKey = "osgi.unit.name";
	private String name;

	public BundleContext getBundleContext() {
		return bundleContext;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	public String getFilterKey() {
		return filterKey;
	}

	public void setFilterKey(String filterKey) {
		this.filterKey = filterKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setFilterValue(String name) {
		this.name = name;
	}

	public ServiceReference getServiceReference() throws InvalidSyntaxException {
		String filter = String.format("(%s=%s)", filterKey, name);
		ServiceReference[] serviceReferences = bundleContext.getServiceReferences(EntityManagerFactory.class.getName(), filter);
		if (serviceReferences != null && serviceReferences.length > 0) {
			return serviceReferences[0];
		} else {
			throw new RuntimeException("EntityManagerFactory is unavailable");
		}
	}

	public EntityManager create() throws InvalidSyntaxException {
		ServiceReference reference = getServiceReference();
		EntityManagerFactory emf = (EntityManagerFactory)bundleContext.getService(reference);
		EntityManager em = emf.createEntityManager();
		return em;
	}
}
