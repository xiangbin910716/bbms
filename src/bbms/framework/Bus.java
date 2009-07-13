package bbms.framework;

public interface Bus extends java.rmi.Remote{
	/**
	 * mount an notifiable entry on bus
	 * @param entry
	 */
	public void mount(NotifiableEntry entry) throws java.rmi.RemoteException;
	
	/**
	 * unmount the notifiable entry on bus
	 * @param entry
	 */
	public void unmount(NotifiableEntry entry) throws java.rmi.RemoteException;
	
	/**
	 * post a new message to Message Bus
	 * @param message
	 */
	public void post(Message message) throws java.rmi.RemoteException;
}
