package homeSpring;

@Component
public class MyBean {
  @Bean
    public void sample()
    {
        System.out.println("Method bean");
    }

}
