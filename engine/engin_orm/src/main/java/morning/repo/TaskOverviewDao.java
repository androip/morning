package morning.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import morning.entity.TaskOverview;
import morning.exception.DBException;


@Component
public class TaskOverviewDao {

	private static Logger logger = LoggerFactory.getLogger(TaskOverviewDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void save(TaskOverview view) throws DBException {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO TaskOverview");
		INSTER_SQL.append("(userId,processInsId,processNodeInsId,createTime,taskName,processName,taskStatus,nodeTId,processsTId)");
		INSTER_SQL.append("VALUE(");
		INSTER_SQL.append("'").append(view.getUserId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcessInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcessNodeInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getCreateTime()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getTaskName()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcessName()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getTaskStatus()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getNodeTId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcesssTId()).append("'").append(",");
		INSTER_SQL.append(")");
		String sql = INSTER_SQL.toString();
		logger.debug("TaskOverview SQL: {}",sql);
		try {
			
		}catch (Exception e){
			throw new DBException(e);
		}
		jdbcTemplate.update(sql);
		
	}

	public List<TaskOverview> queryByUserId(String userId) {
		StringBuffer SELECT_SQL = new StringBuffer("SELECT * FROM TaskOverview");
		SELECT_SQL.append(" WHERE 1=1 ").append(" AND");
		SELECT_SQL.append(" userId=?");
		String sql = SELECT_SQL.toString();
		logger.debug("TaskOverview Query SQL: {}",sql);
		List<TaskOverview> reslist = new ArrayList<TaskOverview>();
		reslist = jdbcTemplate.query(sql, new String[] {userId},new BeanPropertyRowMapper<TaskOverview>(TaskOverview.class));
		return reslist;
	}

	public void save(List<TaskOverview> taskOtaskOVs) throws DBException {
		logger.info("save all TaskOverview {}",taskOtaskOVs);
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO TaskOverview");
		INSTER_SQL.append("(userId,processInsId,processNodeInsId,createTime,taskName,processName,taskStatus,nodeTId,processsTId)");
		INSTER_SQL.append("VALUES");
		for(TaskOverview view:taskOtaskOVs) {
			INSTER_SQL.append("(");
			INSTER_SQL.append("'").append(view.getUserId()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getProcessInsId()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getProcessNodeInsId()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getCreateTime()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getTaskName()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getProcessName()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getTaskStatus()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getNodeTId()).append("'").append(",");
			INSTER_SQL.append("'").append(view.getProcesssTId()).append("'");
			INSTER_SQL.append("),");
		}
		String sql = INSTER_SQL.toString();
		logger.debug("TaskOverview SQL: {}",sql.substring(0,sql.length()-1));
		try {
			jdbcTemplate.execute(sql.substring(0,sql.length()-1));
		}catch (Exception e){
			throw new DBException(e);
		}
		
	
		
		
	}
}
