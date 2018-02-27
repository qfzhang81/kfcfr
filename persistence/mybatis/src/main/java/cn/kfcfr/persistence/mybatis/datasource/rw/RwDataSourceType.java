package cn.kfcfr.persistence.mybatis.datasource.rw;

@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public enum RwDataSourceType {
    reader(false, "reader"),
    writer(true, "writer");

    private boolean writable;
    private String key;

    RwDataSourceType(boolean writable, String key) {
        this.writable = writable;
        this.key = key;
    }

    public boolean isWritable() {
        return writable;
    }

    public String getType() {
        return key;
    }

//    public void setType(String type) {
//        this.type = type;
//    }

}
