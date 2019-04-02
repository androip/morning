package morining.dto.proc.node;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConditionDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7259830568233018786L;
	
	private String filedKey;				//要判断的字段，暂时只支持判断单个字段
	private Map<String,List<String>> targets; //key 条件值，val 目标出度

}
