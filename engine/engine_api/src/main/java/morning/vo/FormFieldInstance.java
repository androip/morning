package morning.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormFieldInstance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873674935872584529L;
	private String formInstanceId;
	private String fkey;
	private Object fval;
	private String ftype;
	private String rkey;			//当ftype为ref的时候，关联的条件字段
	private String tableName;	//当ftype为ref的时候，关联的表
	
	
}
