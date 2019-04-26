package morning.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import morning.entity.process.EdgeIns;

@Component
public class EdgeInstanceDao {

	private static Logger logger = LoggerFactory.getLogger(EdgeInstanceDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public void save(List<EdgeIns> edgeList) {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO EdgeInstance");
		INSTER_SQL.append("(processInsId,processTId,fromNodeInsId,fromNodeTId,toNodeInsId,toNodeTId)");
		INSTER_SQL.append("VALUES");
		for(EdgeIns ins:edgeList) {
			INSTER_SQL.append("(");
			INSTER_SQL.append("'").append(ins.getProcessInsId()).append("'").append(",");
			INSTER_SQL.append("'").append(ins.getProcessTId()).append("'").append(",");
			INSTER_SQL.append("'").append(ins.getFromNodeInsId()).append("'").append(",");
			INSTER_SQL.append("'").append(ins.getFromNodeTId()).append("'").append(",");
			INSTER_SQL.append("'").append(ins.getToNodeInsId()).append("'").append(",");
			INSTER_SQL.append("'").append(ins.getToNodeTId()).append("'");
			INSTER_SQL.append("),");
			
		}
		String sql = INSTER_SQL.toString();
		logger.debug("EdgeInstance {}",sql.substring(0,sql.length()-1));
		jdbcTemplate.execute(sql.substring(0,sql.length()-1));
		
	}

}
