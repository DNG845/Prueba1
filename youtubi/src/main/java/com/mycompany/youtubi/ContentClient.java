package com.mycompany.youtubi;

import uk.co.caprica.vlcj.factory.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

public class ContentClient extends JFrame {
    private final JTextArea textArea;
    private final JLabel imageLabel;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private boolean isPaused = false;

    public static void main(String[] args) {
        new NativeDiscovery().discover(); // Descubre las bibliotecas nativas de VLC
        SwingUtilities.invokeLater(() -> {
            ContentClient client = new ContentClient();
            client.setVisible(true);
        });
    }

    public ContentClient() {
        // Configurar modo nocturno
        setTitle("Cliente de Contenido");
        setSize(1280, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Colores para modo nocturno
        Color backgroundColor = Color.DARK_GRAY;
        Color foregroundColor = Color.WHITE;
        Color buttonColor = new Color(60, 63, 65);
        Color buttonHoverColor = new Color(75, 78, 80);
        Color buttonPressedColor = new Color(50, 53, 55);
        Color buttonTextColor = Color.WHITE;

        // Crear botones principales
        JButton musicButton = createButton("Música", buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton videoButton = createButton("Videos", buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton documentButton = createButton("Documentos", buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);
        JButton imageButton = createButton("Imágenes", buttonColor, buttonHoverColor, buttonPressedColor, buttonTextColor);

        // Configurar eventos de botones
        musicButton.addActionListener(e -> openMusicWindow());
        videoButton.addActionListener(e -> openVideoWindow());
        documentButton.addActionListener(e -> openDocumentWindow());
        imageButton.addActionListener(e -> openImageWindow());

        // Crear el layout y añadir los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1, 10, 10));
        panel.setBackground(backgroundColor);
        panel.add(musicButton);
        panel.add(videoButton);
        panel.add(documentButton);
        panel.add(imageButton);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(backgroundColor);
        textArea.setForeground(foregroundColor);
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));
        textArea.setBorder(new EmptyBorder(20, 20, 20, 20)); // Añadir márgenes

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(backgroundColor);
        scrollPane.setBorder(new LineBorder(Color.BLACK));

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Layout principal
        setLayout(new BorderLayout());
        add(panel, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);
        add(imageLabel, BorderLayout.EAST);

        // Inicializar VLCJ
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        add(mediaPlayerComponent, BorderLayout.NORTH);
    }

    private JButton createButton(String text, Color background, Color hoverBackground, Color pressedBackground, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(Color.BLACK));
        button.setOpaque(true);
        button.setBorder(new EmptyBorder(20, 40, 20, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(background);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(pressedBackground);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverBackground);
            }
        });

        return button;
    }

    private void openMusicWindow() {
        JFrame musicFrame = new JFrame("Música");
        musicFrame.setSize(600, 400);
        musicFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        musicFrame.setLocationRelativeTo(null);
        musicFrame.getContentPane().setBackground(Color.DARK_GRAY);

        // Crear botones de música
        JButton mp3Button1 = createButton("Reproducir MP3-1", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton mp3Button2 = createButton("Reproducir MP3-2", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        // Configurar eventos de botones
        mp3Button1.addActionListener(e -> openContentWindow("192.168.1.34", 8000, "Avispas.mp3", "src/Imagenes/Avispas.jpg"));
        mp3Button2.addActionListener(e -> openContentWindow("192.168.1.34", 8000, "My-or.mp3", "src/Imagenes/My-or.jpg"));

        // Crear el layout y añadir los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(mp3Button1);
        panel.add(mp3Button2);

        musicFrame.add(panel);
        musicFrame.setVisible(true);
    }

    private void openVideoWindow() {
        JFrame videoFrame = new JFrame("Videos");
        videoFrame.setSize(600, 400);
        videoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        videoFrame.setLocationRelativeTo(null);
        videoFrame.getContentPane().setBackground(Color.DARK_GRAY);

        // Crear botones de videos
        JButton mp4Button1 = createButton("Reproducir MP4-1", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton mp4Button2 = createButton("Reproducir MP4-2", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        // Configurar eventos de botones
        mp4Button1.addActionListener(e -> openContentWindow("192.168.1.34", 8000, "java.mp4", null));
        mp4Button2.addActionListener(e -> openContentWindow("192.168.1.34", 8000, "JS.mp4", null));

        // Crear el layout y añadir los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(mp4Button1);
        panel.add(mp4Button2);

        videoFrame.add(panel);
        videoFrame.setVisible(true);
    }

    private void openDocumentWindow() {
        JFrame documentFrame = new JFrame("Documentos");
        documentFrame.setSize(600, 400);
        documentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        documentFrame.setLocationRelativeTo(null);
        documentFrame.getContentPane().setBackground(Color.DARK_GRAY);

        // Crear botones de documentos
        JButton txtButton1 = createButton("Mostrar TXT-1", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton txtButton2 = createButton("Mostrar TXT-2", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        // Configurar eventos de botones
        txtButton1.addActionListener(e -> openTextWindow("192.168.1.34", 8000, "dato.txt"));
        txtButton2.addActionListener(e -> openTextWindow("192.168.1.34", 8000, "dato2.txt"));

        // Crear el layout y añadir los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(txtButton1);
        panel.add(txtButton2);

        documentFrame.add(panel);
        documentFrame.setVisible(true);
    }

    private void openTextWindow(String ipAddress, int port, String fileName) {
        JFrame textFrame = new JFrame("Texto");
        textFrame.setSize(600, 400);
        textFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        textFrame.setLocationRelativeTo(null);
        textFrame.getContentPane().setBackground(Color.DARK_GRAY);

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Serif", Font.PLAIN, 16));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);
        scrollPane.setBorder(null);

        JButton closeButton = createButton("Cerrar", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        closeButton.addActionListener(e -> textFrame.dispose());

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.add(closeButton);

        textFrame.setLayout(new BorderLayout());
        textFrame.add(scrollPane, BorderLayout.CENTER);
        textFrame.add(controlPanel, BorderLayout.SOUTH);

        requestTextFile(ipAddress, port, fileName, textArea);

        textFrame.setVisible(true);
    }

    private void openImageWindow() {
        JFrame imageFrame = new JFrame("Imágenes");
        imageFrame.setSize(600, 400);
        imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        imageFrame.setLocationRelativeTo(null);
        imageFrame.getContentPane().setBackground(Color.DARK_GRAY);

        // Crear botones de imágenes
        JButton uploadButton = createButton("Subir", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton editButton = createButton("Editar", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton downloadButton = createButton("Descargar", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        // Configurar eventos de botones
        uploadButton.addActionListener(e -> uploadImage());
        editButton.addActionListener(e -> editImage());
        downloadButton.addActionListener(e -> downloadImage());

        // Crear el layout y añadir los elementos
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3, 10, 10));
        panel.setBackground(Color.DARK_GRAY);
        panel.add(uploadButton);
        panel.add(editButton);
        panel.add(downloadButton);

        imageFrame.add(panel);
        imageFrame.setVisible(true);
    }

    private void uploadImage() {
        // Implementar lógica para subir imágenes
        JOptionPane.showMessageDialog(this, "Función de subir imágenes aún no implementada.", "Subir Imagen", JOptionPane.INFORMATION_MESSAGE);
    }

    private void editImage() {
        // Implementar lógica para editar imágenes
        JOptionPane.showMessageDialog(this, "Función de editar imágenes aún no implementada.", "Editar Imagen", JOptionPane.INFORMATION_MESSAGE);
    }

    private void downloadImage() {
        // Implementar lógica para descargar imágenes
        JOptionPane.showMessageDialog(this, "Función de descargar imágenes aún no implementada.", "Descargar Imagen", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openContentWindow(String ipAddress, int port, String fileName, String imagePath) {
        JFrame contentFrame = new JFrame("Contenido");
        contentFrame.setSize(1280, 720);
        contentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        contentFrame.setLocationRelativeTo(null);

        JButton backButton = createButton("Regresar", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);
        JButton localPauseButton = createButton("Pausar/Reanudar", new Color(60, 63, 65), new Color(75, 78, 80), new Color(50, 53, 55), Color.WHITE);

        EmbeddedMediaPlayerComponent localMediaPlayerComponent = new EmbeddedMediaPlayerComponent();

        backButton.addActionListener(e -> {
            localMediaPlayerComponent.mediaPlayer().controls().stop();
            contentFrame.dispose();
        });

        localPauseButton.addActionListener(e -> pauseLocalMedia(localMediaPlayerComponent, localPauseButton));

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.add(backButton);
        controlPanel.add(localPauseButton);

        contentFrame.setLayout(new BorderLayout());
        contentFrame.add(controlPanel, BorderLayout.NORTH);
        contentFrame.add(localMediaPlayerComponent, BorderLayout.CENTER);

        if (imagePath != null) {
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            ImageIcon imageIcon = new ImageIcon(imagePath);
            imageLabel.setIcon(imageIcon);
            contentFrame.add(imageLabel, BorderLayout.EAST);
        }

        contentFrame.setVisible(true);
        playMedia(localMediaPlayerComponent, ipAddress, port, fileName);
    }

    private void playMedia(EmbeddedMediaPlayerComponent mediaPlayerComponent, String ipAddress, int port, String fileName) {
        this.mediaPlayerComponent.mediaPlayer().controls().stop();

        try {
            Socket socket = new Socket(ipAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET /" + fileName);

            File tempFile = File.createTempFile("temp", null);
            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();

            mediaPlayerComponent.mediaPlayer().media().play(tempFile.getAbsolutePath());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestTextFile(String ipAddress, int port, String fileName, JTextArea textArea) {
        try {
            Socket socket = new Socket(ipAddress, port);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET /" + fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            textArea.setText(content.toString());

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void pauseMedia() {
        if (isPaused) {
            mediaPlayerComponent.mediaPlayer().controls().play();
        } else {
            mediaPlayerComponent.mediaPlayer().controls().pause();
        }
        isPaused = !isPaused;
    }

    private void pauseLocalMedia(EmbeddedMediaPlayerComponent mediaPlayerComponent, JButton pauseButton) {
        if (isPaused) {
            mediaPlayerComponent.mediaPlayer().controls().play();
            pauseButton.setText("Pausar");
        } else {
            mediaPlayerComponent.mediaPlayer().controls().pause();
            pauseButton.setText("Reanudar");
        }
        isPaused = !isPaused;
    }
}













