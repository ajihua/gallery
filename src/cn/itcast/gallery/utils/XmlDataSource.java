package cn.itcast.gallery.utils;

import cn.itcast.gallery.entity.Painting;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class XmlDataSource {
    private static List<Painting> data = new ArrayList<>();//所有数据
    private static String xmlPath;//xml路径

    static {
        xmlPath = XmlDataSource.class.getResource("/painting.xml").getPath();
        reload();//加载xml数据到集合中(内存)
    }

    /**
     * 加载xml数据到data集合中
     */
    private static void reload() {
        try {
            xmlPath = URLDecoder.decode(xmlPath, "UTF-8");
            SAXReader reader = new SAXReader();
            Document document = reader.read(xmlPath);
            //通过xpath获取指定节点
            List<Node> nodes = document.selectNodes("/root/painting");
            //每次加载要清空data
            data.clear();
            for (Node node : nodes) {
                Element element = (Element) node;
                Painting painting = new Painting();
                painting.setId(Integer.parseInt(element.attributeValue("id")));
                painting.setPname(element.elementText("pname"));
                painting.setCategory(Integer.parseInt(element.elementText("category")));
                painting.setPrice(Integer.parseInt(element.elementText("price")));
                painting.setPreview(element.elementText("preview"));
                painting.setDescription(element.elementText("description"));
                data.add(painting);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 得到data集合
     *
     * @return
     */
    public static List<Painting> getData() {
        return data;
    }

    /**
     * 实现xml追加数据，要更新data   内存和xml数据保持一致
     *
     * @param painting 追加油画对象
     */
    public static void append(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(xmlPath);
            Element root = document.getRootElement();
            Element p = root.addElement("painting");
            p.addAttribute("id", data.size() + "");
            p.addElement("pname").setText(painting.getPname());
            p.addElement("category").setText(painting.getCategory() + "");
            p.addElement("price").setText(painting.getPrice() + "");
            p.addElement("preview").setText(painting.getPreview());
            p.addElement("description").setText(painting.getDescription());
            writer = new OutputStreamWriter(new FileOutputStream(xmlPath), "UTF-8");
//            System.out.println(xmlPath);
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //crud后重新加载
            reload();
        }
    }

    /**
     * 更新数据
     *
     * @param painting
     */
    public static void update(Painting painting) {
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(xmlPath);
            //节点路径[@属性名=属性值] 得到更新的painting节点
            // /root/painting[@id=x]
            List<Node> nodes = document.selectNodes("/root/painting[@id=" + painting.getId() + "]");
            if (nodes.size() == 0)
                throw new RuntimeException("id=" + painting.getId() + "油画编号不存在");
            Element p = (Element) nodes.get(0);
            //通过selectSingleNode 获取子节点
            p.selectSingleNode("pname").setText(painting.getPname());
            p.selectSingleNode("category").setText(painting.getCategory() + "");
            p.selectSingleNode("price").setText(painting.getPrice() + "");
            p.selectSingleNode("preview").setText(painting.getPreview());
            p.selectSingleNode("description").setText(painting.getDescription());
            writer = new OutputStreamWriter(new FileOutputStream(xmlPath), "utf-8");
            document.write(writer);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //load  xml
            reload();
        }
    }

    /**
     * 删除指定id油画
     * @param id 删除的油画
     */
    public static void delete(Integer id){
        SAXReader reader = new SAXReader();
        Writer writer = null;
        try {
            Document document = reader.read(xmlPath);
            //节点路径[@属性名=属性值] 得到更新的painting节点
            // /root/painting[@id=x]
            List<Node> nodes = document.selectNodes("/root/painting[@id=" + id + "]");
            if (nodes.size()==0){
                throw new RuntimeException("id="+id+"油画id不存在");
            }
            Element p = (Element) nodes.get(0);
            p.getParent().remove(p);
            writer = new OutputStreamWriter(new FileOutputStream(xmlPath),"UTF-8");
            document.write(writer);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(writer!=null){
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reload();
            }
        }
    }


    public static void main(String[] args) {
        System.out.println(xmlPath);
    }

}
