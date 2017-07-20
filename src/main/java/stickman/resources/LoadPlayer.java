package stickman.resources;

import stickman.player.Player;
import stickman.entity.*;

public class LoadPlayer {
    static Player player;
    private static final String imagePath = "/player/player.txt";

    public static Player load() {
        if(player == null) {
            Image imageOfPlayer = ReadFile.loadImage(imagePath);
            player = new Player(imageOfPlayer);
        }
        return player;
    }
}
