/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fusesource.devoxx.reportincident.service.impl;

import java.util.List;

import org.apache.camel.Body;
import org.apache.camel.Header;
import org.fusesource.devoxx.reportincident.model.Incident;
import org.fusesource.devoxx.reportincident.dao.IncidentDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fusesource.devoxx.reportincident.service.IncidentService;

public class IncidentServiceImpl implements IncidentService {
    
    private static final transient Log LOG = LogFactory.getLog(IncidentServiceImpl.class);

    /** The incident dao. */
    private IncidentDAO incidentDAO;

    public void removeIncident(long id) {
        getIncidentDAO().removeIncident(id);
    }

    public Incident getIncident(long id) {
        return getIncidentDAO().getIncident(id);
    }

    public List<Incident> findIncident() {
        return getIncidentDAO().findIncident();
    }

    public List<Incident> findIncident(String key) {
        return getIncidentDAO().findIncident(key);
    }

    @Override
    public void saveIncident(Incident incident) {
        try {
            getIncidentDAO().saveIncident(incident);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the incident dao.
     * 
     * @return the incident dao
     */
    public IncidentDAO getIncidentDAO() {
        return incidentDAO;
    }

    /**
     * Sets the incident dao.
     * 
     * @param incidentDAO
     *            the new incident dao
     */
    public void setIncidentDAO(IncidentDAO incidentDAO) {
        this.incidentDAO = incidentDAO;
    }

}
