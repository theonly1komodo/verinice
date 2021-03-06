/*******************************************************************************
 * Copyright (c) 2009 Daniel Murygin <dm[at]sernet[dot]de>.
 * This program is free software: you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either version 3 
 * of the License, or (at your option) any later version.
 *     This program is distributed in the hope that it will be useful,    
 * but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
 * See the GNU Lesser General Public License for more details.
 *     You should have received a copy of the GNU Lesser General Public 
 * License along with this program. 
 * If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Daniel <dm[at]sernet[dot]de> - initial API and implementation
 ******************************************************************************/
package sernet.verinice.hibernate;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateCallback;

import sernet.gs.common.ApplicationRoles;
import sernet.gs.service.SecurityException;
import sernet.verinice.interfaces.IAuthService;
import sernet.verinice.interfaces.IBaseDao;
import sernet.verinice.interfaces.IConfigurationService;
import sernet.verinice.model.common.CnATreeElement;
import sernet.verinice.model.common.Permission;
import sernet.verinice.model.common.configuration.Configuration;

/**
 * Extends {@link TreeElementDao} to check write and delete authorization for {@link CnATreeElement}s.
 * Use this for CnATreeElement-Daos in Spring configuration
 * 
 * @author Daniel Murygin <dm[at]sernet[dot]de>
 */
public class SecureTreeElementDao extends TreeElementDao<CnATreeElement, Integer> {

	private final Logger log = Logger.getLogger(SecureTreeElementDao.class);

	private IAuthService authService;

	private IBaseDao<Configuration, Integer> configurationDao;

	private IBaseDao<Permission, Integer> permissionDao;
	
	private IConfigurationService configurationService;

	
    @Override
    public List<CnATreeElement> findByCriteria(DetachedCriteria criteria) {
	    String username = getAuthService().getUsername();
	    if(isPermissionHandlingNeeded(username)) {
	        enableFilter();
	    }
	    List<CnATreeElement> result = super.findByCriteria(criteria);
	    if(isPermissionHandlingNeeded(username)) {
            disableFilter();
        }
	    return result;
	}
	
	public void enableFilter() {
        if(!hasAdminRole(authService.getRoles())) {
            if(log.isDebugEnabled()) {
                log.debug("Enabling security access filter for user: " + authService.getUsername());
            }
            setAccessFilterEnabled(true);
        }
        setScopeFilterEnabled(true);
    }
	
	public void disableFilter() {
        if(!hasAdminRole(authService.getRoles())) {
            if(log.isDebugEnabled()) {
                log.debug("Disabling security access filter.");
            }
            setAccessFilterEnabled(false);
        }
        setScopeFilterEnabled(false);
    }

