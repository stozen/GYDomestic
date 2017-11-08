package com.gy.util;

import java.util.List;

import com.gy.model.User;

/**
 * @author Zhuxiaodong
 * @version 0.0.1
 * @date 2017-10-31
 * @introduce 这是分页工具类
 */
public class Page {

	 // 结果集
    private List<?> list;

    // 查询记录总数
    private int totalRecords;

    // 每页多少条记录
    private int pageSize;

    // 第几页
    private int pageNo;

    /**
     * @return 总页数
     * */
    public int getTotalPages(){
        return (totalRecords+pageSize-1)/pageSize;
    }

    /**
     * 计算当前页开始记录
     * @param pageSize 每页记录数
     * @param currentPage 当前第几页
     * @return 当前页开始记录号
     */
    public int countOffset(int currentPage,int pageSize){
        int offset = pageSize*(currentPage-1);
        return offset;
    }

    /**
     * @return 首页
     * */
    public int getTopPageNo(){
        return 1;
    }

    /**
     * @return 上一页
     * */
    public int getPreviousPageNo(){
        if(pageNo<=1){
            return 1;
        }
        return pageNo-1;
    }

    /**
     * @return 下一页
     * */
    public int getNextPageNo(){
        if(pageNo>=getBottomPageNo()){
            return getBottomPageNo();
        }
        return pageNo+1;
    }

    /**
     * @return 尾页
     * */
    public int getBottomPageNo(){
        return getTotalPages();
    }


    /**
     * 获得新闻的get方法
     * @return
     */
    public List<?> getList() {
        return list;
    }

    /**
     * 设置新闻的set方法
     * @param list
     */
    public void setList(List<?> list) {
        this.list = list;
    }

    /**
     * 获得总的记录数
     * @return
     */
    public int getTotalRecords() {
        return totalRecords;
    }

    /**
     * 设置总的记录数
     * @param totalRecords
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    /**
     * 获得每页多少条数
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页条数
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获得当前页数
     * @return
     */
    public int getPageNo() {
        return pageNo;
    }

    /**
     * 设置当前页数
     * @param pageNo
     */
    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }
	
}
