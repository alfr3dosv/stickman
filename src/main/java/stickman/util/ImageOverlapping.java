package stickman.util;

import stickman.entity.*;

public class ImageOverlapping {
    public static Point whereIsOverlapping(Entity e, Entity e2) {
        return isHardOverlapping(e, e2);
    }

    public static Point whereIsOverlapping(Image background, Entity e, char against) {
        Point where = new Point(-1, -1);
        Entity context = new Template(background);
        ImageCut cutter = new ImageCut();
        char[][] imageCrop = cutter.cut(e, context).getImage().getChars();
        for(int y = 0; y < e.img.size.y-1; y++)
            for(int x = 0; x < e.img.size.x-1; x++)
                if(imageCrop[y][x] == against)
                    where = new Point(x,y);
        return where;
    }
    private static boolean isSoftOverlapping(Entity e, Entity e2) {
        boolean x = (e.position.x + e.img.size.x) > (e2.position.x + e2.img.size.x);
        boolean y = (e.position.y + e.img.size.x) > (e2.position.y + e2.img.size.y);
        return x && y;
    }


    private static Point isHardOverlapping(Entity e, Entity e2) {
        Point where = new Point(-1,-1);
        char[][] image = e.img.getChars();
        // Crop a image from second entity in the position of the first entity
        // Image.cut() will return a empty char matrix if there are not any overlapping
        ImageCut cutter = new ImageCut();
        char[][] imageCrop = cutter.cut(e, e2).getImage().getChars();
        // Compare chars in the same position, at least one need to be empty ' '
        // otherwise is a hard overlap and return the point
        for(int y = 0; y < e.img.size.y-1; y++)
            for(int x = 0; x < e.img.size.x-1; x++)
                if(!oneIsEmpty(image[y][x], imageCrop[y][x]))
                    where = new Point(x,y);
        return where;
    }

    private static boolean oneIsEmpty(char c1, char c2) {
        return Character.isWhitespace(c1) || Character.isWhitespace(c2);
    }
}
