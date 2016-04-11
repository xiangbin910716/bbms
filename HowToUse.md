# Introduction #

BBMS is very easy to use, and provide powerful **_event-listener_** framework. Generally, there need a application server to run the Message Serivce(**BUS**), and other application can running on other node of the network.

## Extends the abstract **_Notifiable Entry_** ##

Only one thing is must when you implement a notifiable entry on you own, it's the method _update_:

```
	/**
	 * This is the only one MUST implemented method for end user, put the
	 * event-handler code here
	 */
	public void update(Message event) throws RemoteException {
		System.err.println(
				"This is "+this.id+
				", get message from : "+event.getSource()+
				", it said that : "+event.getBody());
	}
```
When event from bus, this _update_ method will be invoked, you can do whatever you want here.

## New a **_Configuration_** ##

```
Configuration config = new RMIServerConfiguration(null, 0);
```
The _RMIServerConfiguration_ reads a properties to know where the Message Service is.

## New a concrete Notifiable Entry ##
```
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
```

## Create **_Message_** ##

```
		// new a message, of type MESSAGE_OPEN.
		Message msg = new CommonMessage(
				entry1.getId(),
				entry2.getId(),
				MessageTypes.MESSAGE_OPEN,
				"busying now");
```

new a message which will be sent from entry1 to entry2, and with type **_MESSAGE\_OPEN_**.

## Put them together ##

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

One thing need to note is that when you finished message deliver, you'd butter **_unregister_** it from the Remote Bus.