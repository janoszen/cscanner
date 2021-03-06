package io.cscanner.core.provider.azure;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import com.microsoft.azure.management.Azure;
import com.microsoft.rest.LogLevel;
import io.cscanner.core.test.engine.CloudProviderConnection;
import io.cscanner.core.test.engine.HostDiscoveryClient;
import io.cscanner.core.test.engine.HostDiscoveryCloudProviderConnection;
import io.cscanner.core.rule.firewall.FirewallClient;
import io.cscanner.core.rule.firewall.FirewallConnection;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;

@ParametersAreNonnullByDefault
public class AzureConnection implements CloudProviderConnection, FirewallConnection, HostDiscoveryCloudProviderConnection {
    private final String connectionName;
    private final Azure azure;

    public AzureConnection(String connectionName, AzureConfiguration azureConfiguration) {
        this.connectionName = connectionName;

        ApplicationTokenCredentials credentials;
        if (azureConfiguration.key != null) {
            credentials = new ApplicationTokenCredentials(
                azureConfiguration.appId,
                azureConfiguration.tenantId,
                azureConfiguration.key,
                AzureEnvironment.AZURE
            );
        } else if (azureConfiguration.certificate != null) {
            credentials = new ApplicationTokenCredentials(
                azureConfiguration.appId,
                azureConfiguration.tenantId,
                azureConfiguration.certificate.getBytes(),
                azureConfiguration.certificatePassword,
                AzureEnvironment.AZURE
            );
        } else {
            throw new RuntimeException("Invalid configuration, either key or certificate must be provided.");
        }

        try {
            Azure.Authenticated azureBuilder = Azure
                .configure()
                .withLogLevel(LogLevel.BASIC)
                .authenticate(credentials);
            if (azureConfiguration.subscriptionId != null) {
                azure = azureBuilder.withSubscription(azureConfiguration.subscriptionId);
            } else {
                azure = azureBuilder.withDefaultSubscription();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getConnectionName() {
        return connectionName;
    }

    @Override
    public HostDiscoveryClient getHostDiscoveryClient() {
        return new AzureHostDiscoveryClient(
            connectionName,
            azure
        );
    }

    @Override
    public FirewallClient getFirewallClient() {
        return new AzureFirewallClient(
            connectionName,
            azure
        );
    }
}
