package morning.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import morning.entity.TaskOverview;


@Component
public class TaskOverviewDao {

	private static Logger logger = LoggerFactory.getLogger(TaskOverviewDao.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void save(TaskOverview view) {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO TaskOverview");
		INSTER_SQL.append("(userId,processInsId,processNodeInsId,taskStatus)");
		INSTER_SQL.append("VALUE(");
		INSTER_SQL.append("'").append(view.getUserId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcessInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getProcessNodeInsId()).append("'").append(",");
		INSTER_SQL.append("'").append(view.getTaskStatus()).append("'");
		INSTER_SQL.append(")");
		String sql = INSTER_SQL.toString();
		logger.debug("TaskOverview SQL: {}",sql);
		jdbcTemplate.execute(sql);
		
	}
}
