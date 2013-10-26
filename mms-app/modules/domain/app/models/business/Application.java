package models.domain.business;

import java.util.Set;

import javax.persistence.*;

import models.domain.AuditedModel;
import models.domain.Datasource;

/**
 * User: markmo
 * Date: 22/04/13
 * Time: 3:31 PM
 */
@Entity
public class Application extends AuditedModel {

    @Id
    @GeneratedValue
    @Column(name = "application_id")
    private Long id;

    @Column(name = "application_name")
    private String name;

    @Column(length = 8000)
    private String description;

    @Column(length = 8000)
    private String knownIssues;

    @Column(length = 8000)
    private String pendingChanges;

    @ManyToOne
    @JoinColumn(name = "datasource_id")
    private Datasource datasource;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "data_platform_id")
    private DataPlatform dataPlatform;

    @ManyToMany
    @JoinTable(
            name = "application_languages",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<ProgrammingLanguage> languages;

    @ManyToMany
    @JoinTable(
            name = "application_client_tech",
            joinColumns = @JoinColumn(name = "application_id"),
            inverseJoinColumns = @JoinColumn(name = "client_tech_id")
    )
    private Set<ClientTechnology> clientTechnologies;

    @ManyToOne
    @JoinColumn(name = "os_id")
    private OperatingSystem os;

    @ManyToOne
    @JoinColumn(name = "hardware_platform_id")
    private HardwarePlatform hardwarePlatform;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pk.application")
    private Set<ApplicationUserGroup> userGroups;

    // Assessment

    @Enumerated(EnumType.STRING)
    private BusinessCriticality businessCriticality;

    @Enumerated(EnumType.STRING)
    private Complexity complexity;

    @Enumerated(EnumType.STRING)
    private LifeExpectancy lifeExpectancy;

    @Enumerated(EnumType.STRING)
    private UsageVariance usageVariance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKnownIssues() {
        return knownIssues;
    }

    public void setKnownIssues(String knownIssues) {
        this.knownIssues = knownIssues;
    }

    public String getPendingChanges() {
        return pendingChanges;
    }

    public void setPendingChanges(String pendingChanges) {
        this.pendingChanges = pendingChanges;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public DataPlatform getDataPlatform() {
        return dataPlatform;
    }

    public void setDataPlatform(DataPlatform dataPlatform) {
        this.dataPlatform = dataPlatform;
    }

    public Set<ProgrammingLanguage> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<ProgrammingLanguage> languages) {
        this.languages = languages;
    }

    public Set<ClientTechnology> getClientTechnologies() {
        return clientTechnologies;
    }

    public void setClientTechnologies(Set<ClientTechnology> clientTechnologies) {
        this.clientTechnologies = clientTechnologies;
    }

    public OperatingSystem getOs() {
        return os;
    }

    public void setOs(OperatingSystem os) {
        this.os = os;
    }

    public HardwarePlatform getHardwarePlatform() {
        return hardwarePlatform;
    }

    public void setHardwarePlatform(HardwarePlatform hardwarePlatform) {
        this.hardwarePlatform = hardwarePlatform;
    }

    public Set<ApplicationUserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<ApplicationUserGroup> userGroup) {
        this.userGroups = userGroup;
    }

    public BusinessCriticality getBusinessCriticality() {
        return businessCriticality;
    }

    public void setBusinessCriticality(BusinessCriticality businessCriticality) {
        this.businessCriticality = businessCriticality;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public LifeExpectancy getLifeExpectancy() {
        return lifeExpectancy;
    }

    public void setLifeExpectancy(LifeExpectancy lifeExpectancy) {
        this.lifeExpectancy = lifeExpectancy;
    }

    public UsageVariance getUsageVariance() {
        return usageVariance;
    }

    public void setUsageVariance(UsageVariance usageVariance) {
        this.usageVariance = usageVariance;
    }

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Application that = (Application) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
