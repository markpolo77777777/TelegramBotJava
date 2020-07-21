import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegB extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) {

        String command = update.getMessage().getText();

        SendMessage message = new SendMessage();

        message.setText("Оберіть команду : /firstname - виведе на екран ваше ім'я; /lastname - виведе на екран вашу фамілію; /info - виведе інфу");

        if(command.equals("/firstname")) {
            System.out.println(update.getMessage().getFrom().getFirstName());
            message.setText(update.getMessage().getFrom().getFirstName());
        }
        if(command.equals("/lastname")) {
            System.out.println(update.getMessage().getFrom().getLastName());
            message.setText(update.getMessage().getFrom().getLastName());
        }
        if(command.equals("/info")) {
            message.setText(update.getMessage().getLocation().getLatitude().toString());
        }

        message.setChatId(update.getMessage().getChatId());

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    public String getBotUsername() {
        return "SanyaPidor by Mark";
    }

    public String getBotToken() {
        return "1176367606:AAHOVNPs_7qMALWeVpyUVxeoVikgg0_6B2w";
    }
}