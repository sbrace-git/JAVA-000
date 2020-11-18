import annotation.AnnocationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import xml.XmlBean;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        AnnocationBean annocationBean = context.getBean(AnnocationBean.class);
        annocationBean.test();
        XmlBean xmlBean = (XmlBean)context.getBean("xmlBean");
        xmlBean.test();
    }
}
