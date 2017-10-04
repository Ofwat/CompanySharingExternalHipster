package uk.gov.ofwat.external.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.gov.service.notify.NotificationClient;

@Configuration
public class GovNotifyServiceConfiguration {

    private final ApplicationProperties applicationProperties;

    public GovNotifyServiceConfiguration(ApplicationProperties applicationProperties){
        this.applicationProperties = applicationProperties;
    }

    @Bean
    public NotificationClient notificationClient() {
        return new NotificationClient(this.applicationProperties.getNotify().getApiKey());
    }
}
