package hello;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import org.springframework.stereotype.Service;

/**
 * Created by ashleycastiglione on 11/28/16.
 */

@Service
public class DynamoUser {

    DynamoDB db;

    DynamoDB getDB(){
        if (db == null){
            AmazonDynamoDBClient client = new AmazonDynamoDBClient(new
                    BasicAWSCredentials("n", "o"));
            client.withRegion(Regions.US_WEST_2);
            DynamoDB dynamoDB = new DynamoDB(client);
            return dynamoDB;
        }
        else return db;
    }

    public String getItem(String username, String password) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("user");

        Item item = table.getItem("username", username);
        if(item.getString("password").equals(password)) {
            return item.toJSONPretty();
        }
        else
            return "401 unauthorized";
    }

    public String postItem(String username, String password, String location){
        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("user");

        Item item = new Item()
                .withPrimaryKey("username", username)
                .withString("password", password)
                .withString("location", location);

        PutItemOutcome outcome = table.putItem(item);
        return this.getItem(username, password);

    }

}
