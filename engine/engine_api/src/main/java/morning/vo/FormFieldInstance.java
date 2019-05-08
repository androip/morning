package morning.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormFieldInstance implements Serializable {

	private String formInstanceId;
	private String fkey;
	private String fval;
	private String ftype;
	private String rkey;			//当ftype为ref的时候，关联的条件字段
	private String tableName;	//当ftype为ref的时候，关联的表
	
}
