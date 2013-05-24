package mms.common.models;

/**
 * User: markmo
 * Date: 18/05/13
 * Time: 5:30 PM
 */
public enum AnalysisModelLifecycleStage {
    DEVELOPMENT("Development"),
    TEST("Test"),
    STAGE("Stage"),
    PRODUCTION("Production");

    private String name;

    AnalysisModelLifecycleStage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
