package org.jahiacommunity.modules.jahiaoauth.pingfederate.connector;

import com.github.scribejava.core.builder.api.DefaultApi20;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PingFederateApi extends DefaultApi20 {

    private static final ConcurrentMap<String, PingFederateApi> INSTANCES = new ConcurrentHashMap<>();

    private final String domain;

    private PingFederateApi(String domain) {
        this.domain = domain;
    }

    public static PingFederateApi instance(String domain) {
        return INSTANCES.computeIfAbsent(domain, PingFederateApi::new);
    }

    @Override
    public String getAccessTokenEndpoint() {
        return domain + "/as/token.oauth2";
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return domain + "/as/authorization.oauth2";
    }
}
