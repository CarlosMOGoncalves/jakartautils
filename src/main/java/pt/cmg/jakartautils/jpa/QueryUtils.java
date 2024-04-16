package pt.cmg.jakartautils.jpa;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import pt.cmg.jakartautils.text.TextFormatter;

public class QueryUtils {

    private static final Logger LOGGER = Logger.getLogger(QueryUtils.class.getName());

    private static final String ERROR_LOG_MESSAGE = "Error executing query";
    private static final String ERROR_EXCEPTION_MESSAGE = "Error executing query. Please check logs for details";

    public static <T> T getSingleResultFromQuery(TypedQuery<T> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.FINE, TextFormatter.formatMessage("Query returned no result : {0}", e.getMessage()));
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ERROR_LOG_MESSAGE, e);
            return null;
        }
    }

    public static int getIntResultFromQuery(Query query) {
        try {
            return ((Number) query.getSingleResult()).intValue();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ERROR_LOG_MESSAGE, e);
            throw new JpaQueryException(ERROR_EXCEPTION_MESSAGE);
        }
    }

    public static <T> List<T> getResultListFromQuery(TypedQuery<T> query) {
        try {
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ERROR_LOG_MESSAGE, e);
            throw new JpaQueryException(ERROR_EXCEPTION_MESSAGE);
        }
    }

    public static List getResultListFromQuery(Query query) {
        try {
            return query.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ERROR_LOG_MESSAGE, e);
            throw new JpaQueryException(ERROR_EXCEPTION_MESSAGE);
        }
    }

    public static int executeQueryUpdate(Query query) {
        try {
            return query.executeUpdate();
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, ERROR_LOG_MESSAGE, e);
            throw new JpaQueryException(ERROR_EXCEPTION_MESSAGE);
        }
    }

}
