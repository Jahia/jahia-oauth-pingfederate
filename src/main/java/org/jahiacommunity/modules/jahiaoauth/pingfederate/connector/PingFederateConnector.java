package org.jahiacommunity.modules.jahiaoauth.pingfederate.connector;

import org.jahia.modules.jahiaauth.service.ConnectorConfig;
import org.jahia.modules.jahiaauth.service.ConnectorPropertyInfo;
import org.jahia.modules.jahiaauth.service.ConnectorService;
import org.jahia.modules.jahiaauth.service.JahiaAuthConstants;
import org.jahia.modules.jahiaauth.service.SettingsService;
import org.jahia.modules.jahiaoauth.service.JahiaOAuthService;
import org.jahia.modules.jahiaoauth.service.OAuthConnectorService;
import org.jahia.services.sites.JahiaSitesService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component(service = {PingFederateConnector.class, OAuthConnectorService.class, ConnectorService.class}, property = {JahiaAuthConstants.CONNECTOR_SERVICE_NAME + "=" + PingFederateConnector.KEY}, immediate = true)
public class PingFederateConnector implements OAuthConnectorService {
    private static final Logger logger = LoggerFactory.getLogger(PingFederateConnector.class);

    public static final String KEY = "PingFederateApi";
    private static final String DOMAIN = "domain";
    protected static final String SSO_LOGIN = "matricule";

    private JahiaOAuthService jahiaOAuthService;
    private JahiaSitesService jahiaSitesService;
    private SettingsService settingsService;

    @Reference
    private void setJahiaOAuthService(JahiaOAuthService jahiaOAuthService) {
        this.jahiaOAuthService = jahiaOAuthService;
    }

    @Reference
    private void setJahiaSitesService(JahiaSitesService jahiaSitesService) {
        this.jahiaSitesService = jahiaSitesService;
    }

    @Reference
    private void setSettingsService(SettingsService settingsService) {
        this.settingsService = settingsService;
    }

    @Activate
    private void onActivate() {
        jahiaOAuthService.addOAuthDefaultApi20(KEY, connectorConfig -> PingFederateApi.instance(connectorConfig.getProperty(DOMAIN)));

        jahiaSitesService.getSitesNames().forEach(siteName -> {
            ConnectorConfig connectorConfig = settingsService.getConnectorConfig(siteName, KEY);
            if (connectorConfig != null) {
                try {
                    validateSettings(connectorConfig);
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        });
    }

    @Deactivate
    private void onDeactivate() {
        jahiaOAuthService.removeOAuthDefaultApi20(KEY);
    }

    @Override
    public String getProtectedResourceUrl(ConnectorConfig config) {
        return config.getProperty(DOMAIN) + "/idp/userinfo.openid";
    }

    @Override
    public List<ConnectorPropertyInfo> getAvailableProperties() {
        List<ConnectorPropertyInfo> availableProperties = new ArrayList<>();
        availableProperties.add(new ConnectorPropertyInfo(SSO_LOGIN, "string"));
        availableProperties.add(new ConnectorPropertyInfo("email", "email"));
        availableProperties.add(new ConnectorPropertyInfo("pi.sri", "string"));
        availableProperties.add(new ConnectorPropertyInfo("sub", "string"));
        return availableProperties;
    }
}
