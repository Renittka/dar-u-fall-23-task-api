package kz.dar.university.task.api.config;

import co.elastic.clients.util.ApiTypeHelper;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {

        try (ApiTypeHelper.DisabledChecksHandle h =
                     ApiTypeHelper.DANGEROUS_disableRequiredPropertiesCheck(true)) {
            return ClientConfiguration.builder()
                    .connectedTo("test".split(","))
                    .build();
        }

    }

}
