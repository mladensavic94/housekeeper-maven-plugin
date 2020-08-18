package io.github.mladensavic94;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.ArtifactRepositoryPolicy;
import org.apache.maven.artifact.repository.MavenArtifactRepository;
import org.apache.maven.artifact.repository.layout.DefaultRepositoryLayout;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.MojoExecution;
import org.apache.maven.plugin.testing.MojoRule;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.File;

import static org.apache.maven.artifact.repository.ArtifactRepositoryPolicy.CHECKSUM_POLICY_IGNORE;
import static org.apache.maven.artifact.repository.ArtifactRepositoryPolicy.UPDATE_POLICY_ALWAYS;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class HousekeeperMojoTest {

    @Rule
    public MojoRule rule = new MojoRule();

    @Rule
    public TestResources resources = new TestResources();


//    @Test
    public void testMojoGoal() throws Exception {
        File testPom = new File("src/test/resources/pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());

        MavenProject project = rule.readMavenProject(testPom.getParentFile());
        MavenSession session = rule.newMavenSession(project);
        ArtifactRepository localRepo = new MavenArtifactRepository("local", "repoID", new DefaultRepositoryLayout(), new ArtifactRepositoryPolicy(true, UPDATE_POLICY_ALWAYS, CHECKSUM_POLICY_IGNORE), new ArtifactRepositoryPolicy(true, UPDATE_POLICY_ALWAYS, CHECKSUM_POLICY_IGNORE));
        session.getRequest().setLocalRepository(localRepo);

        HousekeeperMojo mojo = (HousekeeperMojo) rule.lookupConfiguredMojo(project, "swipe");
        assertNotNull(mojo);
        mojo.execute();
    }
}
