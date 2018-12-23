package com.jiangli.db.model;

import java.io.Serializable;

/**
 * 分页类，只需要传入：分页索引(index，非负，从0开始计)、总长度(rows，非负)、分页长度(limit，正整数)三个属性即可，程序会自动计算分页其它属性值
 * @author zlikun
 */
public class PageBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private int index ;				// 分页索引，从0开始
	private int limit ;				// 每页显示记录数
	private int rows ;				// 总记录数
	
	public PageBean() {

	}
	
	/**
	 * 使用分页长度初始化分页类，分页索引默认为：0
	 * @param limit
	 */
	public PageBean(int limit) {
		this(limit ,0) ;
	}
	
	/**
	 * 使用分页长度和分页索引初始化分页类
	 * @param limit
	 * @param index
	 */
	public PageBean(int limit ,int index) {
		this(limit ,0 ,index) ;
	}

	/**
	 * 使用分页长度、记录总数、分页索引初始化分页类
	 * @param limit
	 * @param rows
	 * @param index
	 */
	public PageBean(int limit ,int rows ,int index) {
		this.setLimit(limit) ;
		this.setRows(rows) ;
		this.setIndex(index) ;
	}

	/**
	 * 获取分页索引，从0开始计
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置分页索引，0表示第一页，不允许设置为负数(负数会被置0)
	 * @param index
	 * @return
	 */
	public PageBean setIndex(int index) {
		if(index < 0) index = 0 ;
		this.index = index;
		return this ;
	}

	/**
	 * 获取分页长度
	 * @return
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * 设置分页长度，只允许设置为正整数
	 * @param limit
	 * @return
	 */
	public PageBean setLimit(int limit) {
		if(limit <= 0) throw new IllegalArgumentException("分页长度不允许设置为负数和0!") ;
		this.limit = limit;
		return this ;
	}

	/**
	 * 获取分页记录总数
	 * @return
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * 设置分页记录总数，不允许为负数
	 * @param rows
	 * @return
	 */
	public PageBean setRows(int rows) {
		if(rows < 0) throw new IllegalArgumentException("分页总记录数不允许设置为负数!") ;
		this.rows = rows;
		return this ;
	}
	
	/**
	 * 获取分页起始索引位置，根据index/limit计算得出，
	 * <b style="color:#F00;">正常情况下，分页索引不能大于等于页数，但此处不作控制，由应用端自行控制</b>
	 * @return
	 */
	public int getStart() {
		return this.getIndex() * this.getLimit() ;
	}
	
	/**
	 * 获取分页结束索引位置，<b style="color:#F00;">结束位置索引不应被包含在查询列表中，否则列表长度多一</b>
	 * @return
	 */
	public int getEnd() {
		if(this.getStart() + this.getLimit() > this.getRows()) {
			return this.getRows() ;
		}
		return this.getStart() + this.getLimit() ;
	}

	/**
	 * 获取分页总页数，根据rows/limit计算得出
	 * @return
	 */
	public int getPages() {
		if(this.getRows() == 0) return 0 ;
		return this.getRows() % this.getLimit() == 0 
				? this.getRows() / this.getLimit()
				: this.getRows() / this.getLimit() + 1 ;
	}
	
	/**
	 * 获取当前实际记录数(通常为分页长度，不足一页时则返回实际记录数)
	 * @return
	 */
	public int getCurrNumber() {
		if(this.getEnd() >= this.getRows()) {
			return this.getRows() - this.getStart() ;
		}
		return this.getLimit() ;
	}

}
