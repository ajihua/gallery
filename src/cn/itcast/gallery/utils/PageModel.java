package cn.itcast.gallery.utils;

import cn.itcast.gallery.entity.Painting;

import java.util.List;

public class PageModel {
    private int page;//当前页
    private int totalPages;//总页数
    private int rows;//当前页显示记录数
    private int totalRows;//总记录数
    private int pageStartRow;//当前页开始记录数
    private int pageEndRow;//当前页结束记录数
    private boolean hasNextPage;//是否有下一页
    private boolean hasPreviousPage;//是否有上一页
    private List<Painting> pageData;//当前页面数据

    //创建对象 就初始化数据
    public PageModel(List<Painting> data, int page, int rows) {
        this.page = page;
        this.rows = rows;
        totalRows = data.size();
        //总记录/页显示记录 int整除还是int  要用浮点 向上取整
        totalPages = new Double(Math.ceil(totalRows / (rows * 1f))).intValue();
        //pageStartRow pageEndRow 意为 当前页数据的起始位置
        pageStartRow = (page - 1) * rows;
        pageEndRow = page * rows;
        //最后一页可能会越界
        if (pageEndRow > totalRows)
            pageEndRow = totalRows;
        pageData = data.subList(pageStartRow, pageEndRow);//前闭后开

        if (page > 1)
            hasPreviousPage = true;
        else
            hasPreviousPage = false;

        if (page < totalPages)
            hasNextPage = true;
        else
            hasNextPage = false;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getPageStartRow() {
        return pageStartRow;
    }

    public void setPageStartRow(int pageStartRow) {
        this.pageStartRow = pageStartRow;
    }

    public int getPageEndRow() {
        return pageEndRow;
    }

    public void setPageEndRow(int pageEndRow) {
        this.pageEndRow = pageEndRow;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public List<Painting> getPageData() {
        return pageData;
    }

    public void setPageData(List<Painting> pageData) {
        this.pageData = pageData;
    }
}
