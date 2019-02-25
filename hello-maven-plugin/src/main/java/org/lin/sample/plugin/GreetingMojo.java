package org.lin.sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Created by lys on 2019/1/23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

@Mojo( name = "sayhi")
public class GreetingMojo extends AbstractMojo {
	/**
	 * The greeting to display
	 * 设置参数
	 */
	@Parameter(property = "sayhi.greeting", defaultValue = "Hello world")
	private String greeting;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info( "Hello, world." +greeting);
		getLog().info("outPut greeting:"+greeting);
	}
}
