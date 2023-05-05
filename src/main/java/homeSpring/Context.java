package homeSpring;

import java.io.File;
import java.util.*;

public class Context {
    private Map<Class<?>, Object> context = new HashMap<>();

    {
        String wayDir = Main.class.getAnnotation(ComponentScan.class).way();
        File file = new File(".\\src\\main\\java\\" + wayDir);
        List<String> list = foundClases(file);
        for (String nameClass : list) {
            if (nameClass.compareTo("Configuration") == 0) {
                continue;
            }
            try {
                inspectComponent(wayDir + "." + nameClass);
                inspectConfiguration(wayDir + "." + nameClass);
                inspectBean(wayDir + "." + nameClass);

            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Object getBean(Class<?> clazz) {
        return context.get(clazz);
    }


    private List<String> foundClases(File file) {
        List<String> list = new ArrayList<>();

        File[] files = file.listFiles();
        for (var elem : files) {
            if (elem.getName().contains(".java")) {
                String name = elem.getName().replace(".java", "");
                list.add(name);
                System.out.println();
            }
        }
        return list;
    }

    private void inspectComponent(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName(string);
        if (clazz.isAnnotationPresent(Component.class)) {

            context.put(clazz, clazz.newInstance());
        } else {
            return;
        }
    }

    private void inspectConfiguration(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName(string);
        if (clazz.isAnnotationPresent(Configuration.class)) {

            context.put(clazz, clazz.newInstance());
        } else {
            return;
        }
    }

    private void inspectBean(String string) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> clazz = Class.forName(string);
        var methods = clazz.getDeclaredMethods();
        for (var meth : methods) {
            if (clazz.isAnnotationPresent(Bean.class)) {
                Bean ann = clazz.getAnnotation(Bean.class);

                context.put(meth.getClass(), meth.getDeclaringClass());
            } else {
                return;
            }

        }


    }

}
