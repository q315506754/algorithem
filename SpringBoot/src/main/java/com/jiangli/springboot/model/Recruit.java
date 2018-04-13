
package com.jiangli.springboot.model;
import java.util.Date;


/**
 * 招生(班次)表 model
 */
public class Recruit {
    private Long recruitId;//主键
    private Long courseId;//
    private Date startTime;//招生开始时间 2018-4-1 00:00:00
    private Date endTime;//招生结束时间 2018-4-1 23:59:59
    private Long validTime;//学习有效期(天数) 从导入这个学生开始算
    private Integer isDeleted;//是否删除  0否 1是
    private Date createTime;//创建时间
    private Date updateTime;//修改时间
    private Long createPerson;//创建人
    private Long deletePerson;//修改人
    private Integer sort;//序号

    @Override
    public String toString() {
        return "Recruit{" +
                "recruitId=" + recruitId +
                ", courseId=" + courseId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", validTime=" + validTime +
                ", isDeleted=" + isDeleted +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createPerson=" + createPerson +
                ", deletePerson=" + deletePerson +
                ", sort=" + sort +
                '}';
    }

    public Integer getSort() {
        return sort;
    }

    public Recruit setSort(Integer sort) {
        this.sort = sort;
        return this;
    }

    public Long getRecruitId() {
        return recruitId;
    }

    public Recruit setRecruitId(Long recruitId) {
        this.recruitId = recruitId;
        return this;
    }

    public Long getCourseId() {
        return courseId;
    }

    public Recruit setCourseId(Long courseId) {
        this.courseId = courseId;
        return this;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Recruit setStartTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Recruit setEndTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public Long getValidTime() {
        return validTime;
    }

    public Recruit setValidTime(Long validTime) {
        this.validTime = validTime;
        return this;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public Recruit setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Recruit setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Recruit setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getCreatePerson() {
        return createPerson;
    }

    public Recruit setCreatePerson(Long createPerson) {
        this.createPerson = createPerson;
        return this;
    }

    public Long getDeletePerson() {
        return deletePerson;
    }

    public Recruit setDeletePerson(Long deletePerson) {
        this.deletePerson = deletePerson;
        return this;
    }
}
