package stickman.util;

import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;
import stickman.core.entity.Image;
import stickman.core.entity.Point;
import stickman.core.loader.Loader;

import java.util.ArrayList;

public class ImageUtilTest {

    @Test public void testRawElements() {
        Image image = (Image) Loader.lookup("test/util/image_1");
        char value = ImageUtil.from(image).raw().getElementAt(new Point(2,1)).value();

        assertEquals('\\', value);
    }

    @Test public void testGetElements() {
        Image image = (Image) Loader.lookup("test/util/image_1");
        List<ImageUtil.Element> elements = new ArrayList<>();
        elements = ImageUtil.from(image).raw().getElements();

        assertEquals(9, elements.size());
    }

    @Test public void testCutImage() {
        Image expected = (Image) Loader.lookup("test/util/image_2");
        Image image = (Image) Loader.lookup("test/util/image_1");

        Image result = ImageUtil.from(image).cut(2,2).where(1,1).getImage();
        assertEquals(expected.toString(), result.toString());
    }

    @Test public void testNegativeCut() {
        String expected = " ";
        Image before = (Image) Loader.lookup("test/util/image_1");
        Image result =
                ImageUtil.from(before).cut(-2,-2).where(-1,-1).getImage();
        assertEquals(expected, result.toString());
    }

    @Test public void testDraw() {
        Image expected = (Image) Loader.lookup("test/util/canvas_after");
        Image before = (Image) Loader.lookup("test/util/canvas_after");
        Image toDraw = (Image) Loader.lookup("test/util/image_1");

        Image result = ImageUtil.from(before).draw(toDraw).where(0,0).getImage();
        assertEquals(expected.toString(), result.toString());
    }

    @Test public void testOverlap() {
        List<Point> expected = new ArrayList<Point>();
        expected.add(new Point(1, 1));
        expected.add(new Point(1, 2));
        Image i = (Image) Loader.lookup("test/util/overlap_1");
        Image i2 = (Image) Loader.lookup("test/util/image_1");

        List<Point> result = ImageUtil.from(i).findOverlaps(i2).getOverlaps();
        assertEquals(expected.size(), result.size());
        for (Point p : result) {
            assertTrue(expected.contains(p));
        }
    }
}
