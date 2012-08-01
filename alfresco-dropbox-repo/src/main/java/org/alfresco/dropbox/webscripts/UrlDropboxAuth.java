/*
 * Copyright 2011-2012 Alfresco Software Limited.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This file is part of an unsupported extension to Alfresco.
 * 
 */

package org.alfresco.dropbox.webscripts;


import java.util.HashMap;
import java.util.Map;

import org.alfresco.dropbox.service.DropboxService;
import org.springframework.extensions.webscripts.Cache;
import org.springframework.extensions.webscripts.DeclarativeWebScript;
import org.springframework.extensions.webscripts.Status;
import org.springframework.extensions.webscripts.WebScriptException;
import org.springframework.extensions.webscripts.WebScriptRequest;

/**
 * 
 *
 * @author Jared Ottley
 */
public class UrlDropboxAuth
    extends DeclarativeWebScript
{

    private final static String CALLBACK_WEBSCRIPT = "/share/service/dropbox/account/verifier";
    private final static String CALLBACK_PARAM     = "callback";
    private final static String AUTHURL            = "authURL";

    private DropboxService      dropboxService;


    public void setDropboxService(DropboxService dropboxService)
    {
        this.dropboxService = dropboxService;
    }


    @Override
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
        Map<String, Object> model = new HashMap<String, Object>();

        if (req.getParameter(CALLBACK_PARAM) != null)
        {
            String callbackUrl = req.getParameter(CALLBACK_PARAM) + CALLBACK_WEBSCRIPT;

            model.put(AUTHURL, dropboxService.getAuthorizeUrl(callbackUrl));
        }
        else
        {
            throw new WebScriptException(Status.STATUS_NOT_ACCEPTABLE, "Missing Callback Parameter");
        }


        return model;
    }

}
