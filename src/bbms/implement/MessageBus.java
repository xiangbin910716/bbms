package bbms.implement;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import bbms.framework.Bus;
import bbms.framework.Message;
import bbms.framework.NotifiableEntity;
import bbms.util.MessageTypes;



/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public class MessageBus extends UnicastRemoteObject implements Bus{
	private static MessageBus instance;
	private List<NotifiableEntity> listeners;
	private List<Message> messages;
	private Thread daemonThread = null;
	
	public static MessageBus getInstance() throws RemoteException{
		if(instance == null){
			instance = new MessageBus();
		}
		return instance;
	}
	
	private MessageBus() throws RemoteException{
		listeners = new LinkedList<NotifiableEntity>();
		messages = new LinkedList<Message>();
		Daemon daemon = new Daemon();
		daemonThread = new Thread(daemon);
        daemonThread.setPriority(Thread.NORM_PRIORITY + 3);
        daemonThread.setDaemon(true);
        daemonThread.start();
        
        while(!daemonThread.isAlive());
	}
	
	/**
	 * mount notifiable object to listener list
	 */
	public void mount(NotifiableEntity entry) throws RemoteException{
		synchronized(listeners){
			listeners.add(entry);
			listeners.notifyAll();
		}
	}

	/**
	 * unmount the special notifiable object from listener
	 */
	public void unmount(NotifiableEntity entry) throws RemoteException{
		synchronized(listeners){
			listeners.remove(entry);
			listeners.notifyAll();
		}
	}
	
	/**
	 * post a new message into the bus
	 * @param message
	 */
	public void post(Message message) throws RemoteException{
		synchronized(messages){
			messages.add(message);
			messages.notifyAll();
		}
	}
	
	/**
	 * worker thread, dispatch message to appropriate listener
	 * 
	 * @author juntao.qiu@gmail.com
	 */
	private class Daemon implements Runnable{
		private boolean loop = true;
		public void run(){
			while(loop){
				if(messages.size() == 0){
					synchronized(messages){
						try {messages.wait();} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				processIncomingMessage();
			}
		}
	}
	
	/**
	 * process the incoming message, remove the first message from
	 * queue, and then check all listeners to see whether should 
	 * deliver the message to or not.
	 */
	private void processIncomingMessage(){
		Message msg;
		synchronized(messages){
			msg = messages.remove(0);
		}
		String target = null;
		int type = 0;
		int mask = 0;
		try {
			target = msg.getTarget();
			type = msg.getType();
			if(target == MessageTypes.SENDTOALL){
				for(NotifiableEntity entry : listeners){
					mask = entry.getSense();
					if((mask & type) == type){entry.update(msg);}
				}
			}else{
				for(NotifiableEntity entry : listeners){
					mask = entry.getSense();
					if(entry.getId().equals(target) && (mask & type) == type){
						entry.update(msg);
					}
				}
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
