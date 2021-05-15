package ua.kiev.prog;

import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MessageList {// класс реализирующий общий список хранящийся на сервере
    private static final MessageList msgList = new MessageList();//создаем переменную меседлист и кладем в нее предсозданный объект new MessegeList, объект создается один раз при загрузке класса в память - это сингелтон

    private final Gson gson;//переменная гсон для сериализация десериализации
    private final List<Message> list = new LinkedList<>();// список реализован как линкедлист

    public static MessageList getInstance() {// статический метод гетинстанс, всем кому нужен этот объект вызывают метод гетинстанс
        return msgList;//и получают ссылку msgList на созданный в памяти объект
    }

    private MessageList() {//конструктор месседжлиста - он приватный, его никто не может вызвать, никто не создат новый объект, кроме уже созданного
        gson = new GsonBuilder().create();
    }

    public synchronized void add(Message m) {//первый синхронайзд метод, так как сервлеты выполняются в разных потоках и поэтому могут одновременно работать с этим списком
        list.add(m);// метод add добавляет сообщение во внутренний список
    }

    public synchronized String toJSON(int n) {//второй синхронайзд метод. Метод toJSON берет все сообщения от n до последнего
        if (n >= list.size()) return null; // если n выходит за пределы списка, мы возвращем null
        return gson.toJson(new JsonMessages(list, n));// и выгружает их в объект JsonMessages (из общего листа (list) начина с n до последнего) то что получилось превращаем в через toJson в json строку
    }
}
