package bbms.implement;

import java.rmi.RemoteException;

import bbms.config.Configuration;
import bbms.framework.AbstractNotifiableEntry;
import bbms.framework.Message;


/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public class CommonNotifiableEntry extends AbstractNotifiableEntry{
	private static final long serialVersionUID = 6805784722150128069L;
	
	/**
	 * first use the <code>config</code> to initialize the <br>
	 * <code>AbstractNotifiableEntry</code>, in order to register 
	 * <code>this</code> to the Message BUS. 
	 * @param config
	 * @param id
	 * @param sense
	 * @throws RemoteException
	 */
	public CommonNotifiableEntry(Configuration config, String id, int sense) 
	throws RemoteException {
		super(config);
		this.id = id;
		this.sense = sense;
	}

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

}
