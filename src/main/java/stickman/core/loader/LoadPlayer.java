package stickman.core.loader;

import stickman.core.entity.Image;
import stickman.game.player.Player;

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
