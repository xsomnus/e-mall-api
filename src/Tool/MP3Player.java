package Tool;

import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class MP3Player {
	   private String filename;
	   private Player player;
   public MP3Player(String filename) {
       this.filename = filename;
   }

   public void play() {
       try {
           BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(filename));
           player = new Player(buffer);
           player.play();
//           Thread t=new Thread();
//           t.sleep(10);
//           player.close();
       } catch (Exception e) {
           System.out.println(e);
       }

   }

   public static void main(String[] args) {
       MP3Player mp3 = new MP3Player("123.mp3");
       mp3.play();
   }
}
