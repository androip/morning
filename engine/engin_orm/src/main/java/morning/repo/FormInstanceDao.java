package morning.repo;

import morning.dto.FormInstancDto;
import morning.entity.process.EdgeIns;
import morning.entity.process.FormInstance;
import morning.exception.DBException;
import morning.vo.FormFieldInstance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class FormInstanceDao {

    private static Logger logger = LoggerFactory.getLogger(FormInstanceDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveAll(List<FormInstance> formInsList) throws DBException {
        StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO FormInstance");
        INSTER_SQL.append("(formTid,formInstanceId,nodeInstanceId,formType,formName)");
        INSTER_SQL.append("VALUES");
        for(FormInstance formIn:formInsList) {
            INSTER_SQL.append("(");
            INSTER_SQL.append("'").append(formIn.getFormTid()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormInstanceId()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getNodeInstanceId()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormType()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormName()).append("'");
            INSTER_SQL.append("),");

        }
        String sql = INSTER_SQL.toString();
        logger.debug("FormInstance SQL{}",sql.substring(0,sql.length()-1));
        try {
            jdbcTemplate.execute(sql.substring(0,sql.length()-1));
        }catch (Exception e){
            throw new DBException(e);
        }
    }

	public void saveField(List<FormInstancDto> formInsDtoList) throws DBException {
		StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO FormFieldInstance");
		INSTER_SQL.append("(formInstanceId,fkey,fval,ftype,rkey,tableName)");
		INSTER_SQL.append("VALUES");
		for(FormInstancDto form : formInsDtoList) {
			List<FormFieldInstance> fields = form.getFormFieldInstanceList();
			for(FormFieldInstance field: fields) {
				INSTER_SQL.append("(");
				INSTER_SQL.append("'").append(field.getFormInstanceId()).append("'").append(",");
				INSTER_SQL.append("'").append(field.getFkey()).append("'").append(",");
				INSTER_SQL.append("'").append(field.getFval()).append("'").append(",");
				INSTER_SQL.append("'").append(field.getFtype()).append("'").append(",");
				INSTER_SQL.append("'").append(field.getRkey()).append("'").append(",");
				INSTER_SQL.append("'").append(field.getTableName()).append("'");
				INSTER_SQL.append("),");
				
			}
		}

		String sql = INSTER_SQL.toString();
	    logger.debug("FormInstance SQL{}",sql.substring(0,sql.length()-1));
	    try {
	        jdbcTemplate.execute(sql.substring(0,sql.length()-1));
	    }catch (Exception e){
	        throw new DBException(e);
	    }
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
