package io.github.mladensavic94;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.DefaultProjectBuildingRequest;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;
import org.apache.maven.shared.dependency.graph.DependencyNode;

import java.util.List;

@Mojo(name = "swipe", threadSafe = true )
public class HousekeeperMojo extends AbstractMojo {

    @Parameter( defaultValue = "${project}", readonly = true, property = "project")
    private MavenProject project;
    @Parameter( defaultValue = "${session}", readonly = true )
    private MavenSession session;
    @Parameter( defaultValue = "${reactorProjects}", readonly = true, required = true )
    private List<MavenProject> reactorProjects;
    @Component(hint = "default")
    private DependencyGraphBuilder dependencyGraphBuilder;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Plugin init");
        try {
            ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
            buildingRequest.setProject(project);
            DependencyNode root = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, null, reactorProjects);
            getLog().info(generateStringOutput(root.getArtifact()));
            root.getChildren().forEach(node -> getLog().info(generateStringOutput(node.getArtifact())));
        } catch (DependencyGraphBuilderException e) {
            getLog().error("Cant build dependency tree", e);
        }
    }

    public String generateStringOutput(Artifact artifact){
        String line = "Artifact: %s, version: %s, scope: %s";
        return String.format(line, artifact.getArtifactId(), artifact.getVersion(), artifact.getScope());
    }
}
