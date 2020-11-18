package xml;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        XmlBean xmlBean = (XmlBean) context.getBean("xmlBean");
        xmlBean.test();
    }
}
