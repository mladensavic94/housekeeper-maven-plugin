package io.github.mladensavic94;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

@Mojo(name = "swipe")
public class Housekeeper extends AbstractMojo {


    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Plugin init");
    }
}
