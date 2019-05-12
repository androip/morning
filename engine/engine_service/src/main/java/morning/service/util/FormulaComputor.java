package morning.service.util;

import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class FormulaComputor {

	public static void main(String args[]) {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
        Compilable compilable = (Compilable) engine;
        Bindings bindings = engine.createBindings(); //Local级别的Binding
        String script1 = "(1+0.1 * (F/100) * T)*P0"; //定义函数并调用
        String script2 = "A+B*100"; //定义函数并调用
        CompiledScript JSFunction = null; //解析编译脚本函数
        try {
            JSFunction = compilable.compile(script2);
            bindings.put("A", 1);
            bindings.put("B", 2);
//            bindings.put("A", 100);
//            bindings.put("P0", 100);
            Object result = JSFunction.eval(bindings);
            System.out.println(result); //调用缓存着的脚本函数对象，Bindings作为参数容器传入
        } catch (ScriptException e) {
            e.printStackTrace();
        }


    }
}
