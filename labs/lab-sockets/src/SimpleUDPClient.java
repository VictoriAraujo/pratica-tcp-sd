import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class SimpleUDPClient {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;
    private byte[] buffer;

    public void start(String serverIp, int serverPort) throws IOException {
        System.out.println("[C1] Criando socket UDP para comunicação com servidor");
        socket = new DatagramSocket();
        this.serverAddress = InetAddress.getByName(serverIp);
        this.serverPort = serverPort;
        this.buffer = new byte[1024];

        Scanner scanner = new Scanner(System.in);
        
        try {
            while (true) {
                System.out.print("Digite uma mensagem (ou 'sair' para encerrar): ");
                String msg = scanner.nextLine();
                
                if ("sair".equalsIgnoreCase(msg)) {
                    break;
                }

                System.out.println("[C2] Enviando msg para " + serverIp + ":" + serverPort + ": " + msg);
                sendMessage(msg);
                
                String response = receiveMessage();
                System.out.println("[C3] Resposta recebida: " + response);
            }
        } finally {
            scanner.close();
            stop();
        }
    }

    public void sendMessage(String msg) throws IOException {
        DatagramPacket packet = new DatagramPacket(
            msg.getBytes(), 
            msg.length(), 
            serverAddress, 
            serverPort
        );
        socket.send(packet);
    }

    public String receiveMessage() throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void stop() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("[C4] Conexão encerrada.");
        }
    }

    public static void main(String[] args) {
        String serverIp = "127.0.0.1"; 
        int serverPort = 6789;
        
        try {
            SimpleUDPClient client = new SimpleUDPClient();
            client.start(serverIp, serverPort);
        } catch (SocketException e) {
            System.out.println("Erro no socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Erro de E/S: " + e.getMessage());
        }
    }
}