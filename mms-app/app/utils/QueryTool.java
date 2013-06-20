package utils;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import controllers.Application;
import play.mvc.*;

import models.Organization;
import models.User;

/**
 * User: markmo
 * Date: 21/06/13
 * Time: 7:54 AM
 */
public class QueryTool {

    public static <T> CriteriaQuery<T> createQuery(Class<T> entity, EntityManager em, final Http.Session session) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> queryRoot = cq.from(entity);
        User localUser = Application.getLocalUser(session);
        if (localUser != null) {
            Organization organization = localUser.organization;
            if (organization != null) {
                cq.where(cb.equal(queryRoot.get("organization"), organization));
            }
        }
        return cq;
    }

    public static <T> List<T> getResultList(Class<T> entity, EntityManager em, final Http.Session session) {
        CriteriaQuery<T> cq = createQuery(entity, em, session);
        return em.createQuery(cq).getResultList();
    }
}
