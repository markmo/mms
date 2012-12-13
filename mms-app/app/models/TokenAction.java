package models;

import java.util.Date;
import javax.persistence.*;
import javax.persistence.Column;

import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.jpa.JPA;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.annotation.EnumValue;

@Entity
public class TokenAction {// extends Model {

    public enum Type {

//        @EnumValue("EV")
        EMAIL_VERIFICATION("EV"),

//        @EnumValue("PR")
        PASSWORD_RESET("PR");

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
    public Long id;

    @Column(unique = true)
    public String token;

    @ManyToOne
    public User targetUser;

    @Enumerated(EnumType.STRING)
    public Type type;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date created;

    @Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date expires;

//    public static final Finder<Long, TokenAction> find = new Finder<Long, TokenAction>(
//            Long.class, TokenAction.class);

    public static TokenAction findByToken(final String token, final Type type) {
//        return find.where().eq("token", token).eq("type", type).findUnique();
        return JPA.em()
                .createQuery("from TokenAction t where t.token = ?1 and t.type = ?2",
                        TokenAction.class)
                .setParameter(1, token)
                .setParameter(2, type)
                .getSingleResult();
    }

    public static void deleteByUser(final User u, final Type type) {
//        Ebean.delete(find.where().eq("targetUser.id", u.id).eq("type", type)
//                .findIterate());
        JPA.em().createQuery("delete from TokenAction t where t.targetUser.id = ?1 and t.type = ?2")
                .setParameter(1, u.id)
                .setParameter(2, type);
    }

    public boolean isValid() {
        return this.expires.after(new Date());
    }

    public static TokenAction create(final Type type, final String token,
                                     final User targetUser) {
        final TokenAction ua = new TokenAction();
        ua.targetUser = targetUser;
        ua.token = token;
        ua.type = type;
        final Date created = new Date();
        ua.created = created;
        ua.expires = new Date(created.getTime() + VERIFICATION_TIME * 1000);
//        ua.save();
        JPA.em().persist(ua);
        return ua;
    }
}
