package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Server is active and wait requests...");

        try (ServerSocket server = new ServerSocket(4305)) {

            while(!server.isClosed()) {
                createNewThread(server.accept());
            }

        } catch (Exception ignored) {}
    }
    public static void createNewThread(Socket client) throws IOException {

        class NewThread extends Thread {
            private Socket client;
            public NewThread(Socket socket) {
                this.client = socket;
            }
            public void run() {
                try {
                    System.out.println("\u001B[0mConnection accepted.");
                    DataInputStream in = new DataInputStream(client.getInputStream());
                    while (!client.isClosed()) {
                        String entry = in.readUTF();

                        String pattern = "POST /api/v1/auth/login HTTP/1.1\n" +
                                "Host: localhost:4305\n" +
                                "Content-Type: application/json\n" +
                                "cache-control: no-ca\n" +
                                "Postman-Token: 0f4152ed-f547-4072-b86b-f456dcb0d1fa\n" +
                                "{\n" +
                                "      \t\"request\":\"" + entry + "\"\n" +
                                "}\n";
                        System.out.println("\u001B[0mServer get Request:");
                        System.out.println("\u001B[33m" + pattern);
                    }
                    in.close();
                } catch (IOException e) {
                    System.out.println("\u001B[0mConnection closed");
                }
            }
        }

        NewThread newThread = new NewThread(client);
        newThread.start();

    }
}
