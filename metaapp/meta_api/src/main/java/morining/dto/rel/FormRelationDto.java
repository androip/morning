package morining.dto.rel;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormRelationDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5470102560156215055L;
	private String rid;
	private String srcFromTId;
	private String desFromTId;
	private String type;//单据转换类型
	private List<String> srcFkeys;
	private List<String> desFkeys;
	private String formula;
}
