package cn.itcast.gallery.controller;

import cn.itcast.gallery.service.PaintingService;
import cn.itcast.gallery.utils.PageModel;
import cn.itcast.gallery.utils.StringUtil;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/page")
public class PaintingController extends HttpServlet {
    private PaintingService paintingService = new PaintingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //展示数据 把pageModel 给jsp显示
        String page = req.getParameter("p");
        String rows = req.getParameter("r");
        String category = req.getParameter("c");

        if (StringUtil.isEmpty(page)) {
            page = "1";
        }

        if (StringUtil.isEmpty(rows)) {
            rows = "6";
        }

        PageModel pageModel = paintingService.pagination(Integer.parseInt(page), Integer.parseInt(rows),category);
        req.setAttribute("pageModel", pageModel);
        req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
    }
}
