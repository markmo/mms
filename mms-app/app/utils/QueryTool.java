package utils;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.*;

import controllers.Application;
import play.mvc.*;

import models.Organization;
import models.Page;
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
        scopeQueryToOrganization(cb, cq, queryRoot, session);
        return cq;
    }

    public static <T> CriteriaQuery<T> scopeQueryToOrganization(
            CriteriaBuilder cb, CriteriaQuery<T> cq, Root queryRoot,
            final Http.Session session) {
        User localUser = Application.getLocalUser(session);
        if (localUser != null && !localUser.isInRole("superadmin")) {
            Organization organization = localUser.organization;
            if (organization != null) {
                cq.where(cb.equal(queryRoot.get("organizationId"), organization.id));
            }
        }
        return cq;
    }

    public static <T> List<T> getResultList(Class<T> entity, EntityManager em, final Http.Session session) {
        CriteriaQuery<T> cq = createQuery(entity, em, session);
        return em.createQuery(cq).getResultList();
    }

    public static <T> CriteriaQuery<T> createPaginationQueryInOrganization(
            Class<T> entity, EntityManager em, final Http.Session session,
            String sortBy, String sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> queryRoot = cq.from(entity);
        scopeQueryToOrganization(cb, cq, queryRoot, session);
        Path sortPath = queryRoot.get(sortBy);
        Order order;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            order = cb.desc(sortPath);
        } else {
            order = cb.asc(sortPath);
        }
        cq.orderBy(order);
        return cq;
    }

    public static <T> CriteriaQuery<T> createPaginationQuery(
            Class<T> entity, EntityManager em,
            String sortBy, String sortOrder) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entity);
        Root<T> queryRoot = cq.from(entity);
        Path sortPath = queryRoot.get(sortBy);
        Order order;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            order = cb.desc(sortPath);
        } else {
            order = cb.asc(sortPath);
        }
        cq.orderBy(order);
        return cq;
    }

    public static <T> List<T> getPageList(Class<T> entity, EntityManager em,
                                          int pageIndex, int pageSize,
                                          String sortBy, String sortOrder) {
        CriteriaQuery<T> cq = createPaginationQuery(entity, em, sortBy, sortOrder);
        return em.createQuery(cq)
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public static <T> List<T> getPageListInOrganization(
            Class<T> entity, EntityManager em, final Http.Session session,
            int pageIndex, int pageSize, String sortBy, String sortOrder) {
        CriteriaQuery<T> cq = createPaginationQueryInOrganization(entity, em, session, sortBy, sortOrder);
        return em.createQuery(cq)
                .setFirstResult((pageIndex - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public static <T> CriteriaQuery<Long> createCountQuery(Class<T> entity,
                                                           EntityManager em) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> queryRoot = cq.from(entity);
        cq.select(cb.count(queryRoot));
        return cq;
    }

    public static <T> CriteriaQuery<Long> createCountQueryInOrganization(
            Class<T> entity, EntityManager em, final Http.Session session) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<T> queryRoot = cq.from(entity);
        scopeQueryToOrganization(cb, cq, queryRoot, session);
        cq.select(cb.count(queryRoot));
        return cq;
    }

    public static <T> Long getTotalRowCount(Class<T> entity, EntityManager em,
                                            final Http.Session session) {
        CriteriaQuery<Long> cq = createCountQueryInOrganization(entity, em, session);
        return em.createQuery(cq).getSingleResult();
    }

    public static <T> Long getTotalRowCount(Class<T> entity, EntityManager em) {
        CriteriaQuery<Long> cq = createCountQuery(entity, em);
        return em.createQuery(cq).getSingleResult();
    }

    public static <T> Page<T> getPage(Class<T> entity, EntityManager em,
                                      int pageIndex, int pageSize,
                                      String sortBy, String sortOrder) {
        List<T> list = getPageList(entity, em, pageIndex, pageSize, sortBy, sortOrder);
        Long totalRowCount = getTotalRowCount(entity, em);
        return new Page<>(list, totalRowCount, pageIndex, pageSize);
    }

    public static <T> Page<T> getPageScopedToOrganization(
            Class<T> entity, EntityManager em, final Http.Session session,
            int pageIndex, int pageSize, String sortBy, String sortOrder) {
        List<T> list = getPageListInOrganization(entity, em, session, pageIndex, pageSize, sortBy, sortOrder);
        Long totalRowCount = getTotalRowCount(entity, em, session);
        return new Page<>(list, totalRowCount, pageIndex, pageSize);
    }
}
