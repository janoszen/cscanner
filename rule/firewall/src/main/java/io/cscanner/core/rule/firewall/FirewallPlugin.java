package io.cscanner.core.rule.firewall;

import io.cscanner.core.test.engine.Plugin;
import io.cscanner.core.test.engine.RuleBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@ParametersAreNonnullByDefault
public class FirewallPlugin implements Plugin {
    @Override
    public List<RuleBuilder<?, ?, ?>> getSupportedRules() {
        return Collections.singletonList(
                new FirewallPublicServiceProhibitedRuleBuilder()
        );
    }
}