	private void setScopeFilterEnabled(boolean enable) {
        if(getConfigurationService().isScopeOnly(authService.getUsername()) && enable) {
            final Integer userScopeId = getConfigurationService().getScopeId(authService.getUsername());
            getHibernateTemplate().enableFilter("scopeFilter").setParameter("scopeId", userScopeId);        
        } else {
            getHibernateTemplate().execute(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    session.disableFilter("scopeFilter");
                    return null;
                }
            }); 
        }
    }
	
	private void setAccessFilterEnabled(boolean enable) {
        if (enable) {
            final Object[] roles = getConfigurationService().getRoles(authService.getUsername());
            getHibernateTemplate().enableFilter("userAccessReadFilter").setParameterList("currentRoles", roles).setParameter("readAllowed", Boolean.TRUE);
        } else {
            getHibernateTemplate().execute(new HibernateCallback() {
                @Override
                public Object doInHibernate(Session session) throws HibernateException, SQLException {
                    session.disableFilter("userAccessReadFilter");
                    return null;
                }
            }); 
        }
    }
	
	
	/**
	 * @param type
	 */
	public SecureTreeElementDao(Class<CnATreeElement> type) {
		super(type);
	}
	
	/* (non-Javadoc)
	 * @see sernet.gs.ui.rcp.main.connect.HibernateBaseDao#delete(java.lang.Object)
	 */
	/* (non-Javadoc)
     * @see sernet.verinice.hibernate.ISecureDao#delete(sernet.verinice.model.common.CnATreeElement)
     */
    @Override
	public void delete(CnATreeElement entity) {
		checkRights(entity);
		super.delete(entity);
		indexDelete(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sernet.gs.ui.rcp.main.connect.HibernateBaseDao#merge(java.lang.Object)
	 */
	/* (non-Javadoc)
     * @see sernet.verinice.hibernate.ISecureDao#merge(sernet.verinice.model.common.CnATreeElement)
     */
    @Override
	public CnATreeElement merge(CnATreeElement entity) {
		return super.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * sernet.gs.ui.rcp.main.connect.HibernateBaseDao#merge(java.lang.Object,
	 * boolean)
	 */
	/* (non-Javadoc)
     * @see sernet.verinice.hibernate.ISecureDao#merge(sernet.verinice.model.common.CnATreeElement, boolean)
     */
    @Override
	public CnATreeElement merge(CnATreeElement entity, boolean fireChange) {
		// check rights only while updating
		if(entity.getDbId()!=null) {
			checkRights(entity);
		}
		return super.merge(entity, fireChange);
	}

	@Override
    public void checkRights(CnATreeElement entity, String username) /*throws SecurityException*/ { 
	    if (log.isDebugEnabled()) {
            log.debug("Checking rights for entity: " + entity + " and username: " + username);
        } 
	    if (isPermissionHandlingNeeded(username)) {
	        logPermissionInfo(entity, username);
	    }
    }

    private void logPermissionInfo(CnATreeElement entity, String username) {
        String[] roleArray = getDynamicRoles(username);
        if(roleArray==null) {
            log.error("Role array is null for user: " + username);
        }
        if(!hasAdminRole(roleArray)) {	    
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < roleArray.length; i++) {
                String name = roleArray[i];
                String escaped = name.replace("\\", "\\\\");
                sb.append("'").append(escaped).append("'");
                if(i<roleArray.length-1) {
                    sb.append(",");
                }           
            }
            String roleParam = sb.toString();
            
            sb = new StringBuilder();
            sb.append("select p.dbId from Permission p where p.cnaTreeElement.dbId = ? and p.role in (");
            // workaraound, because adding roles as ? param does not work
            sb.append(roleParam);
            sb.append(") and p.writeAllowed = ?");
            String hql = sb.toString();
            
            Object[] params = new Object[]{entity.getDbId(),Boolean.TRUE};
            if (log.isDebugEnabled()) {
                log.debug("checkRights, hql: " + hql);
                log.debug("checkRights, entity db-id: " + entity.getDbId() );
            }
            List<Integer> idList = getPermissionDao().findByQuery(hql, params);
            if (log.isDebugEnabled()) {
                log.debug("checkRights, permission ids: ");
                for (Integer integer : idList) {
                    log.debug(integer);
                }
            }
            if(idList==null | idList.isEmpty()) {
                final String message = "User: " + username + " has no right to write CnATreeElement with id: " + entity.getDbId();
                log.warn(message);
                throw new SecurityException(message);
            }
        }
        if(isScopeOnly(username)  
           && !entity.getScopeId().equals(getConfigurationService().getScopeId(username))) {
                final String message = "User: " + username + " has no right to write CnATreeElement with id: " + entity.getDbId();
                log.warn(message);
                throw new SecurityException(message);
        }
    }

    /**
     * @param username 
     * @return
     */
    private boolean isPermissionHandlingNeeded(String username) {
        return getAuthService().isPermissionHandlingNeeded() 
                && !(getAuthService().getAdminUsername().equals(username)) ;
    }
	
	/**
     * @param username
     * @return
     */
    private boolean isScopeOnly(String username) {
        return getConfigurationService().isScopeOnly(username);
    }

    /**
	 * @param entity
	 */
	@Override
    public void checkRights(CnATreeElement entity) /*throws SecurityException*/ { 
	    checkRights(entity, getAuthService().getUsername());
	}

	private boolean hasAdminRole(String[] roles) {
	    if(roles!=null) {
    		for (String r : roles) {
    			if (ApplicationRoles.ROLE_ADMIN.equals(r)){
    				return true;
    			}
    		}
	    }
		return false;
	}

	private String[] getDynamicRoles(String username) {
		return getConfigurationService().getRoles(username);
	}

	public void setAuthService(IAuthService authService) {
		this.authService = authService;
	}

	public IAuthService getAuthService() {
		return authService;
	}

	public void setConfigurationDao(IBaseDao<Configuration, Integer> configurationDao) {
		this.configurationDao = configurationDao;
	}

	public IBaseDao<Configuration, Integer> getConfigurationDao() {
		return configurationDao;
	}

	public void setPermissionDao(IBaseDao<Permission, Integer> permissionDao) {
		this.permissionDao = permissionDao;
	}

	public IBaseDao<Permission, Integer> getPermissionDao() {
		return permissionDao;
	}

    /**
     * @return the configurationService
     */
    public IConfigurationService getConfigurationService() {
        return configurationService;
    }

    /**
     * @param configurationService the configurationService to set
     */
    public void setConfigurationService(IConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

}
