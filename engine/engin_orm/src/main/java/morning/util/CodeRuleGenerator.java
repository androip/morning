package morning.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author jp
 * @date 2017年11月9日
 *
 */
@Component
public class CodeRuleGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CodeRuleGenerator.class);

	/**
	 * 生成编码。规则：业务前缀-时间(eg : yyyymmdd)-流水。
	 * 
	 * @param bus_prefix
	 *            表示业务
	 * @param formatSymbol
	 *            格式化符号 eg. -%s- %d
	 * @param dateFormat
	 *            时间格式 eg.yyyymmdd
	 * @param stream
	 *            流水号
	 * 
	 * @return code值
	 * @author jp
	 */
	public String generateCode(String bus_prefix, String formatSymbol, String dateFormat, String stream) {

		String str = null;
		SimpleDateFormat df = new SimpleDateFormat(dateFormat);// 设置日期格式
		String timestr = df.format(new Date());
		str = String.format(bus_prefix + formatSymbol, timestr);
		str = String.format(str+formatSymbol, stream);
		return str;
	}

	
}
