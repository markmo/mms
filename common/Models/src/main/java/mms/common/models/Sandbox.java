package mms.common.models;

import static utils.JPA_Helper.getSingleResult;

import java.io.IOException;
import javax.persistence.Column;
import javax.persistence.*;

import org.codehaus.jackson.JsonNode;
import org.hibernate.envers.Audited;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 21/02/13
 * Time: 5:02 PM
 */
@Entity
@Audited
public class Sandbox extends AuditedModel {

    public Sandbox() {}

    public Sandbox(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue
    @Column(name = "sandbox_id")
    private Long id;

    @Column(name = "sandbox_name")
    @Constraints.Required
    private String name;

    @Column(length = 8000)
    private String description;

    public static Sandbox findByName(String name) {
        return getSingleResult(Sandbox.class,
                JPA.em().createQuery(
                        "select s from Sandbox s where s.name = ?1"
                )
                        .setParameter(1, name)
        );
    }

    public static Sandbox parseSandbox(JsonNode json) throws IOException {
        String name = json.path("name").getTextValue();
        Sandbox existingSandbox = findByName(name);
        Sandbox sandbox = (existingSandbox == null ? new Sandbox() : existingSandbox);
        sandbox.name = name;
        JPA.em().persist(sandbox);
        return sandbox;
    }

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

    @Override
    public String toString() {
        return name + "(" + id + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sandbox sandbox = (Sandbox) o;

        return (id == sandbox.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
