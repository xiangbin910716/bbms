package bbms.config;

import java.util.Properties;

import bbms.util.PropLoader;


/**
 * A very simple RMI-Server configuration implementation, load
 * configure from properties file on <code>BUS</code> server.
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public class RMIServerConfiguration implements Configuration{
	private static Properties prop;
	private String host;
	private int port;
	
	static{
		prop = PropLoader.getProperties("rmiserverconfig.properties");
	}
	
	/**
	 * if the given configuration is null, then use the default configuration
	 * read the properties file in current package.
	 * @param host
	 * @param port
	 */
	public RMIServerConfiguration(String host, int port){
		if(host == null){
			this.host = 
				prop.getProperty("rmi.server", "127.0.0.1");
		}else{
			this.host = host;
		}
		if(port == 0){
			this.port = 
				Integer.parseInt(prop.getProperty("rmi.port", "9527"));
		}else{
			this.port = port;
		}
	}
	
	/**
	 * get host ip address (means the address of Message BUS)
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * get the Message BUS service port
	 */
	public int getPort() {
		return this.port;
	}
}
