import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class SimpleUDPServer {
    private final int BUFFER_SIZE = 1000;
    private DatagramSocket socket;

    public void start(int port) throws IOException {
        System.out.println("[S1] Criando socket UDP para aguardar mensagens de clientes em loop");
        socket = new DatagramSocket(port);
        
        while (true) {
            System.out.println("[S2] Aguardando nova mensagem em: " + socket.getLocalSocketAddress());
            DatagramPacket request = receivePacket();
            
            new Thread(() -> handleRequest(request)).start();
        }
    }

    private void handleRequest(DatagramPacket request) {
        try {
            String msg = new String(request.getData(), 0, request.getLength());
            System.out.println("[S3] Mensagem recebida de " + request.getSocketAddress() + ": " + msg);
            
            String reply = msg.toUpperCase();
            sendMessage(reply, request.getAddress(), request.getPort());
            System.out.println("[S4] Mensagem enviada para " + request.getSocketAddress() + ": " + reply);
        } catch (IOException e) {
            System.err.println("Erro ao processar mensagem: " + e.getMessage());
        }
    }

    private void sendMessage(String msg, InetAddress address, int port) throws IOException {
        byte[] buffer = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    private DatagramPacket receivePacket() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, BUFFER_SIZE);
        socket.receive(packet);
        return packet;
    }

    public static void main(String[] args) {
        try {
            SimpleUDPServer server = new SimpleUDPServer();
            int port = 6789;
            server.start(port);
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        }
    }
}