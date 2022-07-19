package cn.itcast.gallery.controller;

import cn.itcast.gallery.entity.Painting;
import cn.itcast.gallery.service.PaintingService;
import cn.itcast.gallery.utils.PageModel;
import cn.itcast.gallery.utils.StringUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.UUID;

@WebServlet("/management")
public class ManagementController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //method表示 要处理哪种请求
        String method = req.getParameter("method");
        if ("list".equals(method)) {
            list(req, resp);
        } else if ("show_create".equals(method)) {
            showCreate(req, resp);
        } else if ("create".equals(method)) {
            create(req, resp);
        } else if ("show_update".equals(method)) {
            showUpdate(req, resp);
        } else if ("update".equals(method)) {
            update(req, resp);
        } else if ("delete".equals(method)) {
            delete(req, resp);
        }

    }

    /**
     * ajax请求 获得id 删除油画数据
     * 删除可能失败try一下 返回给客户端json
     * @param req
     * @param resp
     * @throws IOException
     */
    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();
        PaintingService paintingService = new PaintingService();
        try {
            paintingService.delete(Integer.parseInt(id));
            //{"result":"ok"}
            out.println("{\"result\":\"ok\"}");
        }catch (Exception e){
            e.printStackTrace();
            out.println("{\"result\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * 与create方法逻辑类似
     * 多了几个隐藏input page表示当前页更新后停留在当前页
     * id 更新的油画id
     * isPreviewModified 表示上传文件是否修改了 0未修改  1修改了要重新上传
     * @param req
     * @param resp
     */
    protected void update(HttpServletRequest req, HttpServletResponse resp) {
        //初始化FileItemFactory ServletFileUpload
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sf = new ServletFileUpload(factory);
        PaintingService paintingService = new PaintingService();
        Painting painting = new Painting();
        //表示上传文件是否修改了 0未修改  1修改了要重新上传
        Integer isPreviewModified = 0;
        String page = null;
        try {
            //FileItem表示所有表单input
            List<FileItem> fi = sf.parseRequest(req);
            for (FileItem item : fi) {
                //isFormField 判断是什么类型输入项   文件还是普通
                if (item.isFormField()) {
                    //普通输入项getFieldName是input项的name值
                    //getString 是input项的value
                    switch (item.getFieldName()) {
                        case "pname":
                            painting.setPname(item.getString("utf-8"));
                            break;
                        case "id":
                            painting.setId(Integer.parseInt(item.getString()));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(item.getString()));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(item.getString()));
                            break;
                        case "description":
                            painting.setDescription(item.getString("utf-8"));
                            break;
                        case "page":
                            page = item.getString();
                            break;
                        case "isPreviewModified":
                            isPreviewModified = Integer.parseInt(item.getString());
                            break;
                        default:
                            break;
                    }
                } else {
                    //文件输入项
                    if (isPreviewModified == 1) {
                        String path = req.getServletContext().getRealPath("/upload");
                        String fileName = UUID.randomUUID().toString();
                        //getName()文件的文件名
                        String suffix = item.getName().substring(item.getName().lastIndexOf("."));
                        item.write(new File(path, fileName + suffix));
                        //System.out.println(item.getFieldName()+"---"+item.getName());
                        painting.setPreview("/upload/" + fileName + suffix);
                    }
                }
            }
            paintingService.update(painting, isPreviewModified);
            if (StringUtil.isEmpty(page))
                page = "1";
            resp.sendRedirect("/gallery/management?method=list&p=" + page);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示更新jsp页面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //id 更新的油画id  page表示在分页的第几页 目的修改油画数据后还在本页
        String id = req.getParameter("id");
        String page = req.getParameter("page");
        PaintingService paintingService = new PaintingService();
        Painting painting = paintingService.findById(Integer.parseInt(id));
        req.setAttribute("painting", painting);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/WEB-INF/jsp/update.jsp").forward(req, resp);
    }

    /**
     * 提交表单处理逻辑 提取表单数据封装painting
     * 具体注释看update方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sf = new ServletFileUpload(factory);
        PaintingService paintingService = new PaintingService();
        try {
            List<FileItem> formData = sf.parseRequest(req);
            Painting painting = new Painting();
            for (FileItem item : formData) {
                //判断是否是文件输入项
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "pname":
                            painting.setPname(item.getString("UTF-8"));
                            break;
                        case "category":
                            painting.setCategory(Integer.parseInt(item.getString("UTF-8")));
                            break;
                        case "price":
                            painting.setPrice(Integer.parseInt(item.getString("UTF-8")));
                            break;
                        case "description":
                            painting.setDescription(item.getString("UTF-8"));
                            break;
                        default:
                            break;
                    }
                } else {
                    String path = req.getServletContext().getRealPath("/upload");
                    System.out.println(path);
                    String filename = UUID.randomUUID().toString();
                    String suffix = item.getName().substring(item.getName().lastIndexOf("."));
                    item.write(new File(path, filename + suffix));
                    painting.setPreview("/upload/" + filename + suffix);
                }
            }
            paintingService.create(painting);
            resp.sendRedirect("/gallery/management?method=list");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示新增油画jsp
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void showCreate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/create.jsp").forward(req, resp);
    }


    /**
     * 数据分页处理
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PaintingService paintingService = new PaintingService();
        String page = req.getParameter("p");
        String rows = req.getParameter("r");

        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        if (StringUtil.isEmpty(rows)) {
            rows = "6";
        }
        PageModel pageModel = paintingService.pagination(Integer.parseInt(page), Integer.parseInt(rows));
        req.setAttribute("pageModel", pageModel);
        req.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
