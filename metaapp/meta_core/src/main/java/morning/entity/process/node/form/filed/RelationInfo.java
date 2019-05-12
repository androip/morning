package morning.entity.process.node.form.filed;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RelationInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416108280996457303L;
	
	/**
	 * Select viewFkey
	 * from relationSource
	 * where conditions;
	 */
	private String relationSource;
	private List<String> viewFkey;
	private Map<String,Object> conditions;   //key 条件字段 ,val 条件值 
	private String Pkey; //relationSource的主键字段

}
