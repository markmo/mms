package models;

import static controllers.Application.copyProperties;
import static controllers.Application.getSingleResult;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.*;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.*;
import org.apache.commons.lang.StringUtils;
import org.code_factory.jpa.nestedset.*;
import org.code_factory.jpa.nestedset.annotations.NodeClass;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import play.data.format.Formats;
import play.db.jpa.JPA;

import models.TokenAction.Type;

/**
 * Initial version based on work by Steve Chaloner (steve@objectify.be) for
 * Deadbolt2
 *
 * Modified by Mark Moloney for JPA
 */
@Entity
@Table(name = "users", schema = "mms")
@NodeClass(type = SecuritySubject.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id") // still not working
// using DTO at bottom for JSON serialization
public class User extends SecuritySubject {

//	@Email
	// if you make this unique, keep in mind that users *must* merge/link their
	// accounts then on signup with additional providers
	// @Column(unique = true)
	public String email;

    private String firstName;

    private String lastName;

    public String title;

    public String dept;

    @Column(length = 8000)
    public String biography;

    public int numberComments;

    public int numberVotes;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "followed_user_id"),
            inverseJoinColumns = @JoinColumn(name = "following_user_id")
    )
    @JsonManagedReference("follower")
    public List<User> followers;

    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(
            name = "followers",
            joinColumns = @JoinColumn(name = "following_user_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_user_id")
    )
    @JsonBackReference("follower")
    public List<User> following;

	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date lastLogin;

	public boolean active;

	public boolean emailValidated;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonManagedReference("linkedAccount")
	public List<LinkedAccount> linkedAccounts;

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
        this.name = name;
        if (name == null) return;
        String[] parts = name.trim().split("\\s+");
        int len = parts.length;
        lastName = parts[len - 1];
        if (len > 1) {
            firstName = StringUtils.join(Arrays.copyOfRange(parts, 0, len - 1), " ");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName(getName());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName(getName());
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
                "select u from User u join LinkedAccount a " +
                "where u.active = true and a.providerUserId = ?1 " +
                "and a.providerKey = ?2"
        )
                .setParameter(1, identity.getId())
                .setParameter(2, identity.getProvider());
    }

    public static User findByAuthUserIdentity(final AuthUserIdentity identity) {
        if (identity == null) {
            return null;
        }
//        try {
//            return JPA.withTransaction(new F.Function0<User>() {
//                @Override
//                public User apply() {
                    if (identity instanceof UsernamePasswordAuthUser) {
                        return findByUsernamePasswordIdentity((UsernamePasswordAuthUser) identity);
                    } else {
                        return getSingleResult(User.class, getAuthUserFind(identity));
                    }
//                }
//            });
//        } catch (Throwable e) {
//            e.printStackTrace();
//            return null;
//        }
    }

    public static User findByUsernamePasswordIdentity(
            final UsernamePasswordAuthUser identity) {
        return getSingleResult(User.class,
                getUsernamePasswordAuthUserFind(identity));
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
                "select u from User u join u.linkedAccounts a " +
                "where u.active = true and u.email = ?1 " +
                "and a.providerKey = ?2"
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
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        Node<User> node = nsm.createRoot(new User());
		final User user = node.unwrap();
		user.roles = Collections.singletonList(
                SecurityRole.findByRoleName(controllers.Application.USER_ROLE));
		// user.permissions = new ArrayList<UserPermission>();
		// user.permissions.add(UserPermission.findByValue("printers.edit"));
		user.active = true;
		user.lastLogin = new Date();

        ///////////// this wasn't in the original
        // TODO: was ebean creating this association automatically?

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
			final NameIdentity identity = (NameIdentity)authUser;
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

    public static User findById(int id) {
        return getSingleResult(User.class, JPA.em().createQuery(
                "select u from User u where u.id = ?1"
        )
                .setParameter(1, id)
        );
    }

    public static User update(UserDTO userDTO) {
        NestedSetManager nsm = new JpaNestedSetManager(JPA.em());
        SecurityGroup.SecurityGroupDTO parent = userDTO.parentGroup;
        SecurityGroup parentGroup = null;
        if (parent != null && parent.id > 0) {
            parentGroup = JPA.em().find(SecurityGroup.class, parent.id);
        }
        final String[] includedProperties = new String[]{
                "email",
                "firstName",
                "lastName",
                "title",
                "dept",
                "biography",
                "roleIds"
        };
        if (userDTO.id == 0) {
            User user = new User();
            user.parentGroup = parentGroup;
            copyProperties(userDTO, user, Arrays.asList(includedProperties));
            if (parentGroup == null) {
                nsm.createRoot(user);
            } else {
                Node<SecuritySubject> node = nsm.getNode((SecuritySubject)parentGroup);
                node.addChild(user);
            }
            JPA.em().persist(user);
            return user;
        } else {
            User user = User.findById(userDTO.id);
            copyProperties(userDTO, user, Arrays.asList(includedProperties));
            if (userDTO.roleIds == null || userDTO.roleIds.isEmpty()) {
                if (user.roles != null) {
                    user.roles.clear();
                }
            }
            if (!isSameGroup(user.parentGroup, parentGroup)) {
                user.parentGroup = parentGroup;
                if (parentGroup == null) {
                    Node<User> node = nsm.getNode(user);
                    node.moveToRoot();
                } else {
                    Node<SecuritySubject> node = nsm.getNode((SecuritySubject)parentGroup);
                    nsm.getNode((SecuritySubject)user).moveAsLastChildOf(node);
                }
            }
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
        if (parentGroup != null) {
            dto.parentGroup = parentGroup.getDTO();
        }
        dto.roleIds = getRoleIds();

        return dto;
    }

    public static class UserDTO {

        public int id;
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
        public SecurityGroup.SecurityGroupDTO parentGroup;
        public List<Long> roleIds;

    }
}
