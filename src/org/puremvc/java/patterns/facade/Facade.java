/*
   PureMVC Java Port by Donald Stinchfield <donald.stinchfield@puremvc.org>, et al.
   PureMVC - Copyright(c) 2006-08 Futurescale, Inc., Some rights reserved.
   Your reuse is governed by the Creative Commons Attribution 3.0 License
*/

package org.puremvc.java.patterns.facade;

import org.puremvc.java.core.controller.Controller;
import org.puremvc.java.core.model.Model;
import org.puremvc.java.core.view.View;
import org.puremvc.java.interfaces.IFacade;
import org.puremvc.java.interfaces.IMediator;
import org.puremvc.java.interfaces.INotification;
import org.puremvc.java.interfaces.IProxy;
import org.puremvc.java.patterns.observer.Notification;

;

/**
 * A base Singleton <code>IFacade</code> implementation.
 * 
 * <P>
 * In PureMVC, the <code>Facade</code> class assumes these responsibilities:
 * <UL>
 * <LI>Initializing the <code>Model</code>, <code>View</code> and
 * <code>Controller</code> Singletons.</LI>
 * <LI>Providing all the methods defined by the <code>IModel, 
 * IView, & IController</code>
 * interfaces.</LI>
 * <LI>Providing the ability to override the specific <code>Model</code>,
 * <code>View</code> and <code>Controller</code> Singletons created.</LI>
 * <LI>Providing a single point of contact to the application for registering
 * <code>Commands</code> and notifying <code>Observers</code></LI>
 * </UL>
 * <P>
 * Example usage: <listing> import org.puremvc.patterns.facade.&lowast;;
 * 
 * import com.me.myapp.model.~~; import com.me.myapp.view.~~; import
 * com.me.myapp.controller.~~;
 * 
 * public class MyFacade extends Facade { // Notification constants. The Facade
 * is the ideal // location for these constants, since any part // of the
 * application participating in PureMVC // Observer Notification will know the
 * Facade. public static const GO_COMMAND:String = "go"; // Override Singleton
 * Factory method public static function getInstance() : MyFacade { if (instance ==
 * null) instance = new MyFacade(); return instance as MyFacade; } // optional
 * initialization hook for Facade override public function initializeFacade() :
 * void { super.initializeFacade(); // do any special subclass initialization
 * here } // optional initialization hook for Controller override public
 * function initializeController() : void { // call super to use the PureMVC
 * Controller Singleton. super.initializeController(); // Otherwise, if you're
 * implmenting your own // IController, then instead do: // if ( controller !=
 * null ) return; // controller = MyAppController.getInstance(); // do any
 * special subclass initialization here // such as registering Commands
 * registerCommand( GO_COMMAND, com.me.myapp.controller.GoCommand ) } //
 * optional initialization hook for Model override public function
 * initializeModel() : void { // call super to use the PureMVC Model Singleton.
 * super.initializeModel(); // Otherwise, if you're implmenting your own //
 * IModel, then instead do: // if ( model != null ) return; // model =
 * MyAppModel.getInstance(); // do any special subclass initialization here //
 * such as creating and registering Model proxys // that don't require a facade
 * reference at // construction time, such as fixed type lists // that never
 * need to send Notifications. regsiterProxy( new USStateNamesProxy() ); //
 * CAREFUL: Can't reference Facade instance in constructor // of new Proxys from
 * here, since this step is part of // Facade construction! Usually, Proxys
 * needing to send // notifications are registered elsewhere in the app // for
 * this reason. } // optional initialization hook for View override public
 * function initializeView() : void { // call super to use the PureMVC View
 * Singleton. super.initializeView(); // Otherwise, if you're implmenting your
 * own // IView, then instead do: // if ( view != null ) return; // view =
 * MyAppView.getInstance(); // do any special subclass initialization here //
 * such as creating and registering Mediators // that do not need a Facade
 * reference at construction // time. registerMediator( new LoginMediator() ); //
 * CAREFUL: Can't reference Facade instance in constructor // of new Mediators
 * from here, since this is a step // in Facade construction! Usually, all
 * Mediators need // receive notifications, and are registered elsewhere in //
 * the app for this reason. } } </listing>
 * 
 * @see org.puremvc.java.core.model.Model Model
 * @see org.puremvc.java.core.view.View View
 * @see org.puremvc.java.core.controller.Controller Controller
 * @see org.puremvc.java.patterns.observer.Notification Notification
 * @see org.puremvc.java.patterns.mediator.Mediator Mediator
 * @see org.puremvc.java.patterns.proxy.Proxy Proxy
 * @see org.puremvc.java.patterns.command.SimpleCommand SimpleCommand
 * @see org.puremvc.java.patterns.command.MacroCommand MacroCommand
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
	 * @throws Error
	 *             Error if Singleton instance has already been constructed
	 * 
	 */
	protected Facade( )
	{
		if (instance != null) throw new RuntimeException("Facade already constructed");
		initializeFacade();
	}

	/**
	 * Initialize the Singleton <code>Facade</code> instance.
	 * 
	 * <P>
	 * Called automatically by the constructor. Override in your subclass to do
	 * any subclass specific initializations. Be sure to call
	 * <code>super.initializeFacade()</code>, though.
	 * </P>
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
	 * @return the Singleton instance of the Facade
	 */
	public synchronized static Facade getInstance( )
	{
		if (instance == null) {
			try {
				instance = new Facade();
			} catch (Exception e) {
			}
		}
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
	protected void initializeController( )
	{
		if (this.controller != null) {
			return;
		}
		this.controller = Controller.getInstance();
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
	protected void initializeModel( )
	{
		if (this.model != null) {
			return;
		}
		this.model = Model.getInstance();
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
	protected void initializeView( )
	{
		if (this.view != null) {
			return;
		}
		this.view = View.getInstance();
	}

	/**
	 * Register an <code>ICommand</code> with the <code>Controller</code> by
	 * Notification name.
	 * 
	 * @param noteName
	 *            the name of the <code>INotification</code> to associate the
	 *            <code>ICommand</code> with
	 * @param commandClassRef
	 *            a reference to the Class of the <code>ICommand</code>
	 */
	public void registerCommand( String noteName, Class commandClassRef )
	{
		this.controller.registerCommand( noteName, commandClassRef );
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
	 * @param notificationName the name of the notiification to send
	 * @param body the body of the notification (optional)
	 * @param type the type of the notification (optional)
	 */ 
	public void sendNotification( String notificationName, Object body, String type ) 
	{
		notifyObservers( new Notification( notificationName, body, type ) );
	}

	/**
	 * Notify <code>Observer</code>s.
	 * 
	 * @param notification
	 *            the <code>INotification</code> to have the <code>View</code>
	 *            notify <code>Observers</code> of.
	 */
	public void notifyObservers( INotification notification )
	{
		if (this.view != null) {
			this.view.notifyObservers( notification );
		}
	}


}
