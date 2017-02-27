package hello;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by ashleycastiglione on 10/21/16.
 */

@Service
public class DynamoBook {

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

    public Item getItem(int id) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Item item = table.getItem("id", id);

        return item;
    }
    //check to see if item exists id
    //return error response if it does
    //if it doesn't exist, continue on

    public String postItem(int id, String title, String author, Long publicationYear,
                           String bookType, Boolean bookRead, Long rating, String username){
        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Item item = new Item()
                .withPrimaryKey("id", id)
                .withString("title", title)
                .withString("author", author)
                .withLong("publicationYear", publicationYear)
                .withString("bookType", bookType)
                .withBoolean("bookRead", bookRead)
                .withLong("rating", rating)
                .withString("username", username);


        PutItemOutcome outcome = table.putItem(item);
        return outcome.getPutItemResult().toString();

    }

    public String putItem(int id, String title){
        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Item item = table.getItem("id", id);
        item.with("title",title);

        PutItemOutcome outcome = table.putItem(item);
        return outcome.getPutItemResult().toString();

    }

    public String deleteItem(int id){

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        DeleteItemOutcome outcome = table.deleteItem("id", id);

        return outcome.getDeleteItemResult().toString();

    }

    public String getItems() {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Map<String, Object> expressionAttributeValues = new HashMap<String, Object>();
        expressionAttributeValues.put(":id", 100);

        ItemCollection<ScanOutcome> items = table.scan(
                "id < :id", //FilterExpression
                "id, title, author, publicationYear, bookType, bookRead, rating", //ProjectionExpression
                null, //ExpressionAttributeNames - not used in this example
                expressionAttributeValues);
        List<String> itemsList = new ArrayList();
        StringBuffer response = new StringBuffer("[");

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            response.append(iterator.next().toJSONPretty());
            if(iterator.hasNext()) {
                response.append(",");
            }
        }
        response.append("]");

        return response.toString();
    }

    public String getItemsByUsername(String username) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Index index = table.getIndex("username-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("username = :v_user")
                .withValueMap(new ValueMap()
                        .withString(":v_user", username));

        ItemCollection<QueryOutcome> items = index.query(spec);
        StringBuffer response = new StringBuffer("[");

        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            response.append(iterator.next().toJSONPretty());
            if(iterator.hasNext()) {
                response.append(",");
            }
        }
        response.append("]");

        return response.toString();

    }
}
