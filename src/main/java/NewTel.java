import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class NewTel extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {

        String f_id = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            String command = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();

//            SendMessage send = new SendMessage().setChatId(chat_id).setText(command);

            if(command.equals("/markup")) {

                SendMessage newm = new SendMessage().setChatId(chat_id).setText("Here is your keyboard");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                List<KeyboardRow> keyboardRowList = new ArrayList<>();

                KeyboardRow row = new KeyboardRow();

                row.add("MyPhoto");
                row.add("Row 1 Button 2");
                row.add("Row 1 Button 3");

                keyboardRowList.add(row);

                row = new KeyboardRow();

                row.add("Row 2 Button 1");
                row.add("Row 2 Button 2");
                row.add("Row 2 Button 3");

                keyboardRowList.add(row);

                keyboardMarkup.setKeyboard(keyboardRowList);

                newm.setReplyMarkup(keyboardMarkup);

                try {
                    execute(newm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }  else if (command.equals("MyPhoto")) {

                SendPhoto msg = new SendPhoto()
                        .setChatId(chat_id)
                        .setPhoto("AgACAgIAAxkBAAMWXwABlDcUURki_aUUWqg7DHgzd9b_AAI0rjEbNdcISHcZOQWEW5yFV0-AkS4AAwEAAwIAA3gAA2w2BQABGgQ")
                        .setCaption("Photo");

                try {
                    execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
        } else if (update.hasMessage() && update.getMessage().hasPhoto()) {

            long chat_id = update.getMessage().getChatId();

            List<PhotoSize> photos = update.getMessage().getPhoto();

             f_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getFileId();

            Integer w_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getWidth();

            Integer h_id = photos.stream()
                    .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                    .findFirst()
                    .orElse(null).getHeight();

            String caption = "file_id : " + f_id + "\nwidth_id : " + Integer.toString(w_id) + "\nheight_id : " + Integer.toString(h_id);

            SendPhoto mes = new SendPhoto().setChatId(chat_id).setPhoto(f_id).setCaption(caption);

            try{
                execute(mes);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }

    public String getBotUsername() {
        return "tbe1_bot by mrm";
    }

    public String getBotToken() {
        return "1201880051:AAGJNlzH-f55lzv-2giVgP-iTgk8DScCBY4";
    }
}
