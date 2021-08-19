package com.finda.server.mydata.common.code;

public enum Industry {
    BANK("/bank"),
    CARD("/card"),
    INVESTMENT("/invest"),
    INSURANCE("/insu"),
    ELECTRONIC_FINANCE("/efin"),
    CAPITAL("/capital"),
    GUARANTY_INSURANCE("/ginsu"),
    TELECOM("/telecom");

    private String path;

    Industry(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
