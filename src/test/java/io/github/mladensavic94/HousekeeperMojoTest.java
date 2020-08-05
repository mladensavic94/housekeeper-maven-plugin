package io.github.mladensavic94;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import java.io.File;

public class HousekeeperMojoTest extends AbstractMojoTestCase {

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMojoGoal() throws Exception {
        File testPom = new File(getBasedir(), "src/test/resources/test-pom.xml");
        assertNotNull(testPom);
        assertTrue(testPom.exists());
        HousekeeperMojo mojo = (HousekeeperMojo) lookupMojo("swipe", testPom);
        assertNotNull(mojo);
//        mojo.execute();
    }
}
