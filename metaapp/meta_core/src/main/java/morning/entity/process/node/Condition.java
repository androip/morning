package morning.entity.process.node;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Condition implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3472770460898934662L;

	private String filedKey;				//要判断的字段，暂时只支持判断单个字段
	private Map<Object,List<String>> targets; //key 期望的条件值，val 目标出度
}
