/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.cas.adaptors.trusted.web.flow;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.adaptors.trusted.authentication.principal.PrincipalBearingCredentials;
import org.jasig.cas.server.authentication.Credential;
import org.jasig.cas.server.authentication.SimplePrincipal;
import org.jasig.cas.web.flow.AbstractNonInteractiveCredentialsAction;
import org.springframework.webflow.execution.RequestContext;

/**
 * Implementation of the NonInteractiveCredentialsAction that looks for a user
 * principal that is set in the <code>HttpServletRequest</code> and attempts
 * to construct a Principal (and thus a PrincipalBearingCredentials). If it
 * doesn't find one, this class returns and error event which tells the web flow
 * it could not find any credentials.
 * 
 * @author Scott Battaglia
 * @version $Revision$ $Date$
 * @since 3.0.5
 */
public final class PrincipalFromRequestUserPrincipalNonInteractiveCredentialsAction extends AbstractNonInteractiveCredentialsAction {

    @Override
    protected Credential constructCredentialsFromRequest(final HttpServletRequest request, final HttpServletResponse response) {
        final Principal principal = request.getUserPrincipal();

        if (principal != null) {
            if (log.isDebugEnabled()) {
                log.debug("UserPrincipal [" + principal.getName()
                    + "] found in HttpServletRequest");
            }
            return new PrincipalBearingCredentials(new SimplePrincipal(principal.getName()));
        }

        if (log.isDebugEnabled()) {
            log.debug("UserPrincipal not found in HttpServletRequest.");
        }

        return null;
    }
}