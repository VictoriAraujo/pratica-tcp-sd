import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleTCPServer {
    private ServerSocket serverSocket;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("[SERVER] Servidor iniciado na porta " + port + ". Aguardando conexões...");

        while (true) {
            Socket clientSocket = serverSocket.accept(); // Bloqueia até nova conexão
            try {
                // Cria um novo handler para o cliente (em uma thread separada)
                new ClientHandler(clientSocket).start();
            } catch (IOException e) {
                System.err.println("[SERVER] Falha ao criar handler: " + e.getMessage());
                clientSocket.close();
            }
        }
    }

    public void stop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
            System.out.println("[SERVER] Servidor encerrado.");
        }
    }

    public static void main(String[] args) {
        int port = 6666;
        SimpleTCPServer server = new SimpleTCPServer();

        try {
            server.start(port);
        } catch (IOException e) {
            System.err.println("[SERVER] Erro no servidor: " + e.getMessage());
        } finally {
            try {
                server.stop();
            } catch (IOException e) {
                System.err.println("[SERVER] Erro ao encerrar: " + e.getMessage());
            }
        }
    }
}