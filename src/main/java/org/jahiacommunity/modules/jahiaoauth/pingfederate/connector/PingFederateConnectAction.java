package org.jahiacommunity.modules.jahiaoauth.pingfederate.connector;

import org.jahia.bin.Action;
import org.jahia.bin.ActionResult;
import org.jahia.bin.Render;
import org.jahia.modules.jahiaauth.service.SettingsService;
import org.jahia.modules.jahiaoauth.service.JahiaOAuthService;
import org.jahia.services.content.JCRSessionWrapper;
import org.jahia.services.render.RenderContext;
import org.jahia.services.render.Resource;
import org.jahia.services.render.URLResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Component(service = Action.class)
public class PingFederateConnectAction extends Action {
    private static final Logger logger = LoggerFactory.getLogger(PingFederateConnectAction.class);

    private static final String NAME = "connectToPingFederateAction";

    private SettingsService settingsService;
    private JahiaOAuthService jahiaOAuthService;

    @Reference
    private void setSettingsService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Reference
    private void setJahiaOAuthService(JahiaOAuthService jahiaOAuthService) {
        this.jahiaOAuthService = jahiaOAuthService;
    }

    public PingFederateConnectAction() {
        setName(NAME);
        setRequireAuthenticatedUser(false);
        setRequiredMethods(Render.METHOD_GET);
    }

    @Override
    public ActionResult doExecute(HttpServletRequest req, RenderContext renderContext, Resource resource, JCRSessionWrapper session,
                                  Map<String, List<String>> parameters, URLResolver urlResolver) {
        return new ActionResult(HttpServletResponse.SC_OK, jahiaOAuthService.getAuthorizationUrl(
                settingsService.getConnectorConfig(renderContext.getSite().getSiteKey(), PingFederateConnector.KEY),
                req.getRequestedSessionId()), true, null);
    }
}
