package cn.itcast.gallery.service;

import cn.itcast.gallery.dao.PaintingDao;
import cn.itcast.gallery.entity.Painting;
import cn.itcast.gallery.utils.PageModel;

public class PaintingService {
    private PaintingDao paintingDao = new PaintingDao();

    /**
     * pagination数据分页查询
     * @param page 当前页
     * @param rows 当前页记录数
     * @param category 分类标记
     * @return
     */
    public PageModel pagination(int page, int rows, String... category) {
        //业务逻辑的处理 小技巧可变参数
        if (rows == 0)
            throw new RuntimeException("无效的记录数");

        if(category.length==0||category[0]==null)
            return paintingDao.pagination(page, rows);
        else
            return paintingDao.pagination(Integer.parseInt(category[0]),page,rows);
    }

    /**
     * 添加油画
     * @param painting 油画对象
     */
    public void create(Painting painting){
        paintingDao.create(painting);
    }

    /**
     *根据油画id查找油画
     * @param id
     * @return
     */
    public Painting findById(Integer id){
        Painting painting = paintingDao.findById(id);
        if(painting==null){
            throw new RuntimeException("id="+id+"无效的ID");
        }
        return painting;
    }

    /**
     * 更新油画  重点---在原有数据基础上覆盖  因为文件可能不会更改  新的数据放进去uuid文件找不到的问题
     * 要在原有基础上覆盖数据  除非能保证新的Painting包含了所有更新的数据
     * @param painting 新的油画数据
     * @param isPreviewModified 1 表示修改了上传文件 0表示未修改
     */
    public void update(Painting painting,Integer isPreviewModified){
        Painting oldPainting = paintingDao.findById(painting.getId());
        oldPainting.setDescription(painting.getDescription());
        oldPainting.setPname(painting.getPname());
        oldPainting.setPrice(painting.getPrice());
        oldPainting.setCategory(painting.getCategory());
        if(isPreviewModified==1){
            oldPainting.setPreview(painting.getPreview());
        }
        paintingDao.update(oldPainting);
    }

    /**
     * 指定id删除油画
     * @param id 删除油画的id
     */
    public void delete(Integer id){
        paintingDao.delete(id);
    }
}
