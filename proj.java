public class proj {
    public static void main(String[] args) {
        try {
            System.out.println("Complex Java Application is running!");
            int result = divide(10, 0);
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            System.err.println("Error: " + e.getMessage());
            throw e;
        }
    }

    public static int divide(int a, int b) {
        return a / b;
    }
}
