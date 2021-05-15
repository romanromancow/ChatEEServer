package ua.kiev.prog;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetListServlet extends HttpServlet {

    private MessageList msgList = MessageList.getInstance();//ссылка на тотже общий месседж лист

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {//метод doGet
        String fromStr = req.getParameter("from");// шлем в него запросы с параметром from, fromStr - это вычитываем параметр from
        int from = 0;
        try {
            from = Integer.parseInt(fromStr);// парсим параметр в Integer
            if (from < 0) from = 0;//если пришло отрицательное значение скидываем его в ноль
        } catch (Exception ex) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);// если пришло, то что нельзя спарсить в интежер вызываем бедреквест
            return;
        }

        resp.setContentType("application/json");//ставим контент тайп апликейшонс джейсон (специлальный заголовок, который указывает клиенту что мы ему возвращаем

        String json = msgList.toJSON(from);//из общего списка все сообщения от from до посленего получаем ввиде json строки
        if (json != null) {//если что то получили то отправляем клиенту - отправка данных клиенту
            OutputStream os = resp.getOutputStream();// получаем отпутстрим
            byte[] buf = json.getBytes(StandardCharsets.UTF_8);//преобразуем gson строку в массив байтов
            os.write(buf);//этот массив байтов записываем в этот оутпутстрим
        }
        //PrintWriter pw = resp.getWriter();
        //pw.print(json);
    }
}
// по серверу общий комментарий - сервер - принимает сообщение - закидывает в общий список, либо вытаскивает нужное сообщение из общего списка и отправляет клиенту