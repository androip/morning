package morning.bill;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "transform_rule")
public class FormTransformRule implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2959770749892665279L;
	@Id
    private String transformId;
    @Indexed
    private String processTid;
	private String srcFormTid;
    private String desFormTId;
    private List<FieldTransformRule> fieldRules;
}
