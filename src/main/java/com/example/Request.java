package com.example;

public class Request {
    private String url;
    private String path;
    private String queryStr;

    public Request(String url) {
        this.url = url;
        String[] urlBits = url.split("\\?", 2);
        this.path = urlBits[0];

        if (urlBits.length == 2) {
            this.queryStr = urlBits[1];
        }
    }

    public int getIntParam(String paramName, int defaultValue) {
        if (queryStr == null) {
            return defaultValue;
        }

        String[] urlBits = queryStr.split("&");


        for (String urlBit : urlBits) {
            String[] paramNameAndValue = urlBit.split("=", 2);
            String paramName_ = paramNameAndValue[0];
            String paramValue = paramNameAndValue[1];

            if (paramName.equals(paramName_)) {
                return Integer.parseInt(paramValue);
            }
        }
        return defaultValue;
    }

    public String getPath() {
        return path;
    }

    public String getUrl() {
        return url;
    }

    public String getQueryStr() {
        return queryStr;
    }
}
