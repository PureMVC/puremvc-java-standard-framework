/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.interfaces;

/**
 * The interface definition for a PureMVC Model.
 * 
 * <P>
 * In PureMVC, <code>IModel</code> implementors provide access to
 * <code>IProxy</code> objects by named lookup.
 * </P>
 * 
 * <P>
 * An <code>IModel</code> assumes these responsibilities:
 * </P>
 * 
 * <UL>
 * <LI>Maintain a cache of <code>IProxy</code> instances</LI>
 * <LI>Provide methods for registering, retrieving, and removing
 * <code>IProxy</code> instances</LI>
 * </UL>
 */
public interface IModel
{

	/**
	 * Register an <code>IProxy</code> instance with the <code>Model</code>.
	 * 
	 * @param proxy
	 *            an object reference to be held by the <code>Model</code>.
	 */
	public void registerProxy( IProxy proxy );

	/**
	 * Retrieve an <code>IProxy</code> instance from the Model.
	 * 
	 * @param proxy
	 * @return the <code>IProxy</code> instance previously registered with the
	 *         given <code>proxyName</code>.
	 */
	public IProxy retrieveProxy( String proxy );

	/**
	 * Remove an <code>IProxy</code> instance from the Model.
	 * 
	 * @param proxy
	 *            name of the <code>IProxy</code> instance to be removed.
	 */
	public IProxy removeProxy( String proxy );
	
	/**
	 * Check if a Proxy is registered
	 * 
	 * @param proxyName
	 * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
	 */
	boolean hasProxy(String proxyName );
}
