package ua.kiev.prog;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class JsonMessages {// это вспомогательный класс, что бы сообщения возвращались в виде массива
    private final List<Message> list = new ArrayList<>();// Arraylist обычных messages

    public JsonMessages(List<Message> sourceList, int fromIndex) {//конструктор который позволяет, часть сообщений из одного list скопировать в другой
        for (int i = fromIndex; i < sourceList.size(); i++)
            list.add(sourceList.get(i));
    }

}
