Chat TCP para múltiplos usuários em Java

Imagem de clientes conversando com o servidor tcp.

<img width="1029" height="280" alt="image" src="https://github.com/user-attachments/assets/fab92911-0c65-4579-8307-979dd666dbc6" />

Funcionalidades Implementadas
O servidor pode lidar com múltiplos clientes simultaneamente.
Cada cliente é atendido em uma thread separada.
O servidor mostra logs das conexões e mensagens recebidas.

Cliente TCP:

Permite enviar mensagens para o servidor.
Recebe respostas do servidor.
Comando "sair" para encerrar a conexão.

Comunicação:

Troca de mensagens texto entre cliente e servidor
Conexões persistentes até o cliente encerrar
<img width="495" height="122" alt="image" src="https://github.com/user-attachments/assets/f5766db1-3ac9-4cce-9fa8-3b4af5cea8bb" />

Servidor (SimpleTCPServer e MultiTCPServer)
Usa ServerSocket para aceitar conexões.

Cria uma nova thread (ClientHandler) para cada cliente
Registra atividades no console.

Handler de Cliente (ClientHandler)
Thread separada para cada cliente.
Lê mensagens com DataInputStream.
Envia respostas com DataOutputStream.
Gerencia o ciclo de vida da conexão.

Cliente (SimpleTCPClient)
Conecta ao servidor via Socket.
Thread principal para enviar mensagens.
Recebe respostas do servidor.
Interface simples com o usuário via console.
