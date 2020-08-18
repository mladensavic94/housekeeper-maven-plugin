package io.github.mladensavic94;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.resolver.filter.ArtifactFilter;
import org.apache.maven.artifact.resolver.filter.ScopeArtifactFilter;
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
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.RemoteRepository;

import java.util.List;

@Mojo(name = "swipe", threadSafe = true)
public class HousekeeperMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, property = "project")
    private MavenProject project;
    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;
    @Parameter(defaultValue = "${reactorProjects}", readonly = true, required = true)
    private List<MavenProject> reactorProjects;
    @Parameter(property = "scope")
    private String scope;
    @Component
    private RepositorySystem repoSystem;
    @Parameter(defaultValue = "${repositorySystemSession}")
    private RepositorySystemSession repoSession;
    @Parameter(defaultValue = "${project.remoteProjectRepositories}")
    private List<ArtifactRepository> remoteRepos;
    @Component(hint = "default")
    private DependencyGraphBuilder dependencyGraphBuilder;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Plugin init");
        try {
            ProjectBuildingRequest buildingRequest = new DefaultProjectBuildingRequest(session.getProjectBuildingRequest());
            buildingRequest.setRepositorySession(repoSession);
            buildingRequest.setRemoteRepositories(remoteRepos);
            buildingRequest.setProject(project);

            DependencyNode root = dependencyGraphBuilder.buildDependencyGraph(buildingRequest, null);
            getLog().info(generateStringOutput(root.getArtifact()));
            root.getChildren().forEach(node -> getLog().info(generateStringOutput(node.getArtifact())));
        } catch (Exception e) {
            getLog().error("Failed to build dependency tree", e);
        }
    }

    public String generateStringOutput(Artifact artifact) {
        String line = "Artifact: %s, version: %s, scope: %s";
        return String.format(line, artifact.getArtifactId(), artifact.getVersion(), artifact.getScope());
    }

    private ArtifactFilter createResolvingArtifactFilter() {
        ArtifactFilter filter;
        if (scope != null) {
            getLog().debug("+ Resolving dependency tree for scope '" + scope + "'");
            filter = new ScopeArtifactFilter(scope);
        } else {
            filter = null;
        }
        return filter;
    }
}
