package cn.newbeedaly.mybatis.sqlparse;

import org.apache.ibatis.annotations.Select;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


public class ProxyTest {

    public static void main(String[] args) {
        // 接口不能实例化，可以通过匿名内部类创建接口的实现类和方法，Proxy类是JVM动态的创建接口的实现，通过InvocationHandler来实现方法体。
        UserMapper userMapper = (UserMapper)Proxy.newProxyInstance(ProxyTest.class.getClassLoader(), new Class<?>[]{UserMapper.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) {
                //System.out.println(method.getName());
                //System.out.println(args);
                Select annotation = method.getAnnotation(Select.class);
                if(annotation!=null){
                    System.out.println(annotation.value());
                }
                Map<String, Object> methodArgsNameMap = buildMethodArgsNameMap(method, args);
                String sql = parseSql(annotation.value()[0], methodArgsNameMap);
                System.out.println(sql);
                System.out.println(method.getReturnType());
                System.out.println(method.getGenericReturnType());
                return null;
            }


        });

        userMapper.selectUserList(1, "newbeedaly");
    }

    private static String parseSql(String sql, Map<String, Object> methodArgsNameMap) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < sql.length(); i++) {
            char c = sql.charAt(i);
            if(c == '#'){
                int nextIndex = i + 1;
                char nextChar = sql.charAt(nextIndex);
                if(nextChar != '{'){
                    throw new RuntimeException(String.format("这里应该为#{\nsql:%s\nindex:%d",
                            stringBuilder.toString(), nextIndex));
                }
                StringBuilder argSB = new StringBuilder();
                i = parseSqlArg(argSB, sql, nextIndex);
                String argName = argSB.toString();
                Object argValue = methodArgsNameMap.get(argName);
                stringBuilder.append(argValue.toString());
                continue;
            }
            stringBuilder.append(c);
        }

        return stringBuilder.toString();
    }

    private static int parseSqlArg(StringBuilder argSB, String sql, int nextIndex) {
        nextIndex++;
        for (; nextIndex < sql.length(); nextIndex++) {
            char c = sql.charAt(nextIndex);
            if (c != '}'){
                argSB.append(c);
                continue;
            }
            if (c == '}'){
                return nextIndex;
            }
        }
        throw new RuntimeException(String.format("缺少}\nindex:%d", nextIndex));
    }

    public static Map<String, Object> buildMethodArgsNameMap(Method methond, Object[] args){
        HashMap<String, Object> nameArgsMap = new HashMap<>();
        Parameter[] parameters = methond.getParameters();
        AtomicInteger index = new AtomicInteger(0);
        Arrays.asList(parameters).forEach(parameter -> {
            String name = parameter.getName();
            // id name
            System.out.println(name);
            nameArgsMap.put(name, args[index.getAndIncrement()]);
        });
        return nameArgsMap;
    }

}
