import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

class ClientHandler extends Thread {
    private final DataInputStream in;
    private final DataOutputStream out;
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.in = new DataInputStream(clientSocket.getInputStream());
        this.out = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("[SERVER] Novo cliente conectado: " + clientSocket.getRemoteSocketAddress());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = in.readUTF(); 
                System.out.println("[SERVER] Mensagem de " + clientSocket.getRemoteSocketAddress() + ": " + message);

                String response = message.toUpperCase();
                out.writeUTF(response);

                if ("SAIR".equalsIgnoreCase(message)) {
                    break;
                }
            }
        } catch (EOFException e) {
            System.out.println("[SERVER] Cliente desconectou: " + clientSocket.getRemoteSocketAddress());
        } catch (IOException e) {
            System.err.println("[SERVER] Erro com " + clientSocket.getRemoteSocketAddress() + ": " + e.getMessage());
        } finally {
            closeResources();
        }
    }

    private void closeResources() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("[SERVER] Erro ao fechar recursos: " + e.getMessage());
        }
    }
}