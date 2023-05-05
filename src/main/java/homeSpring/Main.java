package homeSpring;

import java.io.File;

@ComponentScan(way = "homeSpring")
@Configuration
public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        var myBean = context.getBean(MyBean.class);
        System.out.println(myBean);
    }
}