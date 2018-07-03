package org.lys.image;

import org.junit.Test;
import org.lys.image.config.BasicContent;
import org.lys.image.config.ImageDrawConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lys on 7/3/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ImageTests {


	@Test
	public void test() throws IOException {
		final BufferedImage base = new BufferedImage(1400, 1400,
				BufferedImage.TYPE_INT_RGB);
		ImageDrawConfig imageDrawConfig = new ImageDrawConfig(base);
		BufferedImage bf1 = ImageIO.read(new File("D:\\qr\\11.jpg"));
		BufferedImage bf2 = ImageIO.read(new File("D:\\qr\\14.jpg"));
		BasicContent<BufferedImage> component1 =
				new BasicContent<>(bf1, 100, 800, bf1.getWidth(), bf1.getHeight());

		BasicContent<BufferedImage> component2 =
				new BasicContent<>(bf2, 100, 20, bf1.getWidth(), bf1.getHeight());
		BasicContent<String> component3 =
				new BasicContent<>("我的天",800,800,0,0);
		List<BasicContent> components = new ArrayList<>(3);
		components.add(component1);
		components.add(component2);
		components.add(component3);
		imageDrawConfig.setDrawComponents(components);

		DrawHandler handler = new AbstractDefaultDrawHandler();
		BufferedImage draw = handler.draw(imageDrawConfig);

		ImageIO.write(draw,"jpg",new File("D:\\qr\\iii.jpg"));
	}
}
