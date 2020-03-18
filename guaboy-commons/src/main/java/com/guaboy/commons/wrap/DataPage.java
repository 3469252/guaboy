package com.guaboy.commons.wrap;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * 数据分页结构
 *
 * @author zhousir
 * @since 1.0.0-RELEASE
 * 
 */
public class DataPage<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 数据集 */
	private List<T> list;
	
	/** 总记录数 */
	private long totalRecordCount;
	
	/** 当前页码 */
	private int pageNo;
	
	/** 每页大小 */
	private int pageSize;

	
	/**
	 * 默认构造函数
	 */
	public DataPage() {
		this.list = new ArrayList<T>();
		this.totalRecordCount = 0L;
		this.pageNo = 1;
		this.pageSize = 1;
	}

	/**
	 * 有参构造函数
	 * 
	 * @param list
	 * @param totalRecord
	 * @param pageNo
	 * @param pageSize
	 */
	public DataPage(List<T> list, long totalRecordCount, int pageNo, int pageSize) {
		this.list = list;
		this.totalRecordCount = totalRecordCount;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
	public long getTotalRecordCount() {
		return totalRecordCount;
	}

	public void setTotalRecordCount(long totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
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

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	//额外提供的快捷方法
	/**
	 * 计算并获取总页数
	 */
	public int getPageCount() {
		return (int) Math.ceil(this.totalRecordCount * 1.000 / this.pageSize);
	}

	
	/**
	 * 当前页是否有下一页
	 */
	public boolean hasNextPage() {
		if (this.pageNo < getPageCount()) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 当前页是否有上一页
	 */
	public boolean hasPrevPage() {
		if (this.pageNo > 1) {
			return true;
		} else {
			return false;
		}
	}

	
	/**
	 * 取得List的第N页的subList
	 * 
	 * @param list 要分页的list
	 * @param pageNo
	 * @param pageSize
	 * 
	 * @return List
	 */
	public static <T> List<T> subList(List<T> list, int pageNo, int pageSize) {
		pageSize = (pageSize <= 0 ? 10 : pageSize);
		pageNo = (pageNo <= 0 ? 1 : pageNo);
		int begin = (pageSize * (pageNo - 1) > list.size() ? list.size() : pageSize * (pageNo - 1));
		int end = (pageSize * pageNo > list.size() ? list.size() : pageSize * pageNo);
		return new ArrayList<T>(list.subList(begin, end));
	}

	
	/**
	 * 取得List的第N页的DataPage对象
	 * 
	 * @param list 要分页的list
	 * @param pageNo
	 * @param pageSize
	 * 
	 * @return DataPage
	 */
	public static <T> DataPage<T> subListByPage(List<T> list, int pageNo, int pageSize) {
		List<T> ls = subList(list, pageNo, pageSize);
		return new DataPage<T>(ls, list.size(), pageNo, pageSize);
	}

	
	/** 重写过的toString */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		sb.append("list=[");
		int size = list.size();
		for (int i = 0; i < size; ++i) {
			sb.append(list.get(i).toString());
			if (i < size-1) {
				sb.append(",");
			}
		}
		sb.append("]");
		sb.append(", totalRecordCount=").append(totalRecordCount);
		sb.append(", pageNo=").append(pageNo);
		sb.append(", pageSize=").append(pageSize);
		sb.append(", pageCount=").append(this.getPageCount());
		sb.append(", hasNextPage=").append(this.hasNextPage());
		sb.append(", hasPrevPage=").append(this.hasPrevPage());
		sb.append("} ");
		return sb.toString();
	}

}