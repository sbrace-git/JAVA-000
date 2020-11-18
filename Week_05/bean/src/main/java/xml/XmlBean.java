package xml;

import lombok.Data;

@Data
public class XmlBean {
    private Long id;
    private String name;

    public XmlBean(){
        System.out.println("new XmlBean");
    }

    public void test() {
        System.out.println("test XmlBean");
    }
}
