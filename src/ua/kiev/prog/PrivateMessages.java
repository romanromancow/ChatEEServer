package ua.kiev.prog;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class PrivateMessages {
    Date date = new Date();
    String from;
    String to;
    String text;

    public PrivateMessages() {
    }

    public PrivateMessages(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toJSON (){
        Gson gson = new GsonBuilder().create();
        return gson.toJson(this);
    }

    public static PrivateMessages fromJSON (String s) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(s, PrivateMessages.class);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("[").append(date)
                .append(", From: ").append(from).append(", To: ").append(to)
                .append("] ").append(text)
                .toString();
    }
}
