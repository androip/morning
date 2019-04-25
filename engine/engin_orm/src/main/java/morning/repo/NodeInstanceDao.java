package morning.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import morning.entity.process.NodeInstance;

@Component
public class NodeInstanceDao {

	private static Logger logger = LoggerFactory.getLogger(NodeInstanceDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void save(NodeInstance nodeIns) {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO NodeInstance");
		INSTER_SQL.append("(nodeInsId,nodeTemplateId,processInsId,nodeType,nodeStatus)");
		INSTER_SQL.append("VALUE(");
		INSTER_SQL.append("'").append(nodeIns.getNodeInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(nodeIns.getNodeTemplateId()).append("'").append(",");
		INSTER_SQL.append("'").append(nodeIns.getProcessInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(nodeIns.getNodeType()).append("'").append(",");
		INSTER_SQL.append("'").append(nodeIns.getNodeStatus()).append("'");
		INSTER_SQL.append(")");
		String sql = INSTER_SQL.toString();
		logger.debug("SQL:"+sql);
		jdbcTemplate.execute(sql);
		
	}
	
	
}
