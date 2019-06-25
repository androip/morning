package morning.repo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import morning.entity.process.NodeInstance;
import morning.entity.process.ProcessInstance;
import morning.exception.DBException;

@Component
public class NodeInstanceDao {

	private static Logger logger = LoggerFactory.getLogger(NodeInstanceDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void save(NodeInstance nodeIns) throws DBException {
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
		logger.debug("NodeInstance SQL:"+sql);
		try {
			jdbcTemplate.execute(sql);
		}catch(Exception e) {
			throw new DBException(e);
		}
			
		
	}

	public NodeInstance getById(String nodeInsId) throws DBException {
		StringBuffer SELECT_SQL = new StringBuffer("SELECT * FROM NodeInstance");
		SELECT_SQL.append(" WHERE 1 = 1");
		SELECT_SQL.append(" AND nodeInsId = ?");
		String sql = SELECT_SQL.toString();
		NodeInstance obj = null;
		logger.debug("NodeInstance getById SQL:"+sql);
		List<NodeInstance> reslist = null;
		try {
//			obj = jdbcTemplate.queryForObject(sql,new String[] {nodeInsId},NodeInstance.class);
			reslist  = jdbcTemplate.query(sql, new String[] {nodeInsId},new BeanPropertyRowMapper<NodeInstance>(NodeInstance.class));
		}catch (Exception e){
			throw new DBException(e);
		}
		return reslist.get(0);
	}

	public void updateNodeStatus(NodeInstance nodeIns) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("UPDATE NodeInstance");
		SQL_BUF.append(" SET nodeStatus = ")
		.append(nodeIns.getNodeStatus())
		.append(" WHERE nodeInsId = ").append("'").append(nodeIns.getNodeInsId()).append("'");
		String sql = SQL_BUF.toString();
		logger.debug("NodeInstance SQL:"+sql);
		try {
			jdbcTemplate.execute(sql);
		}catch(Exception e) {
			throw new DBException(e);
		}
		
	}
	
	
}
