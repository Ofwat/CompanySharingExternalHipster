package uk.gov.ofwat.external.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;
import java.util.concurrent.TimeUnit;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import uk.gov.ofwat.external.domain.message.NotifyMessageTemplate;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(uk.gov.ofwat.external.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.User.class.getName() + ".companies", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.Company.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.Company.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(NotifyMessageTemplate.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.RegistrationRequest.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.DataCollection.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.PublishingStatus.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.DataBundle.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.DataInput.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.DataInput.class.getName() + ".reviewers", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataCollection.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyStatus.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.ReviewSignOff.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.SubmissionSignOff.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.DataFile.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataBundle.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataBundle.class.getName() + ".submissionSignOffs", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.InputType.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataInput.class.getName(), jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataInput.class.getName() + ".reviewSignOffs", jcacheConfiguration);
            cm.createCache(uk.gov.ofwat.external.domain.CompanyDataInput.class.getName() + ".submissionFiles", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
