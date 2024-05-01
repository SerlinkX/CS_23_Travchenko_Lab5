import java.io.*;
import java.net.*;

// Клас, який реалізує UDP сервер
class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876); // Створення сокету для сервера з портом 9876

        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket); // Очікування отримання даних від клієнта

            String clientData = new String(receivePacket.getData()); // Отримання даних від клієнта
            InetAddress clientIP = receivePacket.getAddress(); // Отримання IP-адреси клієнта
            int clientPort = receivePacket.getPort(); // Отримання порту клієнта

            System.out.println("Received from client: " + clientData);

            String response = "Registration successful. Registered clients:\n"; // Формування відповіді сервера
            // Тут можна додати код для збереження IP-адреси та порту клієнта у серверній базі даних або списку

            sendData = response.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientIP, clientPort);
            serverSocket.send(sendPacket); // Відправлення відповіді клієнту
        }
    }
}

// Клас, який реалізує UDP клієнта
class UDPClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket(); // Створення сокету для клієнта

        InetAddress serverIP = InetAddress.getByName("localhost"); // IP-адреса сервера (локально)
        int serverPort = 9876; // Порт сервера

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        String sentence = "Register me!"; // Повідомлення для реєстрації на сервері
        sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);
        clientSocket.send(sendPacket); // Відправлення повідомлення на сервер

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket); // Очікування отримання відповіді від сервера

        String serverResponse = new String(receivePacket.getData()); // Отримання відповіді від сервера
        System.out.println("Response from server:\n" + serverResponse);

        clientSocket.close(); // Закриття сокету клієнта
    }
}