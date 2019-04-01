package morining.dto.proc.edge;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EdgeDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4692319379365699700L;
	private String id;
	private String from;
	private String to;
	
}
