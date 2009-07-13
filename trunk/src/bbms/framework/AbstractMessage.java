package bbms.framework;

import java.io.Serializable;

/**
 * The message is used to deliver over net, so it must implements
 * the <code>java.io.Serializable</code> interface.
 * 
 * @author juntao.qiu@gmail.com
 *
 */
public abstract class AbstractMessage implements Message, Serializable{

	private static final long serialVersionUID = -5907585667335451881L;
	protected String target;
	protected String source;
	protected int type;
	protected Object body;

	protected AbstractMessage(String src, String dst, int type, Object body){
		this.source = src;
		this.target = dst;
		this.type = type;
		this.body = body;
	}
	
	public Object getBody() {
		return this.body;
	}
	
	public void setBody(Object body) {
		this.body = body;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public int getType() {
		return this.type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
