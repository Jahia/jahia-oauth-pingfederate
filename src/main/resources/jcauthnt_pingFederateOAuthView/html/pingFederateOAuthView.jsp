<%@ taglib prefix="jcr" uri="http://www.jahia.org/tags/jcr" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="utility" uri="http://www.jahia.org/tags/utilityLib" %>
<%@ taglib prefix="template" uri="http://www.jahia.org/tags/templateLib" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--@elvariable id="currentNode" type="org.jahia.services.content.JCRNodeWrapper"--%>
<%--@elvariable id="out" type="java.io.PrintWriter"--%>
<%--@elvariable id="script" type="org.jahia.services.render.scripting.Script"--%>
<%--@elvariable id="scriptInfo" type="java.lang.String"--%>
<%--@elvariable id="workspace" type="java.lang.String"--%>
<%--@elvariable id="renderContext" type="org.jahia.services.render.RenderContext"--%>
<%--@elvariable id="currentResource" type="org.jahia.services.render.Resource"--%>
<%--@elvariable id="url" type="org.jahia.services.render.URLGenerator"--%>
<template:addResources type="javascript" resources="i18n/jahia-oauth-pingfederate-i18n_${renderContext.UILocale}.js"
                       var="i18nJSFile"/>
<c:if test="${empty i18nJSFile}">
    <template:addResources type="javascript" resources="i18n/jahia-oauth-pingfederate-i18n.js"/>
</c:if>
<template:addResources type="javascript" resources="pingFederate-connector-controller.js"/>
<template:addResources type="css" resources="styles.css"/>

<md-card ng-controller="PingFederateController as pingFederate" ng-init="pingFederate.init()">
    <div layout="row">
        <md-card-title flex>
            <md-card-title-text>
                <span class="md-headline" message-key="jcauthnt_pingFederateOAuthView"></span>
            </md-card-title-text>
        </md-card-title>
        <div flex layout="row" layout-align="end center">
            <md-button class="md-icon-button" ng-click="pingFederate.toggleCard()">
                <md-tooltip md-direction="top">
                    <span message-key="label.toggleSettings"></span>
                </md-tooltip>
                <md-icon ng-show="!pingFederate.expandedCard">keyboard_arrow_down</md-icon>
                <md-icon ng-show="pingFederate.expandedCard">keyboard_arrow_up</md-icon>
            </md-button>
        </div>
    </div>

    <md-card-content layout="column" ng-show="pingFederate.expandedCard">
        <form name="pingFederateForm">
            <md-switch ng-model="pingFederate.enabled">
                <span message-key="label.activate"></span>
            </md-switch>

            <div layout="row">
                <md-input-container flex>
                    <label message-key="label.domain"></label>
                    <input type="text" ng-model="pingFederate.domain" name="domain" required/>
                    <div ng-messages="pingFederateForm.domain.$error" role="alert">
                        <div ng-message="required" message-key="error.domain.required"></div>
                    </div>
                </md-input-container>

                <div flex="5"></div>

                <md-input-container flex>
                    <label message-key="label.apiKey"></label>
                    <input type="text" ng-model="pingFederate.apiKey" name="apiKey" required/>
                    <div ng-messages="pingFederateForm.apiKey.$error" role="alert">
                        <div ng-message="required" message-key="error.apiKey.required"></div>
                    </div>
                </md-input-container>

                <div flex="5"></div>

                <md-input-container flex>
                    <label message-key="label.apiSecret"></label>
                    <input type="password" ng-model="pingFederate.apiSecret" name="apiSecret" required/>
                    <div ng-messages="pingFederateForm.apiSecret.$error" role="alert">
                        <div ng-message="required" message-key="error.apiSecret.required"></div>
                    </div>
                </md-input-container>
            </div>

            <div layout="row">
                <md-input-container class="md-block" flex>
                    <label message-key="label.scope"></label>
                    <input type="text" ng-model="pingFederate.scope" name="scope"/>
                    <div class="hint" message-key="hint.scope"></div>
                </md-input-container>
            </div>

            <div layout="row">
                <md-input-container class="md-block" flex>
                    <label message-key="label.callbackURL"></label>
                    <input type="url" ng-model="pingFederate.callbackUrl" name="callbackUrl" required/>
                    <div class="hint" ng-show="pingFederateForm.callbackUrl.$valid" message-key="hint.callbackURL"></div>
                    <div ng-messages="pingFederateForm.callbackUrl.$error" ng-show="pingFederateForm.callbackUrl.$invalid"
                         role="alert">
                        <div ng-message="url" message-key="error.notAValidURL"></div>
                        <div ng-message="required" message-key="error.callbackURL.required"></div>
                    </div>
                </md-input-container>
            </div>

            <div layout="row">
                <md-input-container class="md-block" flex>
                    <label message-key="label.returnURL"></label>
                    <input type="url" ng-model="pingFederate.returnUrl" name="returnUrl" />
                    <div ng-messages="pingFederateForm.returnUrl.$error" ng-show="pingFederateForm.returnUrl.$invalid"
                         role="alert">
                        <div ng-message="url" message-key="error.notAValidURL"></div>
                    </div>
                </md-input-container>
            </div>
        </form>

        <md-card-actions layout="row" layout-align="end center">
            <md-button class="md-accent" ng-click="pingFederate.goToMappers()" ng-show="pingFederate.connectorHasSettings"
                       message-key="label.mappers"></md-button>
            <md-button class="md-accent" ng-click="pingFederate.saveSettings()" message-key="label.save"></md-button>
        </md-card-actions>

    </md-card-content>
</md-card>
