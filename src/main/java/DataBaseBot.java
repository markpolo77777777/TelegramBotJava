import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.logging.Level;

import static java.lang.Math.toIntExact;

public class DataBaseBot extends TelegramLongPollingBot{

    @Override
    public void onUpdateReceived(Update update) {

        String user_first_name = update.getMessage().getChat().getFirstName();
        String user_last_name = update.getMessage().getChat().getLastName();
        String user_username = update.getMessage().getChat().getUserName();
        long user_id = update.getMessage().getChat().getId();
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        SendMessage send = new SendMessage().setChatId(chat_id).setText(message_text);

        try {
            execute(send);
            check(user_first_name, user_last_name, toIntExact(user_id), user_username);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private String check(String first_name, String last_name, int user_id, String username) {
        // Set loggers
        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);
        MongoClientURI connectionString = new MongoClientURI("mongodb+srv://new_user_1:<R28kPLM54wAmsPw>@mytest.ickxg.mongodb.net/<MyTest>?retryWrites=true&w=majority");
        MongoClient mongoClient = new MongoClient(connectionString);
        MongoDatabase database = mongoClient.getDatabase("MyTest");
        MongoCollection<Document> collection = database.getCollection("new_user_1");
        long found = collection.count(Document.parse("{id : " + user_id + "}"));
        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
            return "no_exists";
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
            return "exists";
        }


    }

    @Override
    public String getBotUsername() {
        return null;
    }

    @Override
    public String getBotToken() {
        return "867439913:AAH9ZX7dC1FItEZI8DD3aAeN9MzlzQdhVW8";
    }
}

////  heroku , mongodb , webhook , github rep //// - youtube