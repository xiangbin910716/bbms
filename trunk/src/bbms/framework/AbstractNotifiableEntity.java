package bbms.framework;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import bbms.config.Configuration;



/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public abstract class AbstractNotifiableEntity 
extends UnicastRemoteObject 
implements NotifiableEntity {
	protected String id;
	protected int sense;
	
	private String host;
	private int port;
	
	protected AbstractNotifiableEntity(Configuration config) throws RemoteException{
		this.host = config.getHost();
		this.port = config.getPort();
	}

	private static final long serialVersionUID = 5156184321936391027L;

	public String getId() throws RemoteException{
		return this.id;
	}

	/**
	 * post a message into the <code>Message BUS.</code>
	 */
	public void post(Message message) throws RemoteException{
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);
			Bus bus = (Bus) reg.lookup(Configuration.MESSAGE_BUS_INSTANCE);
			bus.post(message);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * register self to <code>Message BUS</code> to listen event comes.
	 */
	public void register() throws RemoteException {
		try {
			Registry reg = LocateRegistry.getRegistry(host, port);
			Bus bus = (Bus) reg.lookup(Configuration.MESSAGE_BUS_INSTANCE);
			bus.mount(this);
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * unregister self when do not want to listen event any more, or while
	 * exit the application.
	 */
	public void unregister() throws RemoteException{
		try{
			Registry reg = LocateRegistry.getRegistry(host, port);
			Bus bus = (Bus) reg.lookup(Configuration.MESSAGE_BUS_INSTANCE);
			bus.unmount(this);
		}catch(NotBoundException e){
			e.printStackTrace();
		}
	}
	
	abstract public void update(Message event) throws RemoteException;
	
	public int getSense() throws RemoteException{
		return this.sense;
	}

}
