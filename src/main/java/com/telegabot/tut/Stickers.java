package com.telegabot.tut;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static java.lang.Math.toIntExact;

public class Stickers extends TelegramLongPollingBot {
    private int count = 0;
    private int countM = 0;
    private String textL = "smth";

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();
            String text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

            if(text.equals("/event")) {

                SendMessage send = new SendMessage().setChatId(chat_id).setText("smth");

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

                List<List<InlineKeyboardButton>> rows = new ArrayList<>();

                List<InlineKeyboardButton> row = new ArrayList<>();

                row.add(new InlineKeyboardButton().setText(Icon.CHECK.get() +  count).setCallbackData("i_am_going"));
                row.add(new InlineKeyboardButton().setText(Icon.NOT.get() + countM).setCallbackData("i_am_pass"));

                rows.add(row);

                inlineKeyboardMarkup.setKeyboard(rows);

                send.setReplyMarkup(inlineKeyboardMarkup);

                try {
                    execute(send);
                    check(user_first_name, user_last_name, toIntExact(user_id), user_username);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }  else  {
                SendMessage sendMessage = new SendMessage().setChatId(chat_id);
                textL = update.getMessage().getText();

                try {
                    sendMessage.setText(textL);
                    execute(sendMessage);
                }
                catch(TelegramApiException e){
                    e.printStackTrace();
                }

            }

        } else if (update.hasCallbackQuery()) {

            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat = update.getCallbackQuery().getMessage().getChatId();

            if(call_data.equals("i_am_going")) {

                count++;

                EditMessageText edit = new EditMessageText()
                        .setChatId(chat)
                        .setMessageId(toIntExact(message_id))
                        .setText(Icon.CHECK.get() + count);

                try {
                    execute(edit);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if(call_data.equals("i_am_pass")) {

                countM++;

                EditMessageText edit = new EditMessageText()
                        .setChatId(chat)
                        .setMessageId(toIntExact(message_id))
                        .setText(Icon.NOT.get() + countM);

                try {
                    execute(edit);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void check(String first_name, String last_name, int user_id, String username) {

        java.util.logging.Logger.getLogger("org.mongodb.driver").setLevel(Level.OFF);

        MongoClientURI connectionString = new MongoClientURI("Your mongoDB password");

        MongoClient mongoClient = new MongoClient(connectionString);

        MongoDatabase database = mongoClient.getDatabase("Your Database");

        MongoCollection<Document> collection = database.getCollection("Your user");

        long found = collection.count(Document.parse("{id : " + user_id + "}"));

        if (found == 0) {
            Document doc = new Document("first_name", first_name)
                    .append("last_name", last_name)
                    .append("id", user_id)
                    .append("username", username);
            collection.insertOne(doc);
            mongoClient.close();
            System.out.println("User not exists in database. Written.");
        } else {
            System.out.println("User exists in database.");
            mongoClient.close();
        }
    }

    @Override
    public String getBotUsername() {
        return "stickers by mrm";
    }

    @Override
    public String getBotToken() {
        return "token";
    }
}
