package cn.itcast.gallery.dao;

import cn.itcast.gallery.entity.Painting;
import cn.itcast.gallery.utils.PageModel;
import cn.itcast.gallery.utils.XmlDataSource;

import java.util.ArrayList;
import java.util.List;

public class PaintingDao {

    /**
     * pagination 数据分页查询
     * @param page 当前页
     * @param rows 当前页显示记录数
     * @return 分页对象
     */
    public PageModel pagination(int page,int rows){
        List<Painting> data = XmlDataSource.getData();
        PageModel pageModel = new PageModel(data,page,rows);
        return pageModel;
    }

    /**
     * pagination 分类分页查询
     * @param category 分类标记
     * @param page 当前页
     * @param rows 当前页显示记录数
     * @return 分类PageModel对象
     */
    public PageModel pagination(int category,int page,int rows){
        List<Painting> data = XmlDataSource.getData();
        List<Painting> categoryList = new ArrayList<>();
        for (Painting p : data){
            if(p.getCategory()==category){
                categoryList.add(p);
            }
        }
        PageModel pageModel = new PageModel(categoryList,page,rows);
        return pageModel;
    }

    /**
     * 数据新增
     * @param painting 新增对象
     */
    public void create(Painting painting){
        XmlDataSource.append(painting);
    }

    /**
     * 根据id 查找油画
     * @param id
     * @return
     */
    public Painting findById(Integer id){
        List<Painting> data = XmlDataSource.getData();
        Painting painting = null;
        for (Painting p : data){
            if(p.getId().equals(id)){
                painting = p;
            }
        }
        return painting;
    }

    /**
     * 删除指定id油画
     * @param id 删除油画的id
     */
    public void delete(Integer id){
        XmlDataSource.delete(id);
    }

    /**
     * 更新油画
     * @param painting
     */
    public void update(Painting painting){
        XmlDataSource.update(painting);
    }
}
