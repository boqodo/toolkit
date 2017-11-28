package java.z.cube.script;

import org.assertj.core.api.Assertions;
import org.joor.Reflect;
import org.junit.Test;
import sun.org.mozilla.javascript.internal.Context;
import sun.org.mozilla.javascript.internal.NativeObject;
import sun.org.mozilla.javascript.internal.Scriptable;
import z.cube.utils.ClassFileScanner;
import z.cube.utils.JsonUtilsEx;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static org.assertj.core.api.Assertions.*;


public class ScriptEngineTest {


    @Test
    public final void js() {
        // 创建脚本引擎管理器
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("JavaScript");
        // 创建一个处理JavaScript的脚本引擎
        //ScriptEngine engine = sem.getEngineByExtension("js");

        String json = JsonUtilsEx.generate(ClassFileScanner.loadConstants());
        System.out.println(json);
        try {
            // 执行js公式
            engine.eval("if(6>5){flag=true;}else{flag =false;}");
            engine.eval("var json=" + json + "; var max=json['Status']['MAX'];var sname=json.Status.ENABLED.name;");
        } catch (ScriptException ex) {
            ex.printStackTrace();
        }
        //看看我们预期的反馈结果 true
        assertThat(engine.get("flag")).isNotNull().isEqualTo(true);

        Double max = (Double) engine.get("max");
        System.out.println(max.longValue());

        NativeObject jsonObject= (NativeObject) engine.get("json");
        assertThat(max.longValue()).isEqualTo(Long.MAX_VALUE);
        assertThat(engine.get("sname")).isEqualTo("F");
    }

    @Test
    public final void parseJS() {
        Context cx = Context.enter();
        try {
            Scriptable scope = cx.initStandardObjects();
            String str = "9*(1+2)";
            Object result = cx.evaluateString(scope, str, null, 1, null);
            double res = Context.toNumber(result);
            System.out.println(res);
        } finally {
            Context.exit();
        }
    }
}
