package bbms.framework;

/**
 * Message is a java-bean like object which can be deliver over
 * remote connection, therefore it could be used on RMI application framework
 * 
 * Event on each end point is wrapped as a message, and it can be
 * delivered to other end point of the distributed application.
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public interface Message{
	public int getType();
	public void setType(int type);
	
	public String getTarget();
	public void setTarget(String target);
	
	public String getSource();
	public void setSource(String source);
	
	public Object getBody();
	public void setBody(Object body);
}
