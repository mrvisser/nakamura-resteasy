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
package uk.co.tfd.sm.jaxrs;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.auth.core.AuthenticationSupport;
import org.osgi.service.http.HttpContext;
import org.sakaiproject.nakamura.api.lite.authorizable.User;

import uk.co.tfd.sm.api.jaxrs.AuthenticationHelper;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Service({AuthenticationHelper.class, Filter.class})
@Component
@Property(name="pattern", value=".*")
public class ThreadAuthenticationHelperImpl implements AuthenticationHelper, Filter {

  private final static ThreadLocal<String> userIdLocal = new ThreadLocal<String>();
  
  @Reference(cardinality = ReferenceCardinality.OPTIONAL_UNARY)
  AuthenticationSupport authenticationSupport;
  
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // nothing to do.
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    String userId = null;
    if (request instanceof HttpServletRequest && response instanceof HttpServletResponse) {
      userId = ((HttpServletRequest) request).getRemoteUser();
      if (userId == null && authenticationSupport != null) {
        authenticationSupport.handleSecurity((HttpServletRequest)request, (HttpServletResponse)response);
        userId = (String) request.getAttribute(HttpContext.REMOTE_USER);
      }
    }
    
    if (userId == null) {
      userId = User.ANON_USER;
    }
    
    userIdLocal.set(userId);
    
    try {
      chain.doFilter(request, response);
    } finally {
      userIdLocal.set(User.ANON_USER);
    }
    
  }

  @Override
  public void destroy() {
    // nothing to do.
  }

  @Override
  public String getCurrentUserId() {
    String userId = userIdLocal.get();
    if (userId == null) {
      userId = User.ANON_USER;
    }
    return userId;
  }

}
