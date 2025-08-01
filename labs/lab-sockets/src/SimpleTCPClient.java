import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Scanner scanner;

    public void start(String serverIp, int serverPort) throws IOException {
        System.out.println("[C1] Conectando ao servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        scanner = new Scanner(System.in);

        System.out.println("[C2] Conexão estabelecida. Digite 'sair' para encerrar.");

        while (true) {
            System.out.print("Digite uma mensagem: ");
            String msg = scanner.nextLine();

            if ("sair".equalsIgnoreCase(msg)) {
                break;
            }

            output.writeUTF(msg);
            System.out.println("[C3] Mensagem enviada: " + msg);

            String response = input.readUTF();
            System.out.println("[C4] Resposta do servidor: " + response);
        }
    }

    public void stop() {
        try {
            if (scanner != null) scanner.close();
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
            System.out.println("[C5] Conexão encerrada.");
        } catch (IOException e) {
            System.err.println("Erro ao fechar recursos: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String serverIp = "localhost"; 
        int serverPort = 6666;
        SimpleTCPClient client = new SimpleTCPClient();

        try {
            client.start(serverIp, serverPort);
        } catch (IOException e) {
            System.err.println("Erro no cliente: " + e.getMessage());
        } finally {
            client.stop();
        }
    }
}