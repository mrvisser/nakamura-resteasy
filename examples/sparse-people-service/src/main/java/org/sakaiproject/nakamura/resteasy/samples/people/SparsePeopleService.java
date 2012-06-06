/*
 * Licensed to the Sakai Foundation (SF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The SF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.sakaiproject.nakamura.resteasy.samples.people;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.sakaiproject.nakamura.api.lite.ClientPoolException;
import org.sakaiproject.nakamura.api.lite.Repository;
import org.sakaiproject.nakamura.api.lite.Session;
import org.sakaiproject.nakamura.api.lite.StorageClientException;
import org.sakaiproject.nakamura.api.lite.accesscontrol.AccessDeniedException;
import org.sakaiproject.nakamura.api.lite.authorizable.Authorizable;
import org.sakaiproject.nakamura.api.lite.authorizable.User;
import org.sakaiproject.nakamura.resteasy.samples.people.api.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.tfd.sm.api.jaxrs.AuthenticationHelper;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

/**
 *
 */
@Service(value=Object.class)
@Component
@Property(name="javax.ws.rs", boolValue=true, propertyPrivate=true)
@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SparsePeopleService {

  private final static Logger LOGGER = LoggerFactory.getLogger(SparsePeopleService.class);
  
  @Reference
  Repository repository;
  
  @Reference
  AuthenticationHelper authenticationHelper;
  
  @GET @Path("/me")
  public Person findCurrentUser() {
    return findById(authenticationHelper.getCurrentUserId());
  }
  
  @GET @Path("/id/{id}")
  public Person findById(@PathParam("id") String id) {
    String currentUserId = authenticationHelper.getCurrentUserId();
    Session s = null;
    try {
      s = repository.loginAdministrative(currentUserId);
      Authorizable auth = s.getAuthorizableManager().findAuthorizable(id);
      if (auth instanceof User) {
        return new Person(auth.getId(), auth.getSafeProperties());
      }
      
      throw new WebApplicationException(Status.NOT_FOUND);
    } catch (ClientPoolException e) {
      LOGGER.error("Error finding user by id.", e);
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } catch (StorageClientException e) {
      LOGGER.error("Error finding user by id.", e);
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } catch (AccessDeniedException e) {
      throw new WebApplicationException(Status.NOT_FOUND);
    } finally {
      logout(s);
    }
  }
  
  @POST
  public Person createPerson(Person person) {
    String currentUserId = authenticationHelper.getCurrentUserId();
    Session s = null;
    try {
      s = repository.loginAdministrative(currentUserId);
      s.getAuthorizableManager().createUser(person.id, person.name, person.password,
          person.extraProperties);
      return findById(person.id);
    } catch (ClientPoolException e) {
      LOGGER.error("Error finding user by id.", e);
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } catch (StorageClientException e) {
      LOGGER.error("Error finding user by id.", e);
      throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
    } catch (AccessDeniedException e) {
      throw new WebApplicationException(Status.NOT_FOUND);
    } finally {
      logout(s);
    }
  }
  
  private void logout(Session s) {
    if (s != null) {
      try {
        s.logout();
      } catch (ClientPoolException e) {
        LOGGER.warn("Error logging out of session.", e);
      }
    }
  }
}
