package models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import play.db.jpa.JPA;

import com.feth.play.module.pa.user.AuthUser;

@Entity
public class LinkedAccount {// extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("linkedAccount")
    public User user;

    public String providerUserId;
    public String providerKey;

//    public static final Finder<Long, LinkedAccount> find = new Finder<Long, LinkedAccount>(
//            Long.class, LinkedAccount.class);

    public static LinkedAccount findByProviderKey(final User user, String key) {
//        return find.where().eq("user", user).eq("providerKey", key)
//                .findUnique();
        return JPA.em()
                .createQuery("select a from LinkedAccount a where a.user = ?1 and a.providerKey = ?2",
                        LinkedAccount.class)
                .setParameter(1, user)
                .setParameter(2, key)
                .getSingleResult();
    }

    public static LinkedAccount create(final AuthUser authUser) {
        final LinkedAccount ret = new LinkedAccount();
        ret.update(authUser);
        return ret;
    }

    public void update(final AuthUser authUser) {
        this.providerKey = authUser.getProvider();
        this.providerUserId = authUser.getId();
    }

    public static LinkedAccount create(final LinkedAccount acc) {
        final LinkedAccount ret = new LinkedAccount();
        ret.providerKey = acc.providerKey;
        ret.providerUserId = acc.providerUserId;

        return ret;
    }
}