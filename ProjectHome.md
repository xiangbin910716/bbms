## What is BBMS ##
**BBMS**(Bus Based Message Service) is a Messaging System written by java. BBMS applications can send messages to and receive messages from any other clients asynchronous.
**BBMS** provides easy to use APIs and high performance and flexibility. It's like the _JMS_(Java Message Service) but not support **_pub/sub_** model by now. Only P2P support, but later may implements the priority message and **_pub/sub_** model.

---

## Features ##
### Easy to use APIs ###
```
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
```
### Where to use it ###
_Event & listener_ framework(or _command_ pattern) provides asynchronous ablity for enterprise application. Messages in BBMS are asynchronous requests, reports or events that are consumed by applications. **BBMS** application is one or more **BBMS** clients exchange messages. So, you can use it whenever you want you application support the asynchronous events functions.

Most **_GUI_** application need asynchronous event to support. Such as the UI need to be updated when you click a button or something the like. **_BBMS_** is lightweight Event & Listener framework for this, anyway, you can extend it to be a powerful message system just like the JMS(Java Message Service) if you like.


---

## Structure ##
![http://bbms.googlecode.com/files/_bbmsf_.png](http://bbms.googlecode.com/files/_bbmsf_.png)

---

## The framework ##
Generally speaking, each client can send messages to, and receive messages from any client, end point can set that what kind of message it cares about easily. Each connects to BBMS which provides facilities for creating, sending and receiving messages.

---

## More details ##
Please refer the wiki pages for more details.