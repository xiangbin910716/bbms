package bbms.framework;

/**
 * This is the core definition of the <code>BBMS</code>, while user can
 * mount thire object on bus to listener events, or post messages to 
 * other objects mounted on the <code>BUS</code>
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public interface Bus extends java.rmi.Remote{
	/**
	 * mount an notifiable entry on bus
	 * @param entry
	 */
	public void mount(NotifiableEntity entry) throws java.rmi.RemoteException;
	
	/**
	 * unmount the notifiable entry on bus
	 * @param entry
	 */
	public void unmount(NotifiableEntity entry) throws java.rmi.RemoteException;
	
	/**
	 * post a new message to Message Bus
	 * @param message
	 */
	public void post(Message message) throws java.rmi.RemoteException;
}
