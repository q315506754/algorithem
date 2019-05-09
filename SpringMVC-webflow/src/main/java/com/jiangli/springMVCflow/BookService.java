package com.jiangli.springMVCflow;

import com.jiangli.springMVCflow.model.ViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Jiangli
 * @date 2018/12/29 17:34
 */
@Component("bookingService")
public class BookService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String createBooking(String hotelId) {
        logger.debug("createBooking hotelId:{}",hotelId);
        return  UUID.randomUUID().toString();
    }

    public List<String> findHotels(ViewObject queryStr) {
        Integer pageIndex = queryStr.getPageIndex();
        if (pageIndex == null) {
            pageIndex = 0;
        }else {
            pageIndex+=1;
        }
        queryStr.setPageIndex(pageIndex);
        logger.debug("findHotels:{}",queryStr);
        return Arrays.asList("aaa",queryStr.getHotelName()+pageIndex,"bbbb");
    }

    public List<String> nextPage(String queryStr) {
        logger.debug("nextPage:{}",queryStr);
        return Arrays.asList("nextPage",queryStr,"nextPage");
    }
    public boolean record(ViewObject queryStr) {
        logger.debug("record:{}",queryStr);
        return true;
    }
    public List<String> previousPage(String queryStr) {
        logger.debug("previousPage:{}",queryStr);
        return Arrays.asList("previousPage",queryStr,"previousPage");
    }

}
