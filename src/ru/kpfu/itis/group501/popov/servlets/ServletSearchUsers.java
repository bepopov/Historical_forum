package ru.kpfu.itis.group501.popov.servlets;

import ru.kpfu.itis.group501.popov.repository.CustomRepository;
import ru.kpfu.itis.group501.popov.repository.CustomStatement;
import ru.kpfu.itis.group501.popov.models.User;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ServletSearchUsers")
public class ServletSearchUsers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String q = request.getParameter("q");
        CustomStatement cs = new CustomStatement();
        Map<String, List<Object>> map = CustomRepository.do_sql(cs.select(User.class).like("username", q));
        if (map != null) {
            List list = map.get("User");
            JSONArray ja = new JSONArray();
            for (int i = 0; i < list.size(); i++) {
                User new_user = (User) list.get(i);
                ja.put(new_user.get("username"));
            }
            JSONObject jo = new JSONObject();
            jo.put("result", ja);
            response.setContentType("text/json");
            response.getWriter().println(jo.toString());
        }
        else {
            response.getWriter().println("Not found");
        }
    }
}