package org.lys.image;

import org.lys.image.config.BasicContent;
import org.lys.image.config.ImageDrawConfig;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 * Created by lys on 7/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class AbstractDefaultDrawHandler implements DrawHandler {
	private ImageDrawConfig config;


	@Override
	public BufferedImage draw(ImageDrawConfig config) {
		this.config = config;
		BufferedImage base = config.getBase();
		Graphics2D g = base.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, base.getWidth(), base.getHeight());
		//校验参数是否符合龟腚
		boolean b = checkParamlegal(config);
		if(!b){
			throw new RuntimeException("画上元素长宽超过画布");
		}
		List<BasicContent> drawComponents = config.getDrawComponents();
		for (BasicContent drawComponent : drawComponents) {
			Object content = drawComponent.getContent();
			if(content.getClass().getName().equals(BufferedImage.class.getName())){
				g.drawImage((BufferedImage) content,drawComponent.getX(),drawComponent.getY(),null);
			}
			if(content.getClass().getName().equals(String.class.getName())){
				g.setColor(Color.black);
				g.setFont(new Font("Serif", Font.PLAIN, 50));
				g.drawString((String)content,drawComponent.getX(),drawComponent.getY());
			}
		}
		g.dispose();

		return base;
	}


	private boolean checkParamlegal(ImageDrawConfig config) {
		//..文字校验不来 看来只能搞搞图片了
		List<BasicContent> drawComponents = config.getDrawComponents();
		BufferedImage base = config.getBase();
		int maxHeight = base.getHeight();
		int maxWidth = base.getWidth();
		for (BasicContent drawComponent : drawComponents) {
			Object content = drawComponent.getContent();
			if (content.getClass().getName().equals(BufferedImage.class.getName())) {
				if((drawComponent.getWidth()+drawComponent.getX()>maxWidth)
						||(drawComponent.getHeight()+drawComponent.getY()>maxHeight)){
					return false;
				}
			}
		}

		return true;
	}
}
