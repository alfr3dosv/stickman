package stickman.game.key;

import stickman.core.entity.*;

public class Key extends Entity {
    Image defaultImage = new Image('K');

    public Key() {
        super();
        setImage(defaultImage);
    }

    public Key(Image i) {
        super();
        setImage(i);
    }
}
