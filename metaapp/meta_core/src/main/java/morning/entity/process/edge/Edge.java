package morning.entity.process.edge;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Edge implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4367153742859310295L;
	
	private String from;
	private String to;

}
