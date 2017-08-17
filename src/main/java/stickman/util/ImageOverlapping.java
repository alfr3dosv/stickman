package stickman.util;

import stickman.entity.*;

import java.util.function.Predicate;

public class ImageOverlapping {
    public static boolean isOverlapping(Entity e, Entity e2) {
        return isHardOverlapping(e, e2);
    }

    public static boolean isSoftOverlapping(Entity e, Entity e2) {
        boolean x = (e.position.x + e.img.size.x) > (e2.position.x + e2.img.size.x);
        boolean y = (e.position.y + e.img.size.x) > (e2.position.y + e2.img.size.y);
        return x && y;
    }


    public static boolean isHardOverlapping(Entity e, Entity e2) {
        char[][] image = e.img.getChars();
        // Crop a image from second entity in the position of the first entity
        // Image.cut() will return a empty char matrix if there are not any ovelappig
        ImageDrawer imageCut = new ImageDrawer();
        imageCut.setOutput(e.img);
        char[][] imageCrop = imageCut.cut(e.img.size, e.position).getChars();
        // Compare chars in the same position, at least one need to be empty ' '
        // otherwise is a hard overlap and return true
        for(int y = 0; y < e.img.size.y; y++)
            for(int x = 0; x < e.img.size.x; x++)
                if(!oneIsEmpty(image[y][x], imageCrop[y][x]))
                    return true;
        return false;
    }

    private static boolean oneIsEmpty(char c1, char c2) {
        return Character.isSpaceChar(c1) || Character.isSpaceChar(c2);
    }
}
