package models;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import models.TokenAction.Type;

import org.apache.commons.lang.StringUtils;
import play.data.format.Formats;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import scala.actors.threadpool.Arrays;
import be.objectify.deadbolt.models.Permission;
import be.objectify.deadbolt.models.Role;
import be.objectify.deadbolt.models.RoleHolder;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 *
 * Modified by Mark Moloney for JPA
 */
@Entity
@Table(name = "users")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id") // still not working
// using DTO at bottom for JSON serialization
public class User implements RoleHolder {// extends Model implements RoleHolder {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
	public Long id;

//	@Email
	// if you make this unique, keep in mind that users *must* merge/link their
	// accounts then on signup with additional providers
	// @Column(unique = true)
	public String email;

    public String firstName;

    public String lastName;

    public String title;

    public String dept;

    public String biography;

    public int numberComments;

    public int numberVotes;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ds_followers",
            joinColumns = @JoinColumn(name = "followed_user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_user_id")
    )
    @JsonManagedReference("follower")
    public List<User> followers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "ds_followers",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_user_id")
    )
    @JsonBackReference("follower")
    public List<User> following;

	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date lastLogin;

	public boolean active;

	public boolean emailValidated;

	@ManyToMany(cascade = CascadeType.ALL)
	public List<SecurityRole> roles;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference("linkedAccount") // not working - currently forced to use @JsonIdentityInfo above
	public List<LinkedAccount> linkedAccounts;

	@ManyToMany(cascade = CascadeType.ALL)
	public List<UserPermission> permissions;

//	public static final Finder<Long, User> find = new Finder<Long, User>(
//			Long.class, User.class);

	@Override
	public List<? extends Role> getRoles() {
		return roles;
	}

	@Override
	public List<? extends Permission> getPermissions() {
		return permissions;
	}

    @Transient
    public String getName() {
        return firstName + " " + lastName;
    }

    public void setName(String name) {
        if (name == null) return;
        String[] parts = name.trim().split("\\s+");
        int len = parts.length;
        lastName = parts[len - 1];
        if (len > 1) {
            firstName = StringUtils.join(Arrays.copyOfRange(parts, 0, len - 1), " ");
        }
    }

//	public static boolean existsByAuthUserIdentity(
//			final AuthUserIdentity identity) {
//		final ExpressionList<User> exp;
//		if (identity instanceof UsernamePasswordAuthUser) {
//			exp = getUsernamePasswordAuthUserFind((UsernamePasswordAuthUser) identity);
//		} else {
//			exp = getAuthUserFind(identity);
//		}
//		return exp.findRowCount() > 0;
//	}

    public static boolean existsByAuthUserIdentity(
            final AuthUserIdentity identity) {
        final TypedQuery<User> exp;
        if (identity instanceof UsernamePasswordAuthUser) {
            exp = getUsernamePasswordAuthUserFind((UsernamePasswordAuthUser) identity);
        } else {
            exp = getAuthUserFind(identity);
        }
        return exp.getMaxResults() > 0;
    }

//	private static ExpressionList<User> getAuthUserFind(
//			final AuthUserIdentity identity) {
//		return find.where().eq("active", true)
//				.eq("linkedAccounts.providerUserId", identity.getId())
//				.eq("linkedAccounts.providerKey", identity.getProvider());
//	}

    @Transactional
    private static TypedQuery<User> getAuthUserFind(final AuthUserIdentity identity) {
        return JPA.em()
                .createQuery("select u from User u join LinkedAccount a where u.active = true and a.providerUserId = ?1 and a.providerKey = ?2",
                        User.class)
                .setParameter(1, identity.getId())
                .setParameter(2, identity.getProvider());
    }

//	public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
//		if (identity == null) {
//			return null;
//		}
//		if (identity instanceof UsernamePasswordAuthUser) {
//			return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
//		} else {
//			return getAuthUserFind(identity).findUnique();
//		}
//	}

    public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
        if (identity == null) {
            return null;
        }
        if (identity instanceof UsernamePasswordAuthUser) {
            return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
        } else {
            return getAuthUserFind(identity).getSingleResult();
        }
    }

