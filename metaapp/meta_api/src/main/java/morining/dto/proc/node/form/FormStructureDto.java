package morining.dto.proc.node.form;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FormStructureDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3174943092851094667L;
	private String parentId;
	private String selfId;

}
