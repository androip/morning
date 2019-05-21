package morning.bill;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FieldTransformRule implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7582300588271530941L;
	private List<String> srcFkey;
    private String desFkey; //目标字段只有一个
    private String formula;  //eg "srcfkey[1] + srcfeky[2]"; 
    private String ruleType;
}
