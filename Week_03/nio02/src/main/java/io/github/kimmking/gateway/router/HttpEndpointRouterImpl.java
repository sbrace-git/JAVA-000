package io.github.kimmking.gateway.router;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HttpEndpointRouterImpl implements HttpEndpointRouter {

    private List<String> endpoints;

    private RouterEnum routerEnum;

    private Map<Double, String> weightMap;

    private AtomicInteger currentIndex = new AtomicInteger(0);

    private int endpointsSize;

    public HttpEndpointRouterImpl(List<String> endpoints) {
        this(endpoints, RouterEnum.RANDOM);
    }

    public HttpEndpointRouterImpl(List<String> endpoints, RouterEnum routerEnum, int... weight) {
        this.endpoints = endpoints;
        this.routerEnum = routerEnum;
        endpointsSize = endpoints.size();
        if (RouterEnum.WEIGHT.equals(routerEnum)) {
            int length = Integer.min(weight.length, endpointsSize);
            int weightSum = Arrays.stream(weight).limit(length).sum();
            weightMap = new TreeMap<>();
            double baseWeight = 0.0d;
            for (int i = 0; i < length; i++) {
                int weightItem = weight[i];
                if (weightItem <= 0) {
                    continue;
                }
                weightMap.put(baseWeight += 100.0d * weightItem / weightSum, endpoints.get(i));
            }
        }
    }

    public String route() {
        if (RouterEnum.ROUND_RIBBON.equals(routerEnum)) {
            return roundRibbon();
        } else if (RouterEnum.WEIGHT.equals(routerEnum)) {
            return weight();
        }
        return random();
    }

    private String random() {
        return endpoints.get(new Random().nextInt(endpointsSize));
    }

    private String roundRibbon() {
        return endpoints.get((currentIndex.getAndIncrement()) % endpointsSize);
    }

    private String weight() {
        int random = new Random().nextInt(100);
        for (Map.Entry<Double, String> entry : weightMap.entrySet()) {
            if (entry.getKey().byteValue() > random) {
                return entry.getValue();
            }
        }
        return null;
    }
}
