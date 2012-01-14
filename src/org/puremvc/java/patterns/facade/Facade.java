/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.patterns.facade;

import org.puremvc.java.core.Controller;
import org.puremvc.java.core.Model;
import org.puremvc.java.core.View;
import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.IFacade;
import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.IProxy;
import org.puremvc.java.patterns.observer.Notification;

/**
 * A base Singleton <code>IFacade</code> implementation.
 * 
 * @see org.puremvc.java.core.Model Model
 * @see org.puremvc.java.core.View View
 * @see org.puremvc.java.core.Controller Controller 
 */
public class Facade implements IFacade
{
	/**
	 * The Singleton instance of the Facade
	 */
	protected static Facade instance = null;

	/**
	 * Reference to the Controller
	 */
	protected Controller controller = null;

	/**
	 * Reference to the Model
	 */
	protected Model model = null;

	/**
	 * Reference to the View
	 */
	protected View view = null;

	/**
	 * Constructor.
	 * 
	 * <P>
	 * This <code>IFacade</code> implementation is a Singleton, so you should
	 * not call the constructor directly, but instead call the static Singleton
	 * Factory method <code>Facade.getInstance()</code>
	 * 
	 */
	protected Facade()
	{
		initializeFacade();
	}

	/**
	 * Initialize the Multiton <code>Facade</code> instance.
	 * 
	 * <P>
	 * Called automatically by the constructor. Override in your
	 * subclass to do any subclass specific initializations. Be
	 * sure to call <code>super.initializeFacade()</code>, though.</P>
	 */
	protected void initializeFacade( )
	{
		initializeModel();
		initializeController();
		initializeView();
	}

	/**
	 * Facade Singleton Factory method
	 * 
	 * @return
	 *		The Singleton instance of the Facade
	 */
	public synchronized static Facade getInstance()
	{
		if( instance == null )
			instance = new Facade();

		return instance;
	}

	/**
	 * Initialize the <code>Controller</code>.
	 * 
	 * <P>
	 * Called by the <code>initializeFacade</code> method. Override this
	 * method in your subclass of <code>Facade</code> if one or both of the
	 * following are true:
	 * <UL>
	 * <LI> You wish to initialize a different <code>IController</code>.</LI>
	 * <LI> You have <code>Commands</code> to register with the
	 * <code>Controller</code> at startup.</code>. </LI>
	 * </UL>
	 * If you don't want to initialize a different <code>IController</code>,
	 * call <code>super.initializeController()</code> at the beginning of your
	 * method, then register <code>Command</code>s.
	 * </P>
	 */
	protected void initializeController()
	{
		if( controller != null )
			return;

		controller = Controller.getInstance();
	}

	/**
	 * Initialize the <code>Model</code>.
	 * 
	 * <P>
	 * Called by the <code>initializeFacade</code> method. Override this
	 * method in your subclass of <code>Facade</code> if one or both of the
	 * following are true:
	 * <UL>
	 * <LI> You wish to initialize a different <code>IModel</code>.</LI>
	 * <LI> You have <code>Proxy</code>s to register with the Model that do
	 * not retrieve a reference to the Facade at construction time.</code></LI>
	 * </UL>
	 * If you don't want to initialize a different <code>IModel</code>, call
	 * <code>super.initializeModel()</code> at the beginning of your method,
	 * then register <code>Proxy</code>s.
	 * <P>
	 * Note: This method is <i>rarely</i> overridden; in practice you are more
	 * likely to use a <code>Command</code> to create and register <code>Proxy</code>s
	 * with the <code>Model</code>, since <code>Proxy</code>s with mutable
	 * data will likely need to send <code>INotification</code>s and thus
	 * will likely want to fetch a reference to the <code>Facade</code> during
	 * their construction.
	 * </P>
	 */
	protected void initializeModel()
	{
		if( model != null )
			return;

		model = Model.getInstance();
	}

	/**
	 * Initialize the <code>View</code>.
	 * 
	 * <P>
	 * Called by the <code>initializeFacade</code> method. Override this
	 * method in your subclass of <code>Facade</code> if one or both of the
	 * following are true:
	 * <UL>
	 * <LI> You wish to initialize a different <code>IView</code>.</LI>
	 * <LI> You have <code>Observers</code> to register with the
	 * <code>View</code></LI>
	 * </UL>
	 * If you don't want to initialize a different <code>IView</code>, call
	 * <code>super.initializeView()</code> at the beginning of your method,
	 * then register <code>IMediator</code> instances.
	 * <P>
	 * Note: This method is <i>rarely</i> overridden; in practice you are more
	 * likely to use a <code>Command</code> to create and register
	 * <code>Mediator</code>s with the <code>View</code>, since
	 * <code>IMediator</code> instances will need to send
	 * <code>INotification</code>s and thus will likely want to fetch a
	 * reference to the <code>Facade</code> during their construction.
	 * </P>
	 */
	protected void initializeView()
	{
		if( view != null )
			return;

		view = View.getInstance();
	}

