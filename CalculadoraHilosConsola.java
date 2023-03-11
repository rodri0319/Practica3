import java.util.Scanner;

public class CalculadoraHilosConsola {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a la calculadora con hilos!");

        while (true) {
            System.out.print("Ingrese una operación (por ejemplo: 2 + 3): ");
            String input = scanner.nextLine();

            if (input.equals("exit")) {
                break;
            }

            String[] parts = input.split(" ");
            double firstValue = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double secondValue = Double.parseDouble(parts[2]);

            Thread t = new Thread(new CalculatorThread(firstValue, operator, secondValue));
            t.start();
        }

        scanner.close();
    }

    static class CalculatorThread implements Runnable {
        private double firstValue;
        private String operator;
        private double secondValue;

        public CalculatorThread(double firstValue, String operator, double secondValue) {
            this.firstValue = firstValue;
            this.operator = operator;
            this.secondValue = secondValue;
        }

        public void run() {
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstValue + secondValue;
                    break;
                case "-":
                    result = firstValue - secondValue;
                    break;
                case "*":
                    result = firstValue * secondValue;
                    break;
                case "/":
                    result = firstValue / secondValue;
                    break;
                default:
                    System.out.println("Operador inválido");
                    break;
            }

            System.out.printf("%.2f %s %.2f = %.2f\n", firstValue, operator, secondValue, result);
        }
    }
}