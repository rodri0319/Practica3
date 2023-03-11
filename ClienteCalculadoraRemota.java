import java.io.*;
import java.net.*;

public class ClienteCalculadoraRemota {

    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            System.out.println("Ingrese la operacion (+, -, *, /):");
            String operacion = new BufferedReader(new InputStreamReader(System.in)).readLine();

            System.out.println("Ingrese el primer numero:");
            int a = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

            System.out.println("Ingrese el segundo numero:");
            int b = Integer.parseInt(new BufferedReader(new InputStreamReader(System.in)).readLine());

            out.println(operacion);
            out.println(a);
            out.println(b);

            String respuesta = in.readLine();
            System.out.println(respuesta);

            socket.close();
        } catch (IOException e) {
            System.out.println("Error al conectarse con el servidor: " + e.getMessage());
        }
    }
}
