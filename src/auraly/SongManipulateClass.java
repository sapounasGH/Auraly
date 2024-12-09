package auraly;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

/**
 *
 * @author Χρήστος Σαπουνας
 */
public class SongManipulateClass {
    public AdvancedPlayer playMP3;
    public Thread playerThread;
    public Player player;
    File tempFile = null;
    boolean needofreplay = false;
    public SongManipulateClass() {}
    
    public void play(InputStream inputStream) throws FileNotFoundException, InterruptedException{
            try {
                tempFile = File.createTempFile("audio_", ".mp3");
            } catch (IOException ex) {
                Logger.getLogger(SongManipulateClass.class.getName()).log(Level.SEVERE, null, ex);
            }

        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tempFile))) {
            byte[] buffer = new byte[31457280];  
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
        }   catch (FileNotFoundException ex) {
                Logger.getLogger(SongManipulateClass.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SongManipulateClass.class.getName()).log(Level.SEVERE, null, ex);
            }
            playMP3();
        }
    
    private void playMP3() {
        stop();
        playerThread = new Thread(() -> {
            try (InputStream fileInputStream = new FileInputStream(tempFile)) {
                player = new Player(fileInputStream);
                player.play();
            } catch (Exception ex) {
                Logger.getLogger(SongManipulateClass.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        playerThread.start();
        
    }
    
    public void stop() {
        if (player != null) {
            player.close();
            player = null;
        }
        if (playerThread != null && playerThread.isAlive()) {
            playerThread.interrupt();
        }
    }
    public void replay() {
        if (tempFile != null) {
            playMP3();
        }else{System.out.println("temp is null");}
    }

}
    
