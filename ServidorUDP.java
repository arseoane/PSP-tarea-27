import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;

public class ServidorUDP {
    private static final int PUERTO = 9876;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PUERTO)) {
            byte[] bufferRecepcion = new byte[1024];

            while (true) {
                DatagramPacket paqueteEntrada = new DatagramPacket(bufferRecepcion, bufferRecepcion.length);
                socket.receive(paqueteEntrada);

                String mensaje = new String(paqueteEntrada.getData(), 0, paqueteEntrada.getLength(), StandardCharsets.UTF_8);
                String[] palabras = mensaje.split(";");

                String masLarga = "";
                for (String palabra : palabras) {
                    String limpia = palabra.trim();
                    if (limpia.length() > masLarga.length()) {
                        masLarga = limpia;
                    }
                }

                String respuesta = masLarga + ";" + masLarga.length();
                byte[] bufferEnvio = respuesta.getBytes(StandardCharsets.UTF_8);

                DatagramPacket paqueteSalida = new DatagramPacket(
                        bufferEnvio,
                        bufferEnvio.length,
                        paqueteEntrada.getAddress(),
                        paqueteEntrada.getPort()
                );
                socket.send(paqueteSalida);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
