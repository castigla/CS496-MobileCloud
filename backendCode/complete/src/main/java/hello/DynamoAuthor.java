package hello;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import org.springframework.stereotype.Service;

import java.util.Iterator;

/**
 * Created by ashleycastiglione on 10/23/16.
 */
@Service
public class DynamoAuthor {

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

    public ItemCollection getItem(String author) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("book");

        Index index = table.getIndex("author-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("author = :v_author")
                .withValueMap(new ValueMap()
                        .withString(":v_author",author));

        ItemCollection<QueryOutcome> items = index.query(spec);
        return items;
    }

    public Item getAuthor(int id) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("author");

        Item item = table.getItem("id", id);

        return item;
    }

    public ItemCollection getItemByUserId(int id) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("author");

        Index index = table.getIndex("userId-index");

        QuerySpec spec = new QuerySpec()
                .withKeyConditionExpression("userId = :v_user")
                .withValueMap(new ValueMap()
                        .withInt(":v_user",id));

        ItemCollection<QueryOutcome> items = index.query(spec);
        return items;
    }

    public Item getAuthorUserId(int userId) {

        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("author");

        Item item = table.getItem("userId", userId);

        return item;
    }

    public String postAuthor(int id, String name, String gender, Long userId){
        DynamoDB dynamoDB = getDB();

        Table table = dynamoDB.getTable("author");

        Item item = new Item()
                .withPrimaryKey("id", id)
                .withString("name", name)
                .withString("gender", gender)
                .withLong("userId", userId);


        PutItemOutcome outcome = table.putItem(item);
        return outcome.getPutItemResult().toString();

    }
}
