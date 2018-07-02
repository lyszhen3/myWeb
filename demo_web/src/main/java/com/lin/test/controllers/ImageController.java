package com.lin.test.controllers;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.lys.Pic;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by lys on 7/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
@Controller
public class ImageController {

	/**
	 * 输出画出来的的image
	 * @param response
	 */
	@RequestMapping("drawImage")
	public void writeImage(HttpServletResponse response) {


		response.setContentType("image/jpg");
		try {
		BufferedImage b = ImageIO.read(new File("D:\\qr\\11.jpg"));
		BufferedImage c = ImageIO.read(new File("D:\\qr\\13.png"));

			Pic.draw(b,200,200,c,500,500,response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
