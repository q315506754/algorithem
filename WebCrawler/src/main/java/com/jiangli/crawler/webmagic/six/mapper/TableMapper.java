package com.jiangli.crawler.webmagic.six.mapper;

import com.jiangli.crawler.webmagic.six.model.TableModel;

public interface TableMapper {
     void insert(TableModel user);

     String selectByTitle(String title);
}