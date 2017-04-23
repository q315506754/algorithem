package com.jiangli.practice.eleme.dao;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

public class BaseRepositoryImpl<T, ID extends Serializable>
  extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

  private final EntityManager entityManager;
  private final JpaEntityInformation jpaEntityInformation;

  public BaseRepositoryImpl(JpaEntityInformation entityInformation,
                            EntityManager entityManager) {
    super(entityInformation, entityManager);
      try {
          logger.debug(BeanUtils.describe(entityInformation).toString());
      } catch (Exception e) {
          e.printStackTrace();
      }
    // Keep the EntityManager around to used from the newly introduced methods.
    this.entityManager = entityManager;
    this.jpaEntityInformation = entityInformation;
  }


    @Override
    public List<T> listAll() {
        System.out.println("listAll...");
        Class javaType = this.jpaEntityInformation.getJavaType();
        logger.debug("listAll:"+javaType);

        Table annotation = (Table)javaType.getDeclaredAnnotation(Table.class);

//        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
//        CriteriaQuery<Object> query = criteriaBuilder.createQuery();
//        TypedQuery<Object> rs = this.entityManager.createQuery(query);
//        List<Object> resultList = rs.getResultList();

        Query nativeQuery = this.entityManager.createNativeQuery("select * from "+annotation.name(),javaType);
        List resultList = nativeQuery.getResultList();
//        for (Object o : resultList) {
//            System.out.println(o);
//        }
//        System.out.println(resultList);
//        entityManager.find()
        return resultList;
    }
}