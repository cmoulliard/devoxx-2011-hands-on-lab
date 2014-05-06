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
package org.fusesource.devoxx.reportincident;

import java.util.Iterator;
import org.fusesource.devoxx.reportincident.model.Incident;
import org.fusesource.devoxx.reportincident.service.IncidentService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private static final transient Log LOG = LogFactory.getLog(HomePage.class);

    @SpringBean
    private IncidentService incidentService;

    /**
     * Constructor that is invoked when page is invoked without a session.
     * 
     * @param parameters
     *            Page parameters
     */
    public HomePage(final PageParameters parameters) {

        LOG.debug("Spring service : " + incidentService.toString());

        // Add the simplest type of label
        add(new Label("message", "List of incidents coming from web services or file : "));

        // Add paging
        final DataView dataView = new DataView("simple", new IncidentProvider()) {

            public void populateItem(final Item item) {
                final Incident incident = (Incident) item.getModelObject();
                item.add(new Label("incidentId", String.valueOf(incident.getIncidentId())));
                item.add(new Label("incidentDate", String.valueOf(incident.getIncidentDate())));
                item.add(new Label("incidentRef", incident.getIncidentRef()));
                item.add(new Label("givenName", incident.getGivenName()));
                item.add(new Label("familyName", incident.getFamilyName()));
                item.add(new Label("summary", incident.getSummary()));
                item.add(new Label("details", incident.getDetails()));
                item.add(new Label("email", incident.getEmail()));
                item.add(new Label("phone", incident.getPhone()));
                item.add(new Label("origin", incident.getOrigin()));
                item.add(new Label("creationUser", incident.getCreationUser()));
                item.add(new Label("creationDate", String.valueOf(incident.getCreationDate())));

                item.add(new AttributeModifier("class", true,
                        new AbstractReadOnlyModel() {
                            @Override
                            public Object getObject() {
                                return (item.getIndex() % 2 == 1) ? "even"
                                        : "odd";
                            }
                        }));
            }
        };
        
        dataView.setItemsPerPage(10);
        add(dataView);
        add(new PagingNavigator("navigator", dataView));

    }

    private class IncidentProvider implements IDataProvider {

        public Iterator iterator(int first, int count) {
            return incidentService.findIncident().iterator();
        }

        public int size() {
            return incidentService.findIncident().size();
        }

        public IModel model(Object object) {
            return new Model((Incident) object);
        }

        public void detach() {
            // TODO Auto-generated method stub

        }
    }

    private class IncidentDetachModel extends LoadableDetachableModel {

        private long id;

        @Override
        protected Object load() {
            return incidentService.findIncident(String.valueOf(id));
        }

        /**
         * @param c
         */
        public IncidentDetachModel(Incident i) {
            this(i.getIncidentId());
        }

        public IncidentDetachModel(long id) {

            if (id == 0) {
                throw new IllegalArgumentException();
            }
            this.id = id;
        }

    }

}
