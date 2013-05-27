package mms.common.models.business;

import java.util.List;
import javax.persistence.*;

import play.db.jpa.JPA;

/**
 * User: markmo
 * Date: 3/05/13
 * Time: 10:56 AM
 */
@Entity
public class BusinessTermAssociation {

    @Id
    @GeneratedValue
    @Column(name = "term_association_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_term_id")
    private BusinessTerm subject;

    @Column(length = 1000)
    private String predicate;

    @ManyToOne
    @JoinColumn(name = "object_term_id")
    private BusinessTerm object;

    @Enumerated(EnumType.STRING)
    private SourceType source;

    @SuppressWarnings("unchecked")
    public static List<BusinessTermAssociation> findBySubjectId(Long id) {
        return JPA.em().createQuery(
                "select a from BusinessTermAssociation a where a.subject.id = ?1"
        )
                .setParameter(1, id)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<BusinessTermAssociation> findBySubjectIdFromSource(Long id, SourceType source) {
        return JPA.em().createQuery(
                "select a from BusinessTermAssociation a " +
                "where a.subject.id = ?1 and a.source = ?2"
        )
                .setParameter(1, id)
                .setParameter(2, source)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<BusinessTermAssociation> findBySubjectIdAndObjectId(
            Long subjectId, Long objectId) {
        return JPA.em().createQuery(
                "select a from BusinessTermAssociation a " +
                "where a.subject.id = ?1 and a.object.id = ?2"
        )
                .setParameter(1, subjectId)
                .setParameter(2, objectId)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    public static List<BusinessTermAssociation> findBySubjectIdAndObjectIdFromSource(
            Long subjectId, Long objectId, SourceType source) {
        return JPA.em().createQuery(
                "select a from BusinessTermAssociation a " +
                "where a.subject.id = ?1 and a.object.id = ?2 and a.source = ?3"
        )
                .setParameter(1, subjectId)
                .setParameter(2, objectId)
                .setParameter(3, source)
                .getResultList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessTerm getSubject() {
        return subject;
    }

    public void setSubject(BusinessTerm subject) {
        this.subject = subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public void setPredicate(String predicate) {
        this.predicate = predicate;
    }

    public BusinessTerm getObject() {
        return object;
    }

    public void setObject(BusinessTerm object) {
        this.object = object;
    }

    public SourceType getSource() {
        return source;
    }

    public void setSource(SourceType source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", subject, predicate, object);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BusinessTermAssociation that = (BusinessTermAssociation) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
