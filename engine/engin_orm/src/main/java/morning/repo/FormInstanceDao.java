package morning.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import morning.dto.FormInstancDto;
import morning.entity.process.FormInstance;
import morning.exception.DBException;
import morning.vo.FormFieldInstance;


@Component
public class FormInstanceDao {

    private static Logger logger = LoggerFactory.getLogger(FormInstanceDao.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveAll(List<FormInstance> formInsList) throws DBException {
        StringBuffer INSTER_SQL = new StringBuffer("INSERT INTO FormInstance");
        INSTER_SQL.append("(formTid,formInstanceId,nodeInstanceId,processInstanceId,formType,formName)");
        INSTER_SQL.append("VALUES");
        for(FormInstance formIn:formInsList) {
            INSTER_SQL.append("(");
            INSTER_SQL.append("'").append(formIn.getFormTid()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormInstanceId()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getNodeInstanceId()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getProcessInstanceId()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormType()).append("'").append(",");
            INSTER_SQL.append("'").append(formIn.getFormName()).append("'");
            INSTER_SQL.append("),");

        }
        String sql = INSTER_SQL.toString();
        logger.debug("FormInstance SQL:{}",sql.substring(0,sql.length()-1));
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
	    logger.debug("FormInstance SQL:{}",sql.substring(0,sql.length()-1));
	    try {
	        jdbcTemplate.execute(sql.substring(0,sql.length()-1));
	    }catch (Exception e){
	        throw new DBException(e);
	    }
		
	}

	
	public void updateFormFieldVal(List<FormInstancDto> formInsDtoList) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("UPDATE FormFieldInstance AS F");
		SQL_BUF.append(" SET F.fval = CASE F.fkey");
		for(FormInstancDto form : formInsDtoList) {
			List<FormFieldInstance> fields = form.getFormFieldInstanceList();
			for(FormFieldInstance field: fields) {
				SQL_BUF.append(" WHENE").append("'").append(field.getFkey()).append("'")
				.append(" THEN").append("'").append(field.getFval());
			}
		}
		SQL_BUF.append(" END")
		.append(" WHERE F.formInstanceId ").append("IN (");
		formInsDtoList.forEach(form->{
			SQL_BUF.append("'").append(form.getFormInsid()).append("'");
		});
		SQL_BUF.append(")");
		String sql = SQL_BUF.toString();
		logger.debug("FormFiels update SQL:"+sql);
		try {
			jdbcTemplate.execute(sql);
		}catch(Exception e) {
			throw new DBException(e);
		}
		
	
		
	}


    public List<FormInstance> getFormInsByNodeInsId(String nodeInsId) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("SELECT * FROM FormInstance");
		SQL_BUF.append(" WHERE");
		SQL_BUF.append(" nodeInstanceId = ?");
		String sql = SQL_BUF.toString();
		logger.debug("Form Query SQL id[{}] :"+ sql,nodeInsId);
		List<FormInstance> reslist = new ArrayList<FormInstance>();
		try {
			reslist = jdbcTemplate.query(sql, new String[] {nodeInsId},new BeanPropertyRowMapper<FormInstance>(FormInstance.class));
		}catch (Exception e){
			throw new DBException(e);
		}
		return  reslist;
    }

	public Map<String,List<FormFieldInstance>> getFormFieldInstance(List<FormInstance> formInstanceList) throws DBException {

		Set<String> formInsIds = new HashSet<String>(){{
				formInstanceList.forEach(form->{
					add(form.getFormInstanceId());
				});
			}
		};

		StringBuffer SQL_BUF = new StringBuffer("SELECT * FROM FormFieldInstance");
		SQL_BUF.append(" WHERE");
		SQL_BUF.append(" formInstanceId IN (:ids)");
		String sql = SQL_BUF.toString();
		logger.debug("FormFiels Query SQL  :"+ sql);
		Map<String, List<FormFieldInstance>> resMap = null;


		Map<String, Object> args  = new HashMap<>();
		args.put("ids", formInsIds);

		Map<String,Object> paramMap = new HashMap<>();
		paramMap.put("ids", formInsIds);

		NamedParameterJdbcTemplate givenParamJdbcTemp = new NamedParameterJdbcTemplate(jdbcTemplate);

		try {
			resMap =  givenParamJdbcTemp.query(sql, args, new ResultSetExtractor<Map<String,List<FormFieldInstance>>>() {
				@Override
				public Map<String,List<FormFieldInstance>> extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String,List<FormFieldInstance>> resMap = new HashMap<>();

					while (rs.next()){

						FormFieldInstance field = new FormFieldInstance();
						field.setFormInstanceId(rs.getString("formInstanceId"));
						field.setFkey(rs.getString("fkey"));
						field.setFval(rs.getString("fval"));
						field.setFtype(rs.getString("ftype"));
						field.setRkey(rs.getString("rkey"));
						field.setTableName(rs.getString("tableName"));

						if(!resMap.containsKey(field.getFormInstanceId())){
							List<FormFieldInstance> reslist = new ArrayList<FormFieldInstance>();
							reslist.add(field);
							resMap.put(field.getFormInstanceId(),reslist);
						}else {
							resMap.get(field.getFormInstanceId()).add(field);
						}

					}
					return resMap;
				}
			});


		}catch (Exception e){
			throw new DBException(e);
		}


		return resMap;

	}

	public List<FormInstance> getFormInsByProInsId(String proceInsId) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("SELECT * FROM FormInstance");
		SQL_BUF.append(" WHERE");
		SQL_BUF.append(" processInstanceId = ?");
		String sql = SQL_BUF.toString();
		logger.debug("Form Query SQL id[{}] :"+ sql,proceInsId);
		List<FormInstance> reslist = new ArrayList<FormInstance>();
		try {
			reslist = jdbcTemplate.query(sql, new String[] {proceInsId},new BeanPropertyRowMapper<FormInstance>(FormInstance.class));
		}catch (Exception e){
			throw new DBException(e);
		}
		return  reslist;
    
	}

	public List<FormFieldInstance> queryFieldByFormInsId(String formInsId) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("SELECT * FROM FormFieldInstance");
		SQL_BUF.append(" WHERE");
		SQL_BUF.append(" formInstanceId = ?");
		String sql = SQL_BUF.toString();
		logger.debug("query field SQL id[{}] :"+ sql,formInsId);
		List<FormFieldInstance> res = new ArrayList<FormFieldInstance>();
		try {
			res = jdbcTemplate.query(sql, new String[] {formInsId},new BeanPropertyRowMapper<FormFieldInstance>(FormFieldInstance.class));
		}catch(Exception e){
			throw new DBException(e);
		}
		return res;
	}

	public List<FormFieldInstance> getFieldInsByNodeInsId(String nodeInsId) throws DBException {
		StringBuffer SQL_BUF = new StringBuffer("SELECT * FROM FormFieldInstance F ");
		SQL_BUF.append("WHERE F.formInstanceId IN (");
		SQL_BUF.append("SELECT form.formInstanceId ");
		SQL_BUF.append("FROM FormInstance form ");
		SQL_BUF.append("WHERE form.nodeInstanceId= ? );");
		String sql = SQL_BUF.toString();
		logger.debug("query field SQL id[{}] :"+ sql,nodeInsId);
		List<FormFieldInstance> res = new ArrayList<FormFieldInstance>();
		try {
			res = jdbcTemplate.query(sql, new String[] {nodeInsId},new BeanPropertyRowMapper<FormFieldInstance>(FormFieldInstance.class));
		}catch(Exception e){
			throw new DBException(e);
		}
		return res;
	}
}
























