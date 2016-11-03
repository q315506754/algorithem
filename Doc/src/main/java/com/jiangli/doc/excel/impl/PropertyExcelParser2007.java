package com.jiangli.doc.excel.impl;


import com.jiangli.doc.excel.anno.FromExcel;
import com.jiangli.doc.excel.enums.ExcelValType;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/5 0005 14:49
 */
public class PropertyExcelParser2007 extends ExcelToModelParser2007<PropertyExcelParser2007.PropModel> {
    public PropertyExcelParser2007() {
        super("筛选后酒店", PropModel.class);
    }

    public static class PropModel {
        @FromExcel(columnName = "城市", valType = ExcelValType.STRING)
        protected String city;
        @FromExcel(columnName = "酒店", valType = ExcelValType.STRING)
        protected String name;
        @FromExcel(columnName = "星级", valType = ExcelValType.STRING)
        protected String star;
        @FromExcel(columnName = "地址", valType = ExcelValType.STRING)
        protected String address;

        @Override
        public String toString() {
            return "PropModel{" +
                    "city='" + city + '\'' +
                    ", name='" + name + '\'' +
                    ", star='" + star + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
