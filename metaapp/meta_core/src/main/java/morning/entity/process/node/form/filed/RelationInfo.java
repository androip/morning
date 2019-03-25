package morning.entity.process.node.form.filed;

import java.io.Serializable;

public class RelationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416108280996457303L;
	
	/**
	 * Select viewFkey
	 * from relationSource
	 * where conditionFKey and foreignFkey;
	 */
	private String relationSource;
	private String viewFkey;
	private String foreignFkey;
	private String conditionFKey;

}
