package com.jiangli.sort.test;

import com.jiangli.common.core.Sorter;
import com.jiangli.sort.BubbleSort;
import com.jiangli.sort.InsertionSort;
import com.jiangli.sort.SelectionSort;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.Repeat;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class InsertionSortTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(InsertionSortTest.class);
    private ApplicationContext applicationContext;
    @Autowired
    private InsertionSort insertionSort;

    @Autowired
    private BubbleSort bubbleSort;

    @Autowired
    private SelectionSort selectionSort;

    private static final int REPEAT_TIMES = 1;
    private static final int FUNC_TIMES = 100;
    private static final List<ParamAndExpect> cases = new LinkedList<ParamAndExpect>();

    static {
        cases.add(createPAEObject(new Integer[]{5, 2, 1, 3, 4}, new Integer[]{1, 2, 3, 4, 5}));
        cases.add(createPAEObject(new Integer[]{4, 3, 1, 2, 5}, new Integer[]{1, 2, 3, 4, 5}));
    }

    private static ParamAndExpect createPAEObject(Integer[] params, Integer[] expectedArrays1) {
        return new ParamAndExpect(params, expectedArrays1);
    }

    static class ParamAndExpect {
        Integer[] testParams;
        Integer[] expectedArrays;

        public ParamAndExpect(Integer[] testParams, Integer[] expectedArrays) {
            this.testParams = testParams;
            this.expectedArrays = expectedArrays;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Before
    public void before() {
        for (ParamAndExpect aCase : cases) {
            logger.debug(Arrays.toString(aCase.testParams)+"=>"+Arrays.toString(aCase.expectedArrays));
        }
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_insertionSort() {
        expect(insertionSort);
    }

    private void expect(Sorter<Integer> sorter) {
        System.currentTimeMillis();
        for (ParamAndExpect aCase : cases) {
            Integer[] output = sorter.sort(aCase.testParams);
            Assert.assertArrayEquals(Arrays.toString(output) + "与预期不服:" + Arrays.toString(aCase.testParams) + "=>" + Arrays.toString(aCase.expectedArrays), aCase.expectedArrays, output);
        }
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_bubbleSort() {
        expect(bubbleSort);
    }

    @Test
    @Repeat(REPEAT_TIMES)
    public void test_selectionSort() {
        expect(selectionSort);
    }

}
