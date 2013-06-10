package models;

import static controllers.Application.getSingleResult;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;

import play.data.format.Formats;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

@Entity
public class TokenAction {

    public enum Type {

        //@EnumValue("EV")
        EMAIL_VERIFICATION("EMAIL_VERIFICATION"),

        //@EnumValue("PR")
        PASSWORD_RESET("PASSWORD_RESET");

        private String value;

        Type(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private static final long serialVersionUID = 1L;

    /**
     * Verification time frame (until the user clicks on the link in the email)
     * in seconds
     * Defaults to one week
     */
    private final static long VERIFICATION_TIME = 7 * 24 * 3600;

    @Id
    @GeneratedValue
    @Column(name = "token_action_id")
    public Long id;

    @Column(unique = true)
    public String token;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    public User targetUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    public Type type;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date created;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date expires;

    @Transactional(readOnly = true)
    public static TokenAction findByToken(final String token, final Type type) {
        return getSingleResult(TokenAction.class,
                JPA.em().createQuery(
                        "select t from TokenAction t where t.token = ?1 and t.type = ?2"
                )
                        .setParameter(1, token)
                        .setParameter(2, type)
        );
    }

    public static void deleteByUser(final User u, final Type type) {
        JPA.em().createQuery(
                "delete from TokenAction t where t.targetUser.id = ?1 and t.type = ?2"
        )
                .setParameter(1, u.getId())
                .setParameter(2, type);
    }

    public boolean isValid() {
        return this.expires.after(new Date());
    }

    @Transactional
    public static TokenAction create(final Type type, final String token,
                                     final User targetUser) {
        final TokenAction ua = new TokenAction();
        ua.targetUser = targetUser;
        ua.token = token;
        ua.type = type;
        final Date created = new Date();
        ua.created = created;
        ua.expires = new Date(created.getTime() + VERIFICATION_TIME * 1000);
        JPA.em().persist(ua);
        return ua;
    }
}
