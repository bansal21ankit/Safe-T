package com.example.safet.saviour;

public final class SaviourDetail {
    private String mSaviourName;
    private String mSaviourPhone;

    public SaviourDetail(String iSaviourName, String iSaviourPhone) {
        mSaviourName = iSaviourName;
        mSaviourPhone = iSaviourPhone;
    }

    public String getSaviourName() {
        return mSaviourName;
    }

    public String getSaviourPhone() {
        return mSaviourPhone;
    }

    public void setSaviourName(String saviourName) {
        this.mSaviourName = saviourName;
    }

    public void setSaviourPhone(String saviourPhone) {
        this.mSaviourPhone = saviourPhone;
    }

    public static class Builder {
        private String saviourName;
        private String saviourPhone;

        public Builder setSaviourName(String saviourName) {
            this.saviourName = saviourName;
            return this;
        }

        public Builder setSaviourPhone(String saviourPhone) {
            this.saviourPhone = saviourPhone;
            return this;
        }

        public SaviourDetail create() {
            return new SaviourDetail(saviourName, saviourPhone);
        }
    }
}