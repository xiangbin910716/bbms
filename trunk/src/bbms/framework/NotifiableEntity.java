package bbms.framework;

import java.rmi.RemoteException;

/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public interface NotifiableEntity extends java.rmi.Remote{
	/**
	 * get the id of entry, unique
	 * @return
	 */
	public String getId() throws RemoteException;
	
	/**
	 * register entry to bus, wait for event
	 * @throws RemoteException 
	 */
	public void register() throws RemoteException;
	
	/**
	 * unregister the entry from bus.
	 * @throws RemoteException
	 */
	public void unregister() throws RemoteException;
	/**
	 * update the entry, when event comes
	 * @param event
	 */
	public void update(Message event) throws java.rmi.RemoteException;
	
	/**
	 * post an event, the target field special which the message will
	 * be deliver to.
	 * @param message
	 * @throws RemoteException 
	 */
	public void post(Message message) throws RemoteException;
	
	/**
	 * get all interested topics of the entry, when the notification comes
	 * if it does not match the sense mask, the message will NOT be delivered.
	 * @return
	 * @throws RemoteException
	 */
	public int getSense() throws RemoteException;
}
