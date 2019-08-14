package utils;

import anno.*;
import dto.User;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class ParseHibernateAnnotation {

    public static void main(String[] args) {
        Class<User> clazz = User.class;
        MyEntity myEntity =  clazz.getAnnotation(MyEntity.class);

        if (myEntity == null) {
            System.out.println("User.class 不是实体类");
        } else {
            System.out.println("User.class 是实体类");
            MyTable myTable =  clazz.getAnnotation(MyTable.class);
            String tableName = myTable.name();
            System.out.println("User.class对应的表名： " + tableName);

            Method[] methods = clazz.getMethods();
            Method primaryKeyMethod = null;

           Optional<Method> method = (Arrays.stream(methods)
                   .filter(
                           m ->
                                   m.getAnnotation(MyId.class) != null)
                   .collect(toList())
                   .stream()
                   .findFirst());

            if (method.isPresent()) {
                primaryKeyMethod = method.get();
                System.out.println("Primary Key : " + primaryKeyMethod.getName());
                MyGeneratedValue myGeneratedValue = primaryKeyMethod.getAnnotation(MyGeneratedValue.class);
                System.out.println("The strategy of id : " + myGeneratedValue.strategy());
                MyColumn myColumn = primaryKeyMethod.getAnnotation(MyColumn.class);
                System.out.println("The mapped column :" + myColumn.value());
            }
            System.out.println("其他非主键属性分别对应的数据库字段如下：");
            Method finalPrimaryKeyMethod = primaryKeyMethod;
            Map<String,String> mapper = Arrays.stream(methods)
                    .filter(
                            method1 ->
                                    method1 != finalPrimaryKeyMethod && method1.getAnnotation(MyColumn.class) !=null
                            )
                    .collect(Collectors.toMap(m -> method2attribute(m.getName()),m-> m.getAnnotation(MyColumn.class).value()));

             mapper.forEach((k ,v) -> System.out.println("key： " + k + "  v: "+ v));
        }
    }

    private static String method2attribute(String methodName) {
        String result = methodName;
        result = result.replaceFirst("get", "");
        result = result.replaceFirst("is", "");
        if(result.length()<=1){
            return result.toLowerCase();
        }
        else{
            return result.substring(0,1).toLowerCase() + result.substring(1);
        }
    }
}
