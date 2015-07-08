package org.drools.audit.jpa;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TransactionRequiredException;
import javax.transaction.NotSupportedException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.kie.api.runtime.Environment;
import org.kie.api.runtime.EnvironmentName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KieRuntimeEventPersister {
	private static final Logger logger = LoggerFactory.getLogger(KieRuntimeEventPersister.class);
    
    private static final String[] KNOWN_UT_JNDI_KEYS = new String[] {"UserTransaction", "java:jboss/UserTransaction", System.getProperty("jbpm.ut.jndi.lookup")};
    
	private boolean isJTA = true;
	private boolean sharedEM = false;
	    
	private EntityManagerFactory emf;
	private Environment env;
	
	public KieRuntimeEventPersister(Environment env) {
		this.env = env;
	}
	
	/**
     * This method persists the entity given to it. 
     * </p>
     * This method also makes sure that the entity manager used for persisting the entity, joins the existing JTA transaction. 
     * @param entity An entity to be persisted.
     */
    public void persist(Object entity) { 
        EntityManager em = getEntityManager();
        Object tx = joinTransaction(em);
        em.persist(entity);
        leaveTransaction(em, tx);
    }
    
    /**
     * This method creates a entity manager. 
     */
    private EntityManager getEntityManager() {
        /**
         * It's important to set the sharedEM flag with _every_ operation
         * otherwise, there are situations where:
         * 1. it can be set to "true"
         * 2. something can happen
         * 3. the "true" value can no longer apply 
         * (I've seen this in debugging logs.. )
         */
        sharedEM = false;
        if( emf != null ) { 
           return emf.createEntityManager();
        } else if (env != null) {
            EntityManager em = (EntityManager) env.get(EnvironmentName.CMD_SCOPED_ENTITY_MANAGER);
        	if (em != null) {
        		sharedEM = true;
        		return em;
        	}
            EntityManagerFactory emf = (EntityManagerFactory) env.get(EnvironmentName.ENTITY_MANAGER_FACTORY);
            if (emf != null) {
                return emf.createEntityManager();
            }
        } 
        throw new RuntimeException("Could not find or create a new EntityManager!");
    }

    /**
     * This method opens a new transaction, if none is currently running, and joins the entity manager/persistence context
     * to that transaction. 
     * @param em The entity manager we're using. 
     * @return {@link UserTransaction} If we've started a new transaction, then we return it so that it can be closed. 
     * @throws NotSupportedException 
     * @throws SystemException 
     * @throws Exception if something goes wrong. 
     */
    private Object joinTransaction(EntityManager em) {
        boolean newTx = false;
        UserTransaction ut = null;

        if (isJTA) {
	        try {
	        	em.joinTransaction();
	        } catch (TransactionRequiredException e) {
				ut = findUserTransaction();
				try {
					if( ut != null && ut.getStatus() == Status.STATUS_NO_TRANSACTION ) { 
		                ut.begin();
		                newTx = true;
		                // since new transaction was started em must join it
		                em.joinTransaction();
		            } 
				} catch(Exception ex) {
					throw new IllegalStateException("Unable to find or open a transaction: " + ex.getMessage(), ex);
				}
				
				if (!newTx) {
	            	// rethrow TransactionRequiredException if UserTransaction was not found or started
	            	throw e;
	            }
			}
	       
	        if( newTx ) { 
	            return ut;
	        }
        } 
//        else { 
//            EntityTransaction tx = em.getTransaction();
//            if( ! tx.isActive() ) { 
//               tx.begin();
//               return tx;
//            }
//        }
        return null;
    }

    /**
     * This method closes the entity manager and transaction. It also makes sure that any objects associated 
     * with the entity manager/persistence context are detached. 
     * </p>
     * Obviously, if the transaction returned by the {@link #joinTransaction(EntityManager)} method is null, 
     * nothing is done with the transaction parameter.
     * @param em The entity manager.
     * @param ut The (user) transaction.
     */
    private void leaveTransaction(EntityManager em, Object transaction) {
        if( isJTA ) { 
            try { 
                if( transaction != null ) { 
                    // There's a tx running, close it.
                    ((UserTransaction) transaction).commit();
                }
            } catch(Exception e) { 
                logger.error("Unable to commit transaction: ", e);
            }
        } else { 
            if( transaction != null ) { 
                ((EntityTransaction) transaction).commit();
            }
        }
        

        if (!sharedEM) {
            try {  
                em.close(); 
            } catch( Exception e ) { 
                logger.error("Unable to close created EntityManager: {}", e.getMessage(), e);
            }
        }
    }

    protected static UserTransaction findUserTransaction() {
    	InitialContext context = null;
    	try {
            context = new InitialContext();
            return (UserTransaction) context.lookup( "java:comp/UserTransaction" );
        } catch ( NamingException ex ) {
        	
        	for (String utLookup : KNOWN_UT_JNDI_KEYS) {
        		if (utLookup != null) {
		        	try {
		        		UserTransaction ut = (UserTransaction) context.lookup(utLookup);
		        		return ut;
					} catch (NamingException e) {
						logger.debug("User Transaction not found in JNDI under {}", utLookup);
						
					}
        		}
        	}
        	logger.warn("No user transaction found under known names");
        	return null;
        }
    }
}
