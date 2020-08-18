package io.github.mladensavic94;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {

    private String artifactId;
    private String version;
    private String scope;
    private List<TreeNode> children;
    private LocalDateTime lastVisited;

    public TreeNode(String artifactId, String version, String scope) {
        this.artifactId = artifactId;
        this.version = version;
        this.scope = scope;
        this.lastVisited = LocalDateTime.now();
        children = new ArrayList<>();

    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public LocalDateTime getLastVisited() {
        return lastVisited;
    }

    public void setLastVisited(LocalDateTime lastVisited) {
        this.lastVisited = lastVisited;
    }
}
