package models;

import static controllers.Application.copyProperties;
import static controllers.Application.getSingleResult;

import java.util.*;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.persistence.*;

import be.objectify.deadbolt.core.models.Permission;
import be.objectify.deadbolt.core.models.Role;
import be.objectify.deadbolt.core.models.Subject;
import com.fasterxml.jackson.annotation.*;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.AuthUser;
import com.feth.play.module.pa.user.AuthUserIdentity;
import com.feth.play.module.pa.user.EmailIdentity;
import com.feth.play.module.pa.user.NameIdentity;
import controllers.Application;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import play.data.format.Formats;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;

import models.TokenAction.Type;

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
public class User implements Subject {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue
	public Long id;

//	@Email
	// if you make this unique, keep in mind that users *must* merge/link their
	// accounts then on signup with additional providers
	// @Column(unique = true)
	public String email;

    public String name;

    public String firstName;

    public String lastName;

    public String title;

    public String dept;

    @Column(length = 8000)
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

    @Override
    public String getIdentifier() {
        return Long.toString(id);
    }

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
        StringBuilder sb = new StringBuilder();
        if (firstName != null) {
            sb.append(firstName);
        }
        if (lastName != null) {
            if (sb.length() > 0)
                sb.append(" ");
            sb.append(lastName);
        }
        return sb.toString();
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

    public static boolean existsByAuthUserIdentity(
            final AuthUserIdentity identity) {
        final Query exp;
        if (identity instanceof UsernamePasswordAuthUser) {
            exp = getUsernamePasswordAuthUserFind((UsernamePasswordAuthUser) identity);
        } else {
            exp = getAuthUserFind(identity);
        }
        return exp.getMaxResults() > 0;
    }

    private static Query getAuthUserFind(final AuthUserIdentity identity) {
        return JPA.em().createQuery(
                "select u from User u join LinkedAccount a where u.active = true and a.providerUserId = ?1 and a.providerKey = ?2"
        )
                .setParameter(1, identity.getId())
                .setParameter(2, identity.getProvider());
    }

    public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
        if (identity == null) {
            return null;
        }
        if (identity instanceof UsernamePasswordAuthUser) {
            return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
        } else {
            return getSingleResult(User.class, getAuthUserFind(identity));
        }
    }

    public static User findByUsernamePasswordIdentity(
            final UsernamePasswordAuthUser identity) {
        return getSingleResult(User.class, getUsernamePasswordAuthUserFind(identity));
    }

    // original
//	private static ExpressionList<User> getUsernamePasswordAuthUserFind(
//			final UsernamePasswordAuthUser identity) {
//		return getEmailUserFind(identity.getEmail()).eq(
//				"linkedAccounts.providerKey", identity.getProvider());
//	}

    private static Query getUsernamePasswordAuthUserFind(
            final UsernamePasswordAuthUser identity) {
        return JPA.em().createQuery(
                "select u from User u join u.linkedAccounts a where u.active = true and u.email = ?1 and a.providerKey = ?2"
        )
                .setParameter(1, identity.getEmail())
                .setParameter(2, identity.getProvider());
    }

	public void merge(final User otherUser) {
		for (final LinkedAccount acc : otherUser.linkedAccounts) {
			this.linkedAccounts.add(LinkedAccount.create(acc));
		}
		// do all other merging stuff here - like resources, etc.

		// deactivate the merged user that got added to this one
		otherUser.active = false;
		JPA.em().persist(Arrays.asList(new User[] { otherUser, this }));
	}

	public static User create(final AuthUser authUser) {
		final User user = new User();
		user.roles = Collections.singletonList(
                SecurityRole.findByRoleName(controllers.Application.USER_ROLE));
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
        JPA.em().persist(user);

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

	public static void addLinkedAccount(final AuthUser oldUser,
			final AuthUser newUser) {
		final User u = User.findByAuthUserIdentity(oldUser);
		u.linkedAccounts.add(LinkedAccount.create(newUser));
        JPA.em().persist(u);
	}

	public static void setLastLoginDate(final AuthUser knownUser) {
		final User u = User.findByAuthUserIdentity(knownUser);
		u.lastLogin = new Date();
        JPA.em().persist(u);
	}

    public static User findByEmail(final String email) {
        return getSingleResult(User.class, getEmailUserFind(email));
    }

    private static Query getEmailUserFind(final String email) {
        return JPA.em().createQuery(
                "select u from User u where u.active = true and u.email = ?1"
        )
                .setParameter(1, email);
    }

	public LinkedAccount getAccountByProvider(final String providerKey) {
		return LinkedAccount.findByProviderKey(this, providerKey);
	}

	public static void verify(final User unverified) {
		// You might want to wrap this into a transaction
		unverified.emailValidated = true;
        JPA.em().persist(unverified);
		TokenAction.deleteByUser(unverified, Type.EMAIL_VERIFICATION);
	}

	public void changePassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		LinkedAccount a = this.getAccountByProvider(authUser.getProvider());
		if (a == null) {
			if (create) {
				a = LinkedAccount.create(authUser);
				a.user = this;
			} else {
				throw new RuntimeException(
						"Account not enabled for password usage"
                );
			}
		}
		a.providerUserId = authUser.getHashedPassword();
        JPA.em().persist(a);
    }

	public void resetPassword(final UsernamePasswordAuthUser authUser,
			final boolean create) {
		// You might want to wrap this into a transaction
		this.changePassword(authUser, create);
		TokenAction.deleteByUser(this, Type.PASSWORD_RESET);
	}

    public static User findById(Long id) {
        return getSingleResult(User.class, JPA.em().createQuery(
                "select u from User u where u.id = ?"
        )
                .setParameter(1, id)
        );
    }

    public static User update(User partialUser) {
        if (partialUser.id == null) {
            JPA.em().persist(partialUser);
            return partialUser;
        } else {
            User user = User.findById(partialUser.id);
            final String[] includedProperties = new String[]{
                    "email",
                    "firstName",
                    "lastName",
                    "name",
                    "title",
                    "dept",
                    "biography"
            };
            copyProperties(partialUser, user, Arrays.asList(includedProperties));
            JPA.em().persist(user);
            return user;
        }
    }

    public UserDTO getDTO() {
        UserDTO dto = new UserDTO();
        dto.id = id;
        dto.email = email;
        dto.firstName = firstName;
        dto.lastName = lastName;
        dto.name = getName();
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
        public String name;
        public String title;
        public String dept;
        public String biography;
        public int numberComments;
        public int numberVotes;
        public int numberFollowers;
        public int numberFollowing;

    }
}
