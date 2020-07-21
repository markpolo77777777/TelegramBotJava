import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.GetUpdates;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class CheckUTime  extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            String command = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
//            GetUpdates getUpdates = new GetUpdates().setLimit(1000);1

            if(command.equals("/start")) {

                SendMessage sm = new SendMessage().setChatId(chat_id).setText("Цей бот допоможе вам не сидіти цілий день у соцільних мережах\n" +
                        "просто обери цю команду /checkutime , та тицні на кнопку щоб обрати \n" +
                        "час який ти витратиш не на соц.мережі , а на щось корисне \n");

                try {
                    execute(sm);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if(update.getMessage().getText().equals("/checkutime")) {

                SendMessage send = new SendMessage().setChatId(chat_id).setText("Обери скільки годин ви хочете перебувати у соціальних мережах");

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

                List<KeyboardRow> keyboardRowList = new ArrayList<>();

                KeyboardRow row = new KeyboardRow();

                row.add("1 hour");
                row.add("2 hour");
                row.add("3 hour");
                row.add("4 hour");

                keyboardRowList.add(row);

                row = new KeyboardRow();

                row.add("5 hour");
                row.add("6 hour");
                row.add("7 hour");
                row.add("8 hour");

                keyboardRowList.add(row);

                row = new KeyboardRow();

                row.add("9 hour");
                row.add("10 hour");
                row.add("11 hour");
                row.add("12 hour");

                keyboardRowList.add(row);

                keyboardMarkup.setKeyboard(keyboardRowList);

                send.setReplyMarkup(keyboardMarkup);

                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }  else if (command.equals("1 hour")) {

                SendMessage send = new SendMessage().setChatId(chat_id).setText("Success. 1 hour");

                TimerTask task = new TimerTask() {
                    public void run() {

                        SendMessage sm = new SendMessage().setChatId(chat_id).setText("The End");

                        try {
                            execute(sm);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Timer timer = new Timer();

                int delay = 1000*60*60;
                timer.schedule(task, delay);

                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else if (command.equals("2 hour")) {

                SendMessage send = new SendMessage().setChatId(chat_id).setText("Success. 2 hour");

                TimerTask task = new TimerTask() {
                    public void run() {

                        SendMessage sm = new SendMessage().setChatId(chat_id).setText("The End");

                        try {
                            execute(sm);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Timer timer = new Timer();

                int delay = 1000*60*60*2;
                timer.schedule(task, delay);

                try {
                    execute(send);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }

        }

    }


    @Override
    public String getBotUsername() {
        return "CheckUTime by mrm";
    }

    @Override
    public String getBotToken() {
        return "1372487738:AAFPRZcvTq2RKQu5yHn2kbZ3GcsAsELNOto";
    }
}

/////  checkutime - smth
