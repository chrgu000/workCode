package com.whty.dmp.core.mybatis;

/**
 *  分页类
 * @author cjp : 2016年6月23日
 */
public class Page {

	private int pageNo = 1;//当前页码
	private int pageSize = 10;//页面大小，设置为“-1”表示不进行分页（分页无效）

	private int count;// 总记录数，设置为“-1”表示不查询总数
	private int totalPage = 0;//总页数
	
	private int first;//首页索引--赋值由构造函数
	private int last;//最后一页索引--赋值由构造函数
	private int prev;//上一页索引
	private int next;//下一页索引
	
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页
	
	/**
	 * 默认构造方法--不分页查询
	 */
	public Page(){
		this.pageSize = -1;
	}
	
	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 */
	public Page(int pageNo, int pageSize) {
		this(pageNo, pageSize, 0);
	}
	
	/**
	 * 构造方法
	 * @param pageNo 当前页码
	 * @param pageSize 分页大小
	 * @param count 数据条数
	 */
	public Page(int pageNo, int pageSize, int count) {
		this.setCount(count);
		this.setPageNo(pageNo);
		this.pageSize = pageSize;
	}
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 设置每页显示大小，<=0 时默认10条，>500时默认500条
	 * @date 2016年6月24日
	 */
	public void setPageSize(int pageSize) {
		if(pageSize <= 0){
			this.pageSize = 10;
		}else if(pageSize > 500 ){
			this.pageSize = 500;
		}else{
			this.pageSize = pageSize ;
		}
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public int getFirst() {
		return first;
	}

	public int getLast() {
		return last;
	}

	public int getPrev() {
		return prev;
	}

	public void setPrev(int prev) {
		this.prev = prev;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	/**
	 * 获取 Mybatis FirstResult
	 */
	public int getFirstResult(){
		int firstResult = (getPageNo() - 1) * getPageSize();
		return firstResult;
	}

	public int getTotalPage() {
		if (count == 0) {
			totalPage = 0;
		} else {
			if (count % pageSize == 0) {
				totalPage = (count / pageSize);
			} else {
				totalPage = (count / pageSize) + 1;
			}
		}
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}