//	public static User findByUsernamePasswordIdentity(
//			final UsernamePasswordAuthUser identity) {
//		return getUsernamePasswordAuthUserFind(identity).findUnique();
//	}

    public static User findByUsernamePasswordIdentity(
            final UsernamePasswordAuthUser identity) {
        try {
            return getUsernamePasswordAuthUserFind(identity).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

//	private static ExpressionList<User> getUsernamePasswordAuthUserFind(
//			final UsernamePasswordAuthUser identity) {
//		return getEmailUserFind(identity.getEmail()).eq(
//				"linkedAccounts.providerKey", identity.getProvider());
//	}

    @Transactional
    private static TypedQuery<User> getUsernamePasswordAuthUserFind(
            final UsernamePasswordAuthUser identity) {
        String providerKey = identity.getProvider();
        return JPA.em()
                .createQuery("select u from User u join u.linkedAccounts a where u.active = true and u.email = ?1 and a.providerKey = ?2",
                        User.class)
                .setParameter(1, identity.getEmail())
                .setParameter(2, identity.getProvider());
    }

    @Transactional
	public void merge(final User otherUser) {
		for (final LinkedAccount acc : otherUser.linkedAccounts) {
			this.linkedAccounts.add(LinkedAccount.create(acc));
		}
		// do all other merging stuff here - like resources, etc.

		// deactivate the merged user that got added to this one
		otherUser.active = false;
		JPA.em().persist(Arrays.asList(new User[] { otherUser, this }));
	}

    @Transactional
	public static User create(final AuthUser authUser) {
		final User user = new User();
		user.roles = Collections.singletonList(SecurityRole
				.findByRoleName(controllers.Application.USER_ROLE));
		// user.permissions = new ArrayList<UserPermission>();
		// user.permissions.add(UserPermission.findByValue("printers.edit"));
		user.active = true;
		user.lastLogin = new Date();

        ///////////// this wasn't in the original
        // TODO: was ebean creating this association automatically

        LinkedAccount a = LinkedAccount.create(authUser);
        a.user = user;
        user.linkedAccounts = Collections.singletonList(a);

        //user.linkedAccounts = Collections.singletonList(LinkedAccount
        //		.create(authUser));
        /////////////

		if (authUser instanceof EmailIdentity) {
			final EmailIdentity identity = (EmailIdentity) authUser;
			// Remember, even when getting them from FB & Co., emails should be
			// verified within the application as a security breach there might
			// break your security as well!
			user.email = identity.getEmail();
			user.emailValidated = false;
		}

		if (authUser instanceof NameIdentity) {
			final NameIdentity identity = (NameIdentity) authUser;
			final String name = identity.getName();
			if (name != null) {
				user.setName(name);
			}
		}

//		user.save();
//		user.saveManyToManyAssociations("roles");
        JPA.em().persist(user);
		// user.saveManyToManyAssociations("permissions");
		return user;
	}

	public static void merge(final AuthUser oldUser, final AuthUser newUser) {
		User.findByAuthUserIdentity(oldUser).merge(
				User.findByAuthUserIdentity(newUser));
	}

	public Set<String> getProviders() {
		final Set<String> providerKeys = new HashSet<String>(
				linkedAccounts.size());
		for (final LinkedAccount acc : linkedAccounts) {
			providerKeys.add(acc.providerKey);
		}
		return providerKeys;
	}

    @Transactional
	public static void addLinkedAccount(final AuthUser oldUser,
			final AuthUser newUser) {
		final User u = User.findByAuthUserIdentity(oldUser);
		u.linkedAccounts.add(LinkedAccount.create(newUser));
//		u.save();
        JPA.em().persist(u);
	}

    @Transactional
	public static void setLastLoginDate(final AuthUser knownUser) {
		final User u = User.findByAuthUserIdentity(knownUser);
		u.lastLogin = new Date();
//		u.save();
        JPA.em().persist(u);
	}

//	public static User findByEmail(final String email) {
//		return getEmailUserFind(email).findUnique();
//	}

    public static User findByEmail(final String email) {
        return getEmailUserFind(email).getSingleResult();
    }

//	private static ExpressionList<User> getEmailUserFind(final String email) {
//		return find.where().eq("active", true).eq("email", email);
//	}

    @Transactional
    private static TypedQuery<User> getEmailUserFind(final String email) {
        return JPA.em()
                .createQuery("select u from User u where u.active = true and u.email = ?1",
                        User.class)
                .setParameter(1, email);
    }

	public LinkedAccount getAccountByProvider(final String providerKey) {
		return LinkedAccount.findByProviderKey(this, providerKey);
	}

    @Transactional
	public static void verify(final User unverified) {
		// You might want to wrap this into a transaction
		unverified.emailValidated = true;
//		unverified.save();
        JPA.em().persist(unverified);
		TokenAction.deleteByUser(unverified, Type.EMAIL_VERIFICATION);
	}

    @Transactional
	public void changePassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		LinkedAccount a = this.getAccountByProvider(authUser.getProvider());
		if (a == null) {
			if (create) {
				a = LinkedAccount.create(authUser);
				a.user = this;
			} else {
				throw new RuntimeException(
						"Account not enabled for password usage");
			}
		}
		a.providerUserId = authUser.getHashedPassword();
//		a.save();
        JPA.em().persist(a);
    }

	public void resetPassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		// You might want to wrap this into a transaction
		this.changePassword(authUser, create);
		TokenAction.deleteByUser(this, Type.PASSWORD_RESET);
	}

    public UserDTO getDTO() {
        UserDTO dto = new UserDTO();
        dto.id = id;
        dto.email = email;
        dto.firstName = firstName;
        dto.lastName = lastName;
        dto.title = title;
        dto.dept = dept;
        dto.numberComments = numberComments;
        dto.numberVotes = numberVotes;
        dto.numberFollowers = (followers == null) ? 0 : followers.size();
        dto.numberFollowing = (following == null) ? 0 : following.size();
        dto.biography = biography;

        return dto;
    }

    public static class UserDTO {

        public Long id;
        public String email;
        public String firstName;
        public String lastName;
        public String title;
        public String dept;
        public String biography;
        public int numberComments;
        public int numberVotes;
        public int numberFollowers;
        public int numberFollowing;

    }
}
