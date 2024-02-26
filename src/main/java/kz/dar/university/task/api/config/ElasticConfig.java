package kz.dar.university.task.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticConfig extends ElasticsearchConfiguration {

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo("tasks-39522c.kb.us-central1.gcp.cloud.es.io:9243")
                .usingSsl()
                .withBasicAuth("elastic", "pass")
                .build();
    }

}
