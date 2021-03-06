package morning.repo;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import morning.entity.process.ProcessInstance;

@Component
public class ProcessInstanceDao {

	private static Logger logger = LoggerFactory.getLogger(ProcessInstanceDao.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void save(ProcessInstance procIns) {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO ProcessInstance");
		INSTER_SQL.append("(processInsId,processTemplateId,processName,createTime,updateTime,createUserId,status)");
		INSTER_SQL.append("VALUE(");
		INSTER_SQL.append("'").append(procIns.getProcessInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getProcessTemplateId()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getProcessName()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getCreateTime()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getUpdateTime()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getCreateUserId()).append("'").append(",");
		INSTER_SQL.append("'").append(procIns.getStatus()).append("'");
		INSTER_SQL.append(")");
		String sql = INSTER_SQL.toString();
		logger.debug("SQL:"+sql);
		jdbcTemplate.execute(sql);
		
	}

	public ProcessInstance getById(String processInsId) {
		StringBuffer INSTER_SQL = new StringBuffer("select * from ProcessInstance\n" + 
				"WHERE processInsId = ?");
		String sql = INSTER_SQL.toString();
		logger.debug("getById SQL: {}",sql);
		List<ProcessInstance> reslist = jdbcTemplate.query(sql, new String[] {processInsId},new BeanPropertyRowMapper<ProcessInstance>(ProcessInstance.class));
		return reslist.get(0);
	}


	public void updateStatus(String procInsId,String status) {
		StringBuffer UPDATE_SQL = new StringBuffer("UPDATE `ProcessInstance` AS P");
		UPDATE_SQL.append(" SET P.status = '").append(status).append("'");
		UPDATE_SQL.append(" WHERE P.processInsId = '").append(procInsId).append("'");
		String sql = UPDATE_SQL.toString();
		logger.debug("updateStatus SQL: {}",sql);
		jdbcTemplate.execute(sql);
	}

}
