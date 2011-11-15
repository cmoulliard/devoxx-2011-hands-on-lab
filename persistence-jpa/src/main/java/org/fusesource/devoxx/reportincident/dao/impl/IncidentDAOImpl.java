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
package org.fusesource.devoxx.reportincident.dao.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fusesource.devoxx.reportincident.dao.IncidentDAO;
import org.fusesource.devoxx.reportincident.model.Incident;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IncidentDAOImpl implements IncidentDAO {

  private static final transient Log LOG = LogFactory.getLog(IncidentDAOImpl.class);

  @PersistenceContext
  private EntityManager em;
  private static final String findIncidentByReference = "select i from Incident as i where i.incidentRef = :ref";
  private static final String findIncident = "select i from Incident as i";

  public List<Incident> findIncident()
  {
    Query q = this.em.createQuery("select i from Incident as i");

    List list = q.getResultList();

    return list;
  }

  public List<Incident> findIncident(String key)
  {
    Query q = this.em.createQuery("select i from Incident as i where i.incidentRef = :ref");
    q.setParameter("ref", key);
    List list = q.getResultList();

    return list;
  }

  public Incident getIncident(long id)
  {
    return (Incident)this.em.find(Incident.class, Long.valueOf(id));
  }

  public void removeIncident(long id)
  {
    Object record = this.em.find(Incident.class, Long.valueOf(id));
    this.em.remove(record);
    this.em.flush();
  }

  public void saveIncident(Incident incident) {
    this.em.persist(incident);
    this.em.flush();
  }
}
