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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fusesource.devoxx.reportincident.InputReportIncident;
import org.fusesource.devoxx.reportincident.model.Incident;

public class WebService {

    private static final transient Log LOG = LogFactory.getLog(WebService.class);

    public void process(Exchange exchange) throws ParseException {
        
        InputReportIncident webincident = (InputReportIncident)exchange.getIn().getBody();
        
        LOG.debug("prg.apache.camel.example.reportincident.model.Incident received from : " + webincident.getFamilyName() + ", " + webincident.getGivenName());
        LOG.debug("prg.apache.camel.example.reportincident.model.Incident info : " + webincident.getIncidentId() + ", at : " + webincident.getIncidentDate());
        LOG.debug("prg.apache.camel.example.reportincident.model.Incident details : " + webincident.getDetails() + ", summary : " + webincident.getSummary());
  
        
        List<Map<String, Incident>> models = new ArrayList<Map<String, Incident>>();
        Map<String, Incident> model = new HashMap<String, Incident>();
            
        // Convert the InputReportIncident into an prg.apache.camel.example.reportincident.model.Incident
        Incident incident = new Incident();
        
        DateFormat format = new SimpleDateFormat( "dd-MM-yyyy" );
        incident.setIncidentDate(format.parse(webincident.getIncidentDate()));
        
        incident.setDetails(webincident.getDetails());
        incident.setEmail(webincident.getEmail());
        incident.setFamilyName(webincident.getFamilyName());
        incident.setGivenName(webincident.getGivenName());
        incident.setIncidentRef(webincident.getIncidentId());
        incident.setPhone(webincident.getPhone());
        incident.setSummary(webincident.getSummary());
        
        // Get Header origin from message
        String origin = (String) exchange.getIn().getHeader("origin");

        // Specify current Date
        format = new SimpleDateFormat( "dd/MM/yyyy HH:mm:ss" );
        String currentDate = format.format( new Date() );
        Date creationDate = format.parse( currentDate );
        
        incident.setCreationDate(creationDate);
        incident.setCreationUser(origin);
        
        LOG.debug("prg.apache.camel.example.reportincident.model.Incident created from web service : " + incident.toString());
        
        // and place it in a model (cfr camel-bindy)
        model.put(Incident.class.getName(), incident);
        models.add(model);
        
        // replace with our input
        exchange.getOut().setBody(models);
        
        // propagate the header
        exchange.getOut().setHeader("origin", origin);
         
     }

}
