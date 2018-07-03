package org.lys.image;

import org.lys.image.config.ImageDrawConfig;

import java.awt.image.BufferedImage;

/**
 * Created by lys on 7/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public interface DrawHandler {


	BufferedImage draw(ImageDrawConfig config);
}
