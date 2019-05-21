package morning.service.util;

import java.util.HashMap;
import java.util.Map;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import morining.dto.rule.FieldTransformRuleDto;

public class FormulaComputor {

//	public static void main(String args[]) {
//
//        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
//        Compilable compilable = (Compilable) engine;
//        Bindings bindings = engine.createBindings(); //Local级别的Binding
//        String script1 = "(1+0.1 * (F/100) * T)*P0"; //定义函数并调用
//        String formlua = "A+B*100"; //定义函数并调用
//        CompiledScript JSFunction = null; //解析编译脚本函数
//        try {
//            JSFunction = compilable.compile(formlua);
//            bindings.put("A", 1);
//            bindings.put("B", 2);
////            bindings.put("A", 100);
////            bindings.put("P0", 100);
//            Object result = JSFunction.eval(bindings);
//            System.out.println(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
//
//
//    }
	
	/**
	 * 传入源字段和公式，返回结果.
	 * @param fieldMap 源单字段
	 * k:字fkey v:字段值
	 * @return
	 */
	public static Object calculate(Map<String,Object>fieldMap,String formlua) {
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
	    Compilable compilable = (Compilable) engine;
	    Bindings bindings = engine.createBindings(); 
	    CompiledScript JSFunction = null; //解析编译脚本函数
	    Object result = null;
        try {
            JSFunction = compilable.compile(formlua);
			for (Map.Entry<String, Object > entry : fieldMap.entrySet()) {
				bindings.put(entry.getKey(),entry.getValue());
			}
            result = JSFunction.eval(bindings);
            System.out.println(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
        } catch (ScriptException e) {
            e.printStackTrace();
        }
		return result;
	}

	/**
	 * 传入源字段和公式，返回结果.
	 * @param computFieldValMap 源单字段
	 * k:字fkey v:字段值
	 * @return
	 */
	public static Map<String, Object> calculate(Map<String, Object> computFieldValMap,FieldTransformRuleDto fieldrule) {
		Map<String, Object> resMap = new HashMap<String,Object>();
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
	    Compilable compilable = (Compilable) engine;
	    Bindings bindings = engine.createBindings(); 
	    CompiledScript JSFunction = null; //解析编译脚本函数
	    Object result = null;
        try {
            JSFunction = compilable.compile(fieldrule.getFormula());
            bindings = (Bindings) computFieldValMap;
            result = JSFunction.eval(bindings);
            System.out.println(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
        } catch (ScriptException e) {
            e.printStackTrace();
        }
        resMap.put(fieldrule.getDesFkey(), result);
		return resMap;
	}
}












 
