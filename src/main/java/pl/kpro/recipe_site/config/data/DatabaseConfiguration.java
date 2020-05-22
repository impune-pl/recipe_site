package pl.kpro.recipe_site.config.data;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.UuidRepresentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * @author Krzysztof 'impune_pl' Prorok <Krzysztof1397@gmail.com>
 */
@Configuration
public class DatabaseConfiguration extends AbstractMongoClientConfiguration
{
    public @Bean MongoClient mongoClient()
    {
        MongoClientSettings settings = MongoClientSettings.builder().uuidRepresentation(UuidRepresentation.STANDARD).applyConnectionString(new ConnectionString("mongodb://localhost:27017")).build();
        return MongoClients.create(settings);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), this.getDatabaseName());
    }



    @Override
    public void configureClientSettings(MongoClientSettings.Builder builder) {
        builder.uuidRepresentation(UuidRepresentation.STANDARD);
    }

    @Override
    protected String getDatabaseName()
    {
        return "recipe_site";
    }
}
