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
package org.fusesource.devoxx.reportincident.internal;

import org.apache.camel.Exchange;
import org.fusesource.devoxx.reportincident.model.Incident;
import org.fusesource.devoxx.reportincident.service.IncidentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IncidentSaver {

    private static final transient Log LOG = LogFactory.getLog(IncidentSaver.class);
    
    private IncidentService incidentService = null;

    public void process(Exchange exchange) throws ParseException {

        int count = 0;

        List<Map<String, Object>> models = new ArrayList<Map<String, Object>>();
        Map<String, Object> model = new HashMap<String, Object>();

        // Get models from message
        models = (List<Map<String, Object>>) exchange.getIn().getBody();
        
        // Get Header origin from message
        String origin = (String) exchange.getIn().getHeader("origin");
        LOG.debug("Header origin : " + origin);

        Iterator<Map<String, Object>> it = models.iterator();
        
        // Specify current Date
        DateFormat format = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        String currentDate = format.format( new Date() );
        Date creationDate = format.parse( currentDate );
        
        while (it.hasNext()) {

            model = it.next();
            
            LOG.debug("Model retrieved");

            for (String key : model.keySet()) {
                
                LOG.debug("Object retrieved : " + model.get(key).toString());
                
                // Retrieve incident from model
                Incident incident = (Incident) model.get(key);
                incident.setCreationDate(creationDate);
                incident.setOrigin(origin);
                incident.setCreationUser("camel");
                
                LOG.debug("Count : " + count + ", " + incident.toString());
                
                // Save org.fusesource.devoxx.reportincident.model.Incident
                incidentService.saveIncident(incident);
                LOG.debug(">>> org.fusesource.devoxx.reportincident.model.Incident saved");
            }

            count++;
        }

        LOG.debug("Nber of CSV records received by the csv bean : " + count);

    }
    
    // Property used to inject service implementation
    public void setIncidentService(IncidentService incidentService) {
        this.incidentService = incidentService;
    }
    
    

}
