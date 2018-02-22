package cn.kfcfr.persistence.mybatis.datasource;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public enum DataSourceType {
    reader("reader"),
    writer("writer");

    private String type;

    DataSourceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
