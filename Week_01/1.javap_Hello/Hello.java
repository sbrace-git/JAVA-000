public class Hello {
    public static void main(String[] args) {

    	int a = 10086;
    	int b = 10010;
    	double c = 3.0;
    	double d = 4.0;

    	int add = a + b;
    	int subtract = b - a;
    	int multiply = a * b;
    	double divide = c / d;

        int sum = 0;
        for (int i = 0; i < 10; i++) {
            if (sum % 2 == 0) {
                sum ++;
            }
        }
    }
}
