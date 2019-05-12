package morning.bill;


import java.io.Serializable;

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
	private String srcFkey;
    private String desFkey;
    private String formula;
    private String ruleType;
}
