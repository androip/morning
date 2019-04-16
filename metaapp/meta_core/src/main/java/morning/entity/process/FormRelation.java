package morning.entity.process;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import morning.vo.RelationType;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "form_relation")
public class FormRelation {

	private String rid;
	private String processTid;//所属流程模版
	private RelationType type;//单据转换类型
	private String srcFromTId;
	private String desFromTId;
	private List<String> srcFkeys;
	private List<String> desFkeys;
	private String formula;
	
}
