package org.lys.image.config;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by lys on 7/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class ImageDrawConfig {
	/**
	 * 基础画布
	 */
	private BufferedImage base;

	/**
	 * 用来画在base 的元素
	 */
	private List<BasicContent> drawComponents;

	public ImageDrawConfig(BufferedImage base) {
		this.base = base;
	}

	public BufferedImage getBase() {
		return base;
	}

	public List<BasicContent> getDrawComponents() {
		return drawComponents;
	}

	public void setDrawComponents(List<BasicContent> drawComponents) {
		this.drawComponents = drawComponents;
	}

	public static void main(String[] args) {
	}
}
