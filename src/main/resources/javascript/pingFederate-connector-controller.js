(function () {
    'use strict';

    angular.module('JahiaOAuthApp').controller('PingFederateController', PingFederateController);
    PingFederateController.$inject = ['$location', 'settingsService', 'helperService', 'i18nService'];

    function PingFederateController($location, settingsService, helperService, i18nService) {
        // must mach value in the plugin in pom.xml
        i18nService.addKey(jcauthpingfederatei18n);

        const CONNECTOR_SERVICE_NAME = 'PingFederateApi';

        const vm = this;

        vm.saveSettings = () => {
            // Value can't be empty
            if (!vm.domain || !vm.apiKey || !vm.apiSecret || !vm.callbackUrl || vm.callbackUrl.trim() === '') {
                helperService.errorToast(i18nService.message('label.missingMandatoryProperties'));
                return false;
            }

            // the node name here must be the same as the one in your spring file
            settingsService.setConnectorData({
                connectorServiceName: CONNECTOR_SERVICE_NAME,
                properties: {
                    enabled: vm.enabled,
                    apiKey: vm.apiKey,
                    apiSecret: vm.apiSecret,
                    domain: vm.domain,
                    callbackUrl: vm.callbackUrl,
                    returnUrl: vm.returnUrl,
                    scope: vm.scope
                }
            }).success(() => {
                vm.connectorHasSettings = true;
                helperService.successToast(i18nService.message('label.saveSuccess'));
            }).error(data => helperService.errorToast(`${i18nService.message('jcauthnt_pingFederateOAuthView')}: ${data.error}`));
        };

        vm.goToMappers = () => $location.path(`/mappers/${CONNECTOR_SERVICE_NAME}`);

        vm.toggleCard = () => vm.expandedCard = !vm.expandedCard;

        vm.init = () => {
            settingsService.getConnectorData(CONNECTOR_SERVICE_NAME, ['enabled', 'domain', 'apiKey', 'apiSecret', 'callbackUrl', 'returnUrl', 'scope'])
                .success(data => {
                    if (data && !angular.equals(data, {})) {
                        vm.expandedCard = vm.connectorHasSettings = true;
                        vm.enabled = data.enabled;
                        vm.domain = data.domain;
                        vm.apiKey = data.apiKey;
                        vm.apiSecret = data.apiSecret;
                        vm.callbackUrl = data.callbackUrl || '';
                        vm.returnUrl = data.returnUrl || '';
                        vm.scope = data.scope;
                    } else {
                        vm.connectorHasSettings = false;
                        vm.enabled = false;
                    }
                })
                .error(data => helperService.errorToast(`${i18nService.message('jcauthnt_pingFederateOAuthView')}: ${data.error}`));
        };
    }
})();
