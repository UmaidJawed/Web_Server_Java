import java.io.*;
import java.net.Socket;
import java.net.ServerSocket;

class Main {

  public static void main(String[] args) throws Exception {

    // Start receiving messages - ready to receive messages!

    try (ServerSocket serverSocket = new ServerSocket(8080)) {
      System.out.println("Server started\n Listening for messages.");

      while (true) {
        // Handle a new incoming message
        try (Socket client = serverSocket.accept()) {
          // client <-- messages queued up in it!!
          System.out.println("Debug got new message " + client.toString());

          // Read the request - listen to the message
          InputStreamReader isr = new InputStreamReader(client.getInputStream());

          BufferedReader br = new BufferedReader(isr);

          // Read the first request from the client
          StringBuilder request = new StringBuilder();
          String line;// Temp variable called line that holds one line at a time of message.
          line = br.readLine();
          while (!line.isBlank()) {
            request.append(line + "\r\n");
            line = br.readLine();

          }

          // System.out.println("------REQUEST------");
          // System.out.println(request.toString());
          // Decide how we'd like to respond

          // Get the first line of the request
          String firstLine = request.toString().split("\n")[0];
          // Get the second thing "resource" from the first line (separated by spaces)
          String resource = firstLine.toString().split(" ")[1];
          // Compare the "resource" to our list of things
          // System.out.println(resource);
          if (resource.equals("/warrior")) {
            // System.out.println(resource.equals("/jess"));
            FileInputStream image = new FileInputStream("warrior.jpg");
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write(image.readAllBytes());
            clientOutput.flush();

          }

          else if (resource.equals("/hello")) {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("Hello World".getBytes());
            clientOutput.flush();

          }

          else {
            OutputStream clientOutput = client.getOutputStream();
            clientOutput.write("HTTP/1.1 200 OK\r\n".getBytes());
            clientOutput.write("\r\n".getBytes());
            clientOutput.write("What ya looking for ".getBytes());
            clientOutput.flush();

          }
          // Send back the appropriate thing based on the resource

          // Just send back a simple "Hello World"

          // Send back an image?

          // Load the image from the file system
          // System.out.println(image.toString());
          // Turn the image into bytes
          // Set the contentType?

          // Change response based on route?

          // Send a response - send our reply

          // Get ready for the next message
          client.close();
        }
      }
    }

  }
}
