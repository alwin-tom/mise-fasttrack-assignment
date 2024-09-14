package com.airfranceklm.fasttrack.assignment.dao.generator;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.Configurable;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import java.io.Serializable;
import java.util.Properties;

public class PrimaryKeyGenerator implements IdentifierGenerator, Configurable {
    private String prefix;

    /**
     *
     * @param session
     * @param obj
     * @return
     * @implNote A primary key generator to match the pattern of 'prefix' concatinated with a six digit number. Used for EmployeeId
     * @throws HibernateException
     */
    @Override
    public Serializable generate(
            SharedSessionContractImplementor session, Object obj)
            throws HibernateException {

        String query = String.format("select %s from %s",
                session.getEntityPersister(obj.getClass().getName(), obj).getIdentifierPropertyName(),
                obj.getClass().getSimpleName());

        final long max = session.createQuery(query).stream()
                .map(o -> ((String) o).replace(prefix, ""))
                .mapToLong(o -> Long.parseLong((String) o)).max().orElse(0l);
        String format = "%1$06d";
        return prefix + String.format(format, max + 1);
    }

    @Override
    public void configure(Type type, Properties properties, ServiceRegistry serviceRegistry) throws MappingException {
        prefix = properties.getProperty("prefix");
    }
}
