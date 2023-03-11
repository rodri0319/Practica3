import java.io.*;
import java.net.*;
import java.util.Scanner;

public class CalculadoraRemota {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            while (true) {
                Socket clientSocket = serverSocket.accept();

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            System.out.println("Error al crear el servidor: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String operacion = in.readLine();
            int a = Integer.parseInt(in.readLine());
            int b = Integer.parseInt(in.readLine());

            OperacionMatematica operacionMatematica = null;
            switch (operacion) {
                case "+":
                    operacionMatematica = new Suma(a, b);
                    break;
                case "-":
                    operacionMatematica = new Resta(a, b);
                    break;
                case "*":
                    operacionMatematica = new Multiplicacion(a, b);
                    break;
                case "/":
                    operacionMatematica = new Division(a, b);
                    break;
                default:
                    System.out.println("Operacion no valida.");
                    return;
            }
            operacionMatematica.start();
            out.println("La operacion se ha iniciado correctamente.");
        } catch (IOException e) {
            System.out.println("Error al manejar la conexion: " + e.getMessage());
        }
    }
}

abstract class OperacionMatematica extends Thread {
    protected int a;
    protected int b;

    public OperacionMatematica(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public abstract void realizarOperacion();

    public void run() {
        realizarOperacion();
    }
}

class Suma extends OperacionMatematica {
    public Suma(int a, int b) {
        super(a, b);
    }

    public void realizarOperacion() {
        int result = a + b;
        System.out.println("La suma de " + a + " y " + b + " es: " + result);
    }
}

class Resta extends OperacionMatematica {
    public Resta(int a, int b) {
        super(a, b);
    }

    public void realizarOperacion() {
        int result = a - b;
        System.out.println("La resta de " + a + " y " + b + " es: " + result);
    }
}

class Multiplicacion extends OperacionMatematica {
    public Multiplicacion(int a, int b) {
        super(a, b);
    }

    public void realizarOperacion() {
        int result = a * b;
        System.out.println("La multiplicacion de " + a + " y " + b + " es: " + result);
    }
}

class Division extends OperacionMatematica {
    public Division(int a, int b) {
        super(a, b);
    }

    public void realizarOperacion() {
        if (b == 0) {
            System.out.println("No se puede dividir por cero.");
            return;
        }
        double result = (double)a / b;
        System.out.println("La division de " + a + " y " + b + " es: " + result);
    }
}

