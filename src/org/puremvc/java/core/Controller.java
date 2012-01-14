/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.core;

import java.util.HashMap;
import java.util.Map;

import org.puremvc.java.interfaces.ICommand;
import org.puremvc.java.interfaces.IController;
import org.puremvc.java.interfaces.IFunction;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.patterns.observer.Observer;

/**
 * A Singleton <code>IController</code> implementation.
 * 
 * <P>
 * In PureMVC, the <code>Controller</code> class follows the
 * 'Command and Controller' strategy, and assumes these 
 * responsibilities:
 * <UL>
 * <LI> Remembering which <code>ICommand</code>s 
 * are intended to handle which <code>INotifications</code>.</LI>
 * <LI> Registering itself as an <code>IObserver</code> with
 * the <code>View</code> for each <code>INotification</code> 
 * that it has an <code>ICommand</code> mapping for.</LI>
 * <LI> Creating a new instance of the proper <code>ICommand</code>
 * to handle a given <code>INotification</code> when notified by the <code>View</code>.</LI>
 * <LI> Calling the <code>ICommand</code>'s <code>execute</code>
 * method, passing in the <code>INotification</code>.</LI> 
 * </UL>
 * 
 * <P>
 * Your application must register <code>ICommands</code> with the 
 * Controller.
 * <P>
	 * The simplest way is to subclass </code>Facade</code>, 
 * and use its <code>initializeController</code> method to add your 
 * registrations. 
 * 
 * @see org.puremvc.java.core.View View
 * @see org.puremvc.java.patterns.observer.Observer Observer
 * @see org.puremvc.java.patterns.observer.Notification Notification
 * @see org.puremvc.java.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.patterns.command.MacroCommand MacroCommand
 */
public class Controller implements IController
{

	/** 
	 * Reference to the singleton instance
	 */
	protected static Controller instance;
	/**
	 * Mapping of Notification names to Command Class references
	 */
	protected Map<String,ICommand> commandMap;

	/**
	 * Local reference to View
	 */
	protected View view;

	/**
	 * Constructor.
	 * 
	 * <P>
	 * This <code>IController</code> implementation is a Singleton, so you
	 * should not call the constructor directly, but instead call the static
	 * Singleton Factory method <code>Controller.getInstance()</code>
	 * 
	 */
	protected Controller()
	{
		instance = this;
		commandMap = new HashMap<String,ICommand>();
		initializeController();
	}

	/**
	 * Initialize the Singleton <code>Controller</code> instance.
	 * 
	 * <P>Called automatically by the constructor.</P> 
	 * 
	 * <P>Note that if you are using a subclass of <code>View</code>
	 * in your application, you should <i>also</i> subclass <code>Controller</code>
	 * and override the <code>initializeController</code> method in the 
	 * following way:</P>
	 * 
	 * <listing>
	 *		// ensure that the Controller is talking to my IView implementation
	 *		override public function initializeController(  ) : void 
	 *		{
	 *			view = MyView.getInstance();
	 *		}
	 * </listing>
	 */
	protected void initializeController( )
	{
		view = View.getInstance();
	}

	/**
	 * <code>Controller</code> Singleton Factory method.
	 * 
	 * @return the Singleton instance of <code>Controller</code>
	 */
	public synchronized static Controller getInstance()
	{
		if (instance == null)
			instance = new Controller();

		return instance;
	}

	/**
	 * If an <code>ICommand</code> has previously been registered to handle a
	 * the given <code>INotification</code>, then it is executed.
	 * 
	 * @param note
	 * 		The notification to send associated with the command to call.
	 */
	public void executeCommand( INotification note )
	{
		//No reflexion in GWT
		//ICommand commandInstance = (ICommand) commandClassRef.newInstance();
		ICommand commandInstance = (ICommand) this.commandMap.get( note.getName() );
		if(commandInstance!=null){
			commandInstance.execute( note );
		}
	}

	/**
	 * Register a particular <code>ICommand</code> class as the handler for a
	 * particular <code>INotification</code>.
	 * 
	 * <P>
	 * If an <code>ICommand</code> has already been registered to handle
	 * <code>INotification</code>s with this name, it is no longer used, the
	 * new <code>ICommand</code> is used instead.
	 * </P>
	 * 
	 * The Observer for the new ICommand is only created if this the 
	 * first time an ICommand has been regisered for this Notification name.
	 * 
	 * @param notificationName
	 *            the name of the <code>INotification</code>
	 * @param command
	 *            an instance of <code>ICommand</code>
	 */
	public void registerCommand( String notificationName, ICommand command )
	{
		if( null != this.commandMap.put( notificationName, command ) )
			return;
			
		view.registerObserver
		(
			notificationName,
			new Observer
			(
				new IFunction()
				{
					public void onNotification( INotification notification )
					{
						executeCommand( notification );
					}
				},
				this
			)
		);
	}

	/**
	 * Remove a previously registered <code>ICommand</code> to
	 * <code>INotification</code> mapping.
	 * 
	 * @param notificationName
	 *            the name of the <code>INotification</code> to remove the
	 *            <code>ICommand</code> mapping for
	 */
	public void removeCommand( String notificationName )
	{
		// if the Command is registered...
		if ( hasCommand( notificationName ) )
		{
			// remove the observer
			view.removeObserver( notificationName, this );
			commandMap.remove( notificationName );
		}
	}
	/**
	 * Check if a Command is registered for a given Notification 
	 * 
	 * @param notificationName
	 *		The name of the command to check for existance.
	 *
	 * @return whether a Command is currently registered for the given <code>notificationName</code>.
	 */
	public boolean hasCommand(String notificationName )
	{
		return commandMap.containsKey(notificationName);
	}
}
