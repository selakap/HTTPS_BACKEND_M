package backend;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MyBackend {

    public static void main(String[] args) {
        try {
            // Get the port to listen on
            //curl https://localhost:7001 -k
            // Create a ServerSocket to listen on that port.
            System.setProperty("javax.net.ssl.keyStore", "/Users/selakapiumal/Desktop/ssltest/wso2carbon-new.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "wso2carbon");
            ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
            ServerSocket ss = ssf.createServerSocket(7001);
            // Now enter an infinite loop, waiting for & handling connections.
            for (;;) {
                // Wait for a client to connect. The method will block;
                // when it returns the socket will be connected to the client
                Socket client = ss.accept();

                // Get input and output streams to talk to the client
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                System.out.println("https test");


                char[] buf = new char[10];
                StringBuilder outt = new StringBuilder();
                while (true) {
                    try{
                        int read = in.read(buf);
                        outt.append(buf, 0, read);
                        //if (read < 100)
                        break;
                    }

                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                PrintWriter out = new PrintWriter(client.getOutputStream());

                // Start sending our reply, using the HTTP 1.1 protocol
                out.print("HTTP/1.1 404 Not Found\r\n");
                out.print("Content-Type: application/vnd.error+json; charset=UTF-8\r\n");
                out.print("Content-Length: 82646\r\n");
                out.print("X-Frame-Options: SAMEORIGIN\r\n");
                out.print("X-Forwarded-Proto: https\r\n");
                out.print("X-Forwarded-For: 137.184.108.232, 137.184.108.232\r\n");
                out.print("X-Forwarded-Port: 443\r\n");
                out.print("X-Real-IP: 137.184.108.232, 137.184.108.232\r\n");
                out.print("X-Amzn-Trace-Id: Root=1-631e1d61-665a56dc729b001f3dea652e\r\n");
                out.print("Connection: Close\r\n");
                out.print("Date: Sun, 11 Sep 2022 17:39:46 GMT\r\n");
                out.print("\r\n"); // End of headers

                out.print("\r\n");
                String body = "{" +
                        "                            \"message\":\"The requested resource is not available.\"," +
                        "                             \"_links\":{" +
                        "                                            \"about\":{" +
                        "                                                    \"href\": \"doku/sap/public/bc/ur/Login/assets/corbu/sap_logo.png\"" +
                        "                                            }," +
                        "" +
                        "                                            \"help\":{" +
                        "                                                    \"href\": \"http://errors.api.aligntech.com/wso2/404\"" +
                        "                                            }" +
                        "" +
                        "                                    }" +
                        "" +
                        "                }";
                out.print(body + "\r\n");

                out.close(); // Flush and close the output stream
                in.close(); // Close the input stream
                client.close(); // Close the socket itself
            } // Now loop again, waiting for the next connection
        }
        // If anything goes wrong, print an error message
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java HttpMirror <port>");
        }    }
}