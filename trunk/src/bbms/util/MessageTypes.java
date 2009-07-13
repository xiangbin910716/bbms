package bbms.util;

public class MessageTypes {	
	/* target */
	public static String SENDTOALL = "SendToAll";
	
	/*
	 * 0x8000 = 1000 0000 0000 0000
	 * 0x4000 = 0100 0000 0000 0000
	 * 0x2000 = 0010 0000 0000 0000
	 * 0x1000 = 0001 0000 0000 0000
	 * 
	 * it's very useful when you want to combine some messages
	 * together, and the user can simply determine what exactly
	 * what you want. Refer the implementation of MessageBus.java
	 * for more details.
	 */
	public static final int MESSAGE_TIMEOUT = 0x8000;
	public static final int MESSAGE_CLICKED = 0x4000;
	public static final int MESSAGE_CLOSE = 0x2000;
	public static final int MESSAGE_OPEN = 0x1000;
	
	public static final int MESSAGE_READY = 0x0800;
	public static final int MESSAGE_BUSY = 0x0400;
	public static final int MESSAGE_WAIT = 0x0200;
	public static final int MESSAGE_OKAY = 0x0100;
}
