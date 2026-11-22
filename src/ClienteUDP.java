import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClienteUDP {
    private static final String HOST = "localhost";
    private static final int PUERTO = 9876;
    private static final int TIMEOUT_MS = 2000;
    private static final int MAX_REINTENTOS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduce varias palabras separadas por espacios:");
        String linea = scanner.nextLine().trim();

        if (linea.isEmpty()) {
            return;
        }

        String[] palabras = linea.split("\\s+");
        String payload = String.join(";", palabras);

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
            InetAddress direccionServidor = InetAddress.getByName(HOST);
            byte[] bufferEnvio = payload.getBytes(StandardCharsets.UTF_8);
            byte[] bufferRecepcion = new byte[1024];

            boolean recibido = false;

            for (int intento = 1; intento <= MAX_REINTENTOS; intento++) {
                DatagramPacket paqueteEnvio = new DatagramPacket(
                        bufferEnvio,
                        bufferEnvio.length,
                        direccionServidor,
                        PUERTO
                );
                socket.send(paqueteEnvio);

                try {
                    DatagramPacket paqueteRecepcion = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
                    socket.receive(paqueteRecepcion);

                    String respuesta = new String(
                            paqueteRecepcion.getData(),
                            0,
                            paqueteRecepcion.getLength(),
                            StandardCharsets.UTF_8
                    );
                    String[] partes = respuesta.split(";");
                    System.out.println("Palabra más larga: " + partes[0] + " (Longitud: " + partes[1] + ")");
                    recibido = true;
                    break;
                } catch (SocketTimeoutException e) {
                    System.out.println("Tiempo agotado en el intento " + intento + " de " + MAX_REINTENTOS + ".");
                }
            }

            if (!recibido) {
                System.out.println("Servidor no disponible");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