	/**
	 * Register an <code>ICommand</code> with the <code>Controller</code> by
	 * Notification name.
	 * 
	 * @param noteName
	 *            the name of the <code>INotification</code> to associate the
	 *            <code>ICommand</code> with
	 * @param command
	 *            an instance of the <code>ICommand</code>
	 */
	public void registerCommand( String noteName, ICommand command )
	{
		controller.registerCommand( noteName, command );
	}

	/**
	 * Remove a previously registered <code>ICommand</code> to <code>INotification</code> mapping from the Controller.
	 * 
	 * @param notificationName the name of the <code>INotification</code> to remove the <code>ICommand</code> mapping for
	 */
	public void removeCommand( String notificationName ) {
		this.controller.removeCommand( notificationName );
	}

	/**
	 * Check if a Command is registered for a given Notification 
	 * 
	 * @param notificationName
	 * @return whether a Command is currently registered for the given <code>notificationName</code>.
	 */
	public boolean hasCommand( String notificationName)
	{
		return controller.hasCommand(notificationName);
	}

	/**
	 * Register a <code>IMediator</code> with the <code>View</code>.
	 * 
	 * @param mediator
	 *            the name to associate with this <code>IMediator</code>
	 */
	public void registerMediator( IMediator mediator )
	{
		if (this.view != null) {
			this.view.registerMediator( mediator );
		}
	}

	/**
	 * Register an <code>IProxy</code> with the <code>Model</code> by name.
	 * 
	 * @param proxy
	 *            the name of the <code>IProxy</code> instance to be
	 *            registered with the <code>Model</code>.
	 */
	public void registerProxy( IProxy proxy )
	{
		this.model.registerProxy( proxy );
	}

	/**
	 * Remove an <code>IMediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 *            name of the <code>IMediator</code> to be removed.
	 * @return the <code>IMediator</code> that was removed from the <code>View</code>	             
	 */
	public IMediator removeMediator( String mediatorName )
	{
		if (this.view != null) {
			return this.view.removeMediator( mediatorName );
		}
		return null;
	}

	/**
	 * Remove an <code>IProxy</code> from the <code>Model</code> by name.
	 * 
	 * @param proxyName
	 *            the <code>IProxy</code> to remove from the
	 *            <code>Model</code>.
      * @return the <code>IProxy</code> that was removed from the <code>Model</code>	             
	 */
	public IProxy removeProxy( String proxyName )
	{
		if (this.model != null) {
			return this.model.removeProxy( proxyName );
		}
		return null;
	}
	
	/**
	 * Check if a Proxy is registered
	 * 
	 * @param proxyName
	 * @return whether a Proxy is currently registered with the given <code>proxyName</code>.
	 */
	public boolean hasProxy( String proxyName )
	{
		return model.hasProxy( proxyName );
	}
	
	
	/**
	 * Check if a Mediator is registered or not
	 * 
	 * @param mediatorName
	 * @return whether a Mediator is registered with the given <code>mediatorName</code>.
	 */
	public boolean hasMediator( String mediatorName )
	{
		return view.hasMediator( mediatorName );
	}

	/**
	 * Retrieve an <code>IMediator</code> from the <code>View</code>.
	 * 
	 * @param mediatorName
	 * @return the <code>IMediator</code> previously registered with the given
	 *         <code>mediatorName</code>.
	 */
	public IMediator retrieveMediator( String mediatorName )
	{
		return this.view.retrieveMediator( mediatorName );
	}

	/**
	 * Retrieve an <code>IProxy</code> from the <code>Model</code> by name.
	 * 
	 * @param proxyName
	 *            the name of the proxy to be retrieved.
	 * @return the <code>IProxy</code> instance previously registered with the
	 *         given <code>proxyName</code>.
	 */
	public IProxy retrieveProxy( String proxyName )
	{
		return this.model.retrieveProxy( proxyName );
	}

	/**
	 * Create and send an <code>INotification</code>.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification 
	 * instances in our implementation code.
	 * @param notificationName the name of the notification to send
	 * @param body the body of the notification (optional)
	 * @param type the type of the notification (optional)
	 */ 
	public void sendNotification( String notificationName, Object body, String type ) 
	{
		notifyObservers( new Notification( notificationName, body, type ) );
	}
	
	/**
	 * Create and send an <code>INotification</code>.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification 
	 * instances in our implementation code.
	 * @param notificationName the name of the notification to send
	 * @param body the body of the notification (optional)
	 */ 
	public void sendNotification( String notificationName, Object body ) 
	{
		sendNotification( notificationName, body, null );
	}
	
	/**
	 * Create and send an <code>INotification</code>.
	 * 
	 * <P>
	 * Keeps us from having to construct new notification 
	 * instances in our implementation code.
	 * @param notificationName the name of the notification to send
	 */ 
	public void sendNotification( String notificationName ) 
	{
		sendNotification( notificationName, null, null );
	}

	/**
	 * Notify <code>Observer</code>s of an <code>INotification</code>.
	 * 
	 * @param note
	 *            the <code>INotification</code> to have the <code>View</code>
	 *            notify observers of.
	 */
	public void notifyObservers( INotification note )
	{
		if( view != null)
			view.notifyObservers( note );
	}
}
