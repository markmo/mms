package models.domain.business;

import javax.persistence.*;

/**
 * User: markmo
 * Date: 23/04/13
 * Time: 11:17 AM
 */
@Entity
public class ApplicationUserGroup {

    @EmbeddedId
    private ApplicationUserGroupPK pk;

    @ManyToOne
    @JoinColumn(name = "alias_id")
    private Alias alias;

    public ApplicationUserGroupPK getPk() {
        return pk;
    }

    public void setPk(ApplicationUserGroupPK pk) {
        this.pk = pk;
    }

    public Alias getAlias() {
        return alias;
    }

    public void setAlias(Alias alias) {
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ApplicationUserGroup that = (ApplicationUserGroup) o;

        if (pk != null ? !pk.equals(that.pk) : that.pk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
