/*
 PureMVC Java port by Frederic Saunier <frederic.saunier@puremvc.org>
 
 Adapted from sources of thoses different authors :
 	Donald Stinchfield <donald.stinchfield@puremvc.org>, et all
 	Ima OpenSource <opensource@ima.eu>
 	Anthony Quinault <anthony.quinault@puremvc.org>
 
 PureMVC - Copyright(c) 2006-10 Futurescale, Inc., Some rights reserved. 
 Your reuse is governed by the Creative Commons Attribution 3.0 License
*/
package org.puremvc.java.patterns.mediator;

import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.INotifier;
import org.puremvc.java.patterns.observer.Notifier;

/**
 * A base <code>IMediator</code> implementation.
 * 
 * @see org.puremvc.java.core.View View
 */
public class Mediator extends Notifier implements IMediator, INotifier
{

	/**
	 * The default name of the <code>Mediator</code>.
	 */
	public static final String NAME = "Mediator";
	
	/** 
	 * The name of the <code>Mediator</code>.
	 */
	protected String mediatorName = null;

	/**
	 * The view component
	 */
	protected Object viewComponent = null;

	/**
	 * Constructor.
	 * @param mediatorName
	 * @param viewComponent 
	 */
	public Mediator( String mediatorName, Object viewComponent )
	{
		this.mediatorName = (mediatorName != null)?mediatorName:NAME; 
		this.viewComponent = viewComponent;	
	}

	/**
	 * Get the name of the <code>Mediator</code>.
	 * 
	 * @return the name
	 */
	public final String getMediatorName( )
	{
		return mediatorName;
	}

	/**
	 * Set the <code>IMediator</code>'s view component.
	 * 
	 * @param viewComponent
	 * 		The view component
	 */
	public void setViewComponent( Object viewComponent ) 
	{
		this.viewComponent = viewComponent;
	}

	/**
	 * Get the <code>Mediator</code>'s view component.
	 * 
	 * <P>
	 * Additionally, an implicit getter will usually be defined in the subclass
	 * that casts the view object to a type, like this:
	 * </P>
	 * 
	 * <listing> private function get comboBox : mx.controls.ComboBox { return
	 * viewComponent as mx.controls.ComboBox; } </listing>
	 * @return the view component
	 */
	public Object getViewComponent()
	{
		return viewComponent;
	}

	/**
	 * Handle <code>INotification</code>s.
	 * 
	 * <P>
	 * Typically this will be handled in a switch statement, with one 'case'
	 * entry per <code>INotification</code> the <code>Mediator</code> is
	 * interested in.
	 * @param notification 
	 */
	public void handleNotification( INotification notification ){}

	/**
	 * List the <code>INotification</code> names this <code>Mediator</code>
	 * is interested in being notified of.
	 * 
	 * @return String[] the list of <code>INotification</code> names
	 */
	public String[] listNotificationInterests( )
	{
		return new String[]{};
	}

	/**
	 * Called by the View when the Mediator is registered
	 */ 
	public void onRegister(){}
	
	/**
	 * Called by the View when the Mediator is removed
	 */ 
	public void onRemove(){}
}
