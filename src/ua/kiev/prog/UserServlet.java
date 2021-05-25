package ua.kiev.prog;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserList userList = UserList.getInstance();
    private List <User> users = userList.getListuser();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        byte[] buf = requestBodyToArray(req);//requestBodyToArray из запроса (req) все содержимое возвращает в виде массива байтов
        String bufStr = new String(buf, StandardCharsets.UTF_8);//преобразование этого массива (buf)  спомощью стринга в строку, потому что должна прилететь строка с json

        User user = User.fromJSON(bufStr);// из json пытаемся десериализировать объект юзер (десериализация, закидываем в список)
        if (user != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getLogin().equals(user.getLogin()) && users.get(i).getPassword().equals(user.getPassword())) {// проверка логина и пароля юзера с юзерами
                    resp.setStatus(HttpServletResponse.SC_CREATED);
                    return;
                } else if (users.get(i).getLogin().equals(user.getLogin()) && (!users.get(i).getPassword().equals(user.getPassword()))) {
                    resp.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
                    return;
                }
            }
            userList.add(user);//если получилось закидываем его в наш общий список userList
        }else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);//если нет то возращаем bad request
    }

    private byte[] requestBodyToArray(HttpServletRequest req) throws IOException {//то что под HTTP заголовками можно вычитать через HttpServletRequest
        InputStream is = req.getInputStream();//это содержимое (что можно вычитать доступно ввиде стрима - вызываем getInputStream получаем InputStream для чтения
        ByteArrayOutputStream bos = new ByteArrayOutputStream();//резиновый массив
        byte[] buf = new byte[10240];
        int r;

        do {
            r = is.read(buf);//запускаем цикл read в buf пытается считать столько сколько влезет
            if (r > 0)
                bos.write(buf, 0, r);// если хоть что то прочитанное то r вернет больше нуля через bos.write записываем прочитанные байты в ByteArrayOutputStream
        } while (r != -1);//читаем пока рид не вернет -1

        return bos.toByteArray();//когда все вычитали toByteArray возвращает все что мы накопили в ByteArrayOutputStream  в виде одного массива
    }
}

