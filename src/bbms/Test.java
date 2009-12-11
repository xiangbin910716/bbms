package bbms;
import java.rmi.RemoteException;

import bbms.config.Configuration;
import bbms.config.RMIServerConfiguration;
import bbms.framework.Message;
import bbms.implement.CommonMessage;
import bbms.implement.CommonNotifiableEntry;
import bbms.util.MessageTypes;

/**
 * 
 * @author juntao.qiu@china.jinfonet.com
 *
 */
public class Test{
	public static void main(String[] args) throws RemoteException{
		/*
		 * create a notifiable entry, declare that it's care of
		 * TIMEOUT, CLOSE, and READY event.
		 */
		Configuration config = new RMIServerConfiguration(null, 0);
		CommonNotifiableEntry entry1 = 
			new CommonNotifiableEntry(config, "client1", 
				MessageTypes.MESSAGE_TIMEOUT | 
				MessageTypes.MESSAGE_CLOSE | 
				MessageTypes.MESSAGE_READY);
		
		/*
		 * create another notifiable entry, declare that it's care of
		 * OPEN, CLOSE, and TIMEOUT event.
		 */
		CommonNotifiableEntry entry2 = 
			new CommonNotifiableEntry(config, "client2", 
				MessageTypes.MESSAGE_OPEN | 
				MessageTypes.MESSAGE_CLOSE | 
				MessageTypes.MESSAGE_TIMEOUT);
		
		// register them to the remote Message BUS to listener events
		entry1.register();
		entry2.register();
		
		// new a message, of type MESSAGE_OPEN.
		Message msg = new CommonMessage(
				entry1.getId(),
				entry2.getId(),
				MessageTypes.MESSAGE_OPEN,
				"busying now");
		
		// deliver it to entry2, which is from entry1
		entry1.post(msg);
		
		// create a message, of type MESSAGE_CLICKED, the entry2
		// does not handle this type, it'll not be deliver to entry2
		Message msgCannotBeReceived = new CommonMessage(
				entry1.getId(),
				entry2.getId(),
				MessageTypes.MESSAGE_CLICKED,
				"cliked evnet");
		entry1.post(msgCannotBeReceived);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// re use the message object to send another message entry
		msg.setSource(entry2.getId());
		msg.setTarget(entry1.getId());
		msg.setType(MessageTypes.MESSAGE_READY);
		msg.setBody("okay now");
		entry2.post(msg);
		
		// unregister self when all works are done or 
		// don't want to listen any more
		entry1.unregister();
		entry2.unregister();
	}
}
