
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Reflect {


    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        //获取类对象
        Class<?> c = MethodClass.class;
        //实例化
        Object object = c.newInstance();
        System.out.println(object);
        //获取父类以及自己的方法
        Method[] methods = c.getMethods();
        //只能获取自己的方法
        Method[] declaredMethod = c.getDeclaredMethods();
        //获取add方法
        Method addMethod = c.getMethod("add", int.class, int.class);

        //getMethods获取到的方法
        System.out.println("getMethods获取的方法：");
        Arrays.stream(methods).forEach(method -> System.out.println(method));

        //getDeclared获取到的方法
        System.out.println("getDeclaredMethods获取的方法：");
        Arrays.stream(declaredMethod).forEach(method -> System.out.println(method));

        //方法调用
        System.out.println("调用add方法：");
        Object result = addMethod.invoke(object,10,1000);
        System.out.println(result);

        //利用反射创建数组
        System.out.println("利用反射创建数组：");
        Class<?> stringClass = String.class;
        Object stringArray = Array.newInstance(stringClass,10);
        Array.set(stringArray,0,"jacvk0");
        Array.set(stringArray,1,"jacvk1");
        Array.set(stringArray,2,"jacvk2");
        Array.set(stringArray,3,"jacvk3");
        Array.set(stringArray,4,"jacvk4");
        Array.set(stringArray,5,"jacvk5");

        System.out.println(Array.get(stringArray,3));



    }



}
