/**
 * 
 */
package unittests;


import org.junit.jupiter.api.Test;
import java.awt.Color;

import renderer.ImageWriter;

/**
 * @author avigail and shira
 *
 */
class ImageWriterTest {

	/**
	 * Test method for {@link renderer.ImageWriter#ImageWriter(java.lang.String, int, int)}.
	 */
	@Test
	public void testImageWriter() {
		var writer = new ImageWriter("firstImage", 800, 500);
		for (int i = 0; i < 500; i++) {
			for (int j = 0; j < 800; j++) {
				if (i % 50 == 0 || j % 50 == 0 || i == 799 || j == 499)
					writer.writePixel(j, i, new primitives.Color(Color.black));
				else
					writer.writePixel(j, i, new primitives.Color(Color.blue));
			}
		}
		writer.writeToImage();
	}
}
