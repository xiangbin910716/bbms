package bbms.config;

/**
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public interface Configuration {
	
	public String MESSAGE_BUS_INSTANCE = "MessageBusInstance";
	
	/**
	 * get host ip from configuration
	 * @return
	 */
	public String getHost();
	
	/**
	 * get host port from configuration
	 * @return the BUS Service port
	 */
	public int getPort();
}
