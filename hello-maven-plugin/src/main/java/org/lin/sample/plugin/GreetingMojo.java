package org.lin.sample.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Created by lys on 2019/1/23.
 *
 * @author lys
 * @version 3.0.0-SNAPSHOT
 * @since 3.0.0-SNAPSHOT
 */

@Mojo( name = "sayhi")
public class GreetingMojo extends AbstractMojo {
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info( "Hello, world." );
	}
}
