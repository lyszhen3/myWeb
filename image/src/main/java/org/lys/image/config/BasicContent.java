package org.lys.image.config;

/**
 * Created by lys on 7/2/2018.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */
public class BasicContent<T> {
	/**
	 * x 轴距离
	 */
	private int x;
	/**
	 * y轴距离
	 */
	private int y;

	private T content;

	private int width;

	private int height;

	public BasicContent(T content, int x, int y, int width, int height) {
		this.content = content;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
