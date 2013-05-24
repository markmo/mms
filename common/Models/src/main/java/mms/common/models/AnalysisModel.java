package mms.common.models;

import java.util.Date;

/**
 * User: markmo
 * Date: 18/05/13
 * Time: 5:15 PM
 */
public class AnalysisModel {

    private Long id;

    private String name;

    private String description;

    private Algorithm algorithm;

    private Date lastRun;

    private Date lastTrained;

    private Date publishedDate;

    private Sandbox sandbox;

    private String modelVersion;

    private AnalysisModelVersionStatus versionStatus;

    private AnalysisModelLifecycleStage lifecycleStage;

    private String notes;
}
