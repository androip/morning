package morning.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IdGenerator {

	public static String generatFormId() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String timestr = "form"+df.format(new Date());
		return timestr;
	}

	public static String generatProcessInsId() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String timestr = "proc"+df.format(new Date());
		return timestr;
	}

	public static String generatRuleId() {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");// 设置日期格式
		String timestr = "rule"+df.format(new Date());
		return timestr;
	}

	
}
