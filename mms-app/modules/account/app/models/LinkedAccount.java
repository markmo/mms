package models.account;

import static controllers.account.Application.getSingleResult;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.feth.play.module.pa.user.AuthUser;
import play.db.jpa.JPA;

@Entity
public class LinkedAccount {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "linked_account_id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference("linkedAccount")
    public User user;

    public String providerUserId;
    public String providerKey;

    public static LinkedAccount findByProviderKey(final User user, String key) {
        return getSingleResult(LinkedAccount.class,
                JPA.em().createQuery(
                        "select a from LinkedAccount a where a.user.id = ?1 and a.providerKey = ?2"
                )
                        .setParameter(1, user.getId())
                        .setParameter(2, key)
        );
    }

    public static LinkedAccount create(final AuthUser authUser) {
        final LinkedAccount ret = new LinkedAccount();
        ret.update(authUser);
        return ret;
    }

    public static LinkedAccount create(final LinkedAccount acc) {
        final LinkedAccount ret = new LinkedAccount();
        ret.providerKey = acc.providerKey;
        ret.providerUserId = acc.providerUserId;
        return ret;
    }

    public void update(final AuthUser authUser) {
        this.providerKey = authUser.getProvider();
        this.providerUserId = authUser.getId();
    }
}
