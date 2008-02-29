/*
 * PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved.
 * Your reuse is governed by Creative Commons Attribution 3.0 License
 */

package org.puremvc.java.core.model;

import java.util.Hashtable;

import org.puremvc.java.interfaces.IModel;
import org.puremvc.java.interfaces.IProxy;

/**
 * A Singleton <code>IModel</code> implementation.
 * 
 * <P>
 * In PureMVC, the <code>Model</code> class provides access to model objects
 * (Proxies) by named lookup.
 * 
 * <P>
 * The <code>Model</code> assumes these responsibilities:
 * </P>
 * 
 * <UL>
 * <LI>Maintain a cache of <code>IProxy</code> instances.</LI>
 * <LI>Provide methods for registering, retrieving, and removing
 * <code>IProxy</code> instances.</LI>
 * </UL>
 * 
 * <P>
 * Your application must register <code>IProxy</code> instances with the
 * <code>Model</code>. Typically, you use an <code>ICommand</code> to
 * create and register <code>IProxy</code> instances once the
 * <code>Facade</code> has initialized the Core actors.
 * </p>
 * 
 * @see org.puremvc.java.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.interfaces.IProxy IProxy
 */
public class Model implements IModel
{

	/**
	 * Singleton instance
	 */ 
	protected static Model instance;

	/**
	 * Mapping of proxyNames to IProxy instances
	 */
	protected Hashtable proxyMap;

	/**
	 * Constructor.
	 * 
	 * <P>
	 * This <code>IModel</code> implementation is a Singleton, so you should
	 * not call the constructor directly, but instead call the static Singleton
	 * Factory method <code>Model.getInstance()</code>
	 * 
	 * @throws Error
	 *             Error if Singleton instance has already been constructed
	 * 
	 */
	protected Model( )
	{
		instance = this;
		this.proxyMap = new Hashtable();
		initializeModel();
	}

	/**
	 * Initialize the Singleton <code>Model</code> instance.
	 * 
	 * <P>
	 * Called automatically by the constructor, this is your opportunity to
	 * initialize the Singleton instance in your subclass without overriding the
	 * constructor.
	 * </P>
	 * 
	 */
	protected void initializeModel( )
	{
	}

	/**
	 * <code>Model</code> Singleton Factory method.
	 * 
	 * @return the Singleton instance
	 */
	public synchronized static Model getInstance( )
	{
		if (instance == null) {
			instance = new Model();
		}
		return instance;
	}

	/**
	 * Register an <code>Proxy</code> with the <code>Model</code>.
	 * 
	 * @param proxy
	 *            an <code>Proxy</code> to be held by the <code>Model</code>.
	 */
	public void registerProxy( IProxy proxy )
	{
		this.proxyMap.put( proxy.getProxyName(), proxy );
	}

	/**
	 * Remove an <code>Proxy</code> from the <code>Model</code>.
	 * 
	 * @param proxy
	 *            name of the <code>Proxy</code> instance to be removed.
	 */
	public IProxy removeProxy( String proxy )
	{
		return (IProxy) this.proxyMap.remove( proxy );
	}

	/**
	 * Retrieve an <code>Proxy</code> from the <code>Model</code>.
	 * 
	 * @param proxy
	 * @return the <code>Proxy</code> instance previously registered with the
	 *         given <code>proxyName</code>.
	 */
	public IProxy retrieveProxy( String proxy )
	{
		return (IProxy) this.proxyMap.get( proxy );
	}
}
