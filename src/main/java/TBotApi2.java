import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

public class TBotApi2  extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {
        String message_text = update.getMessage().getText();
        long chat_id = update.getMessage().getChatId();
        int count = 0;

        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
             if (message_text.equals("/event")) {

                SendMessage message = new SendMessage() // Create a message object object
                        .setChatId(chat_id)
                        .setText("You send /event");

                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>(); // створюємо ряд

                List<InlineKeyboardButton> rowInline = new ArrayList<>();  // об'єднуємо ряди

                rowInline.add(new InlineKeyboardButton().setText("Update message text").setCallbackData("update_msg_text"));
                rowInline.add(new InlineKeyboardButton().setText("New message text").setCallbackData("new_msg_text"));

                // Set the keyboard to the markup
                rowsInline.add(rowInline);
                // Add it to the message
                markupInline.setKeyboard(rowsInline);
                message.setReplyMarkup(markupInline);

                try {
                    execute(message); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }  else if (update.hasCallbackQuery()) {
                 // Set variables
                 String call_data = update.getCallbackQuery().getData();
                 long message_id = update.getCallbackQuery().getMessage().getMessageId();
                 long chat = update.getCallbackQuery().getMessage().getChatId();

                 if (call_data.equals("update_msg_text")) {
                     String answer = "Updated message text";
                     EditMessageText new_message = new EditMessageText()
                             .setChatId(chat)
                             .setMessageId(toIntExact(message_id))
                             .setText(answer);
                     try {
                         execute(new_message);
                     } catch (TelegramApiException e) {
                         e.printStackTrace();
                     }
                 }
                 if (call_data.equals("new_msg_text")) {
                     String answer = "New message text";
                     EditMessageText new_message = new EditMessageText()
                             .setChatId(chat)
                             .setMessageId(toIntExact(message_id))
                             .setText(answer);
                     try {
                         execute(new_message);
                     } catch (TelegramApiException e) {
                         e.printStackTrace();
                     }
                 }
             }

        } else  {

        }
    }

    @Override
    public String getBotUsername() {
        return "tbe1_bot by mrm";
    }

    @Override
    public String getBotToken() {
        return "867439913:AAH9ZX7dC1FItEZI8DD3aAeN9MzlzQdhVW8";
    }
}
