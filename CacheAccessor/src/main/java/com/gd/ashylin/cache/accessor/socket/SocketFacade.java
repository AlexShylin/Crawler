package com.gd.ashylin.cache.accessor.socket;

import java.io.*;
import java.net.Socket;

/**
 * @author Alexander Shylin
 */
public class SocketFacade {

    private BufferedReader in;
    private PrintWriter out;
    private Socket socket;

    public SocketFacade(Socket socket) {
        this.socket = socket;
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getData() {
        String data;
        try {
            while ((data = in.readLine()) != null) {
                break;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return data;
    }

    public void sendData(String data) {
        if (data == null || data.length() == 0) {
            return;
        }
        out.println(data);
    }

    public void close() {
        out.close();
        try {
            in.close();
            socket.close();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
