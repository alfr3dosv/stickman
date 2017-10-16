package stickman.core.entity;

import static org.junit.Assert.*;
import org.junit.*;

public class ImageTest {
    @Test public void testBiggerSizeThanSource() {
        char[][] matrix3x3 = {  // source
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '}
        };
        char[][] matrix4x4 = { // expected
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
                {' ', ' ', ' ', ' '},
        };
        Image imageBiggerThanSource = new Image(new Size(4,4), matrix3x3);
        Image imageFromChars = new Image(matrix4x4);
        assertEquals(imageFromChars.toString(), imageBiggerThanSource.toString());
    }
}
