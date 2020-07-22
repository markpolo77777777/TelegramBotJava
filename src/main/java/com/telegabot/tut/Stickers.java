package com.telegabot.tut;

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

public class Stickers extends TelegramLongPollingBot {
    private int count = 0;
    private int countM = 0;
    private String textL = "smth";
   //private final ArrayList<Long> chatIDsURL = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
        //update.getUpdateId();
        StringBuilder sb = new StringBuilder();

        if(update.hasMessage() && update.getMessage().hasText()) {

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
                   //chatIDsURL.add(update.getMessage().getChatId());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }  else  {
                SendMessage sendMessage = new SendMessage().setChatId(chat_id);
                textL = update.getMessage().getText();

                try {
                    sendMessage.setText(textL);
                    execute(sendMessage);
                    //chatIDsURL.add(update.getMessage().getChatId());
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
                   //chatIDsURL.add(update.getMessage().getChatId());
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
                    //chatIDsURL.add(update.getMessage().getChatId());
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "stickers by mrm";
    }

    @Override
    public String getBotToken() {
        return "867439913:AAH9ZX7dC1FItEZI8DD3aAeN9MzlzQdhVW8";
    }
}
