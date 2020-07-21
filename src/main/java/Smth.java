import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Smth extends TelegramLongPollingBot {
    private final ArrayList<Long> chatIDsURL = new ArrayList<>();
    private int countP = 0;
    private int countM = 0;

    public void onUpdateReceived(Update update) {
        update.getUpdateId();

        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if (!chatIDsURL.contains(update.getMessage().getChatId())) { //новый пользователь

            if (text.equals("/event")) {

                SendMessage sendMessage = new SendMessage().setChatId(chatId);

                try {
                    sendMessage.setText("Введите событие : пример , ( событие 20:10 10/8/20 )");
                    execute(sendMessage);
                    chatIDsURL.add(update.getMessage().getChatId());
                }
                catch(TelegramApiException e){
                    e.printStackTrace();
                }
            }
        } else {
            SendMessage sendMessage = new SendMessage().setChatId(chatId);
            String textL = update.getMessage().getText();

            switch (text) {
                case "/event":
                    textL = "Введите событие : пример , ( событие 20:10 10/8/20 )";

                    break;
                case "+":
                    countP++;

                    try {
                        sendMessage.setText("За : " + countP);
                        execute(sendMessage);
                        chatIDsURL.add(update.getMessage().getChatId());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                    break;
                case "-":
                    countM++;

                    try {
                        sendMessage.setText("Проти : " + countM);
                        execute(sendMessage);
                        chatIDsURL.add(update.getMessage().getChatId());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    
                    break;
            }

            try {
                if(!(text.equals("+") || text.equals("-"))) {
                    sendMessage.setText("/event : " + textL);
                }
                execute(sendMessage);
                chatIDsURL.add(update.getMessage().getChatId());
            }
            catch(TelegramApiException e){
                e.printStackTrace();
            }

        }
    }

    public String getBotUsername(){
        return "taskm_bot by mrm";
    }

    @Override
    public String getBotToken() {
        return "867439913:AAH9ZX7dC1FItEZI8DD3aAeN9MzlzQdhVW8";
    }
}

//// event - smth