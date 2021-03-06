package io.cscanner.core.rule.firewall;

import io.cscanner.core.test.engine.RuleBuilder;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FirewallPublicServiceProhibitedRuleBuilder implements RuleBuilder<FirewallPublicServiceProhibitedRule, FirewallConnection, FirewallPublicServiceProhibitedRule.Options> {
    @Override
    public String getType() {
        return FirewallPublicServiceProhibitedRule.RULE;
    }

    @Override
    public Class<FirewallConnection> getConnectionType() {
        return FirewallConnection.class;
    }

    @Override
    public Class<FirewallPublicServiceProhibitedRule.Options> getConfigurationType() {
        return FirewallPublicServiceProhibitedRule.Options.class;
    }

    @Override
    public FirewallPublicServiceProhibitedRule create(FirewallPublicServiceProhibitedRule.Options options) {
        return new FirewallPublicServiceProhibitedRule(
            options
        );
    }
}
