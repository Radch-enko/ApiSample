package com.api.sample.network.models;

import java.util.Map;

public class CountriesResponse {
    private Map<String, String> list;

    public Map<String, String> getList() {
        return list;
    }

    public void setList(Map<String, String> list) {
        this.list = list;
    }
}
