package annotation;

import org.springframework.stereotype.Component;

@Component
public class AnnocationBean {
    private Long id;
    private String name;

    public AnnocationBean(){
        System.out.println("new AnnocationBean");
    }

    public void test() {
        System.out.println("test AnnocationBean");
    }
}
