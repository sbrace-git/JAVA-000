import java.nio.file.Files;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> helloClazz = new HelloClassLoader().findClass("Hello");
            helloClazz.getMethod("hello").invoke(helloClazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(getResource("Hello.xlass").toURI()));
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
            return defineClass(name, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}