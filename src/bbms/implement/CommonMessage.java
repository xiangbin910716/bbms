package bbms.implement;

import bbms.framework.AbstractMessage;

public class CommonMessage extends AbstractMessage{
	private static final long serialVersionUID = 285997306647067958L;

	public CommonMessage(String src, String dst, int type, Object body){
		super(src, dst, type, body);
	}
}
