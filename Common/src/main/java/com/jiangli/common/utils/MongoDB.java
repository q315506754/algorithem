package com.jiangli.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.mongodb.core.MongoOperations;

public class MongoDB implements ApplicationContextAware {
    static ApplicationContext a;
    @Autowired
    @Qualifier("mongoTemplate")
    MongoOperations mongoOperation;

    public static MongoDB getInstance() throws BeansException {
        return (MongoDB) a.getBean("mongoDB");
    }

    /**
     * 获取老数据库(Didatour) MongoOperations对象
     *
     * @return MongoOperations
     */
    public static MongoOperations getMongoDB() {
        return getInstance().getMongoOperation();
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        a = arg0;
    }

    public MongoOperations getMongoOperation() {
        return mongoOperation;
    }

    public void setMongoOperation(MongoOperations mongoOperation) {
        this.mongoOperation = mongoOperation;
    }

}
