package com.dveamer.simplifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PathFixture {

    private static String HUG_PATH_TEMPLATE = "hugs%d/hijklmn/op/qrstuv%d/wxy/zabcd/efg%d/{param1}/op/qrstuv/wxy/zabcd/efg/hijklmn/op/qrstuv/wxy/z/{param2}";


    public static List<String> wholeListeningPath() {
        List<String> list = expectedInOutList().stream().map(e->e.getOutput()).distinct().collect(Collectors.toList());
        list.addAll(additionalListeningPaths());
        list.addAll(additionalHugPaths(20000));
        return list;
    }

    public static Collection<ExpectedInOut> expectedInOutList() {
        List<ExpectedInOut> list = new ArrayList<>();
        list.add(new ExpectedInOut("/index.html","index.html"));
        list.add(new ExpectedInOut("/products/index.html","products/index.html"));
        list.add(new ExpectedInOut("/products/1","products/{productId}"));
        list.add(new ExpectedInOut("/products/2","products/{productId}"));
        list.add(new ExpectedInOut("/products/3","products/3"));
        list.add(new ExpectedInOut("/products/PROD_001","products/{productId}"));
        list.add(new ExpectedInOut("/products/1/promotions","products/{productId}/promotions"));
        list.add(new ExpectedInOut("/products/2/promotions","products/{productId}/promotions"));
        list.add(new ExpectedInOut("/products/PROD_001/promotions","products/{productId}/promotions"));
        list.add(new ExpectedInOut("/products/1/promotions/2","products/{productId}/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/2/promotions/1","products/{productId}/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/3/promotions/1","products/3/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/PROD_001/promotions/PROM_010","products/{productId}/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/PROD_001/promotions/products","products/{productId}/promotions/{promotionId}"));

        list.add(new ExpectedInOut("/products/1/promotions/2/","products/{productId}/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/PROD_001/promotions/products/","products/{productId}/promotions/{promotionId}"));
        list.add(new ExpectedInOut("/products/index1.html","products/{}.html"));
        list.add(new ExpectedInOut("/index1.html","{}.html"));
        list.add(new ExpectedInOut("/products.promotion.html","{}.html"));

        return list;
    }

    private static List<String> additionalListeningPaths() {
        List<String> list = new ArrayList<>();
        list.add("products/{productId}/promotions/{promotionId}.html");
        list.add("products/{productId}/promotions/{promotionId}/");
        list.add("products/{productId}/promotions/{promotionId}/?abcd=eft");
        list.add("products/{productId}/promotions/{promotionId}?abcd=eft");
        list.add("/products/{productId}/promotions/{promotionId}?abcd=eft");
        list.add("/products/{productId}/promotions/{promotionId}/?abcd=eft");
        list.add("/products/{productId}/promotions/{promotionId}");
        list.add("/products/{productId}/promotions/{promotionId}/");
        return list;
    }


    private static List<String> additionalHugPaths(long size) {
        List<String> list = new ArrayList<>();

        for(int i=0; i<size; i++) {
            list.add(createHugePath(i));
        }
        return list;
    }

    private static String createHugePath(long index) {
        return String.format(HUG_PATH_TEMPLATE, index, index, index);
    }

}
