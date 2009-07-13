package bbms.implement;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import bbms.config.Configuration;
import bbms.framework.Bus;

/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public class BusManager {
	/**
	 * initialize the Message BUS, then the service is started
	 * and client can deliver messages via the BUS.
	 * @throws RemoteException
	 */
	public void initMessageBus() throws RemoteException{
		Bus bus = MessageBus.getInstance();
		Registry reg = LocateRegistry.createRegistry(9527);
		reg.rebind(Configuration.MESSAGE_BUS_INSTANCE, bus);
	}
	
	public static void main(String[] args) throws RemoteException{
		new BusManager().initMessageBus();
		System.err.println("Message BUS is working now ...");
	}
}
