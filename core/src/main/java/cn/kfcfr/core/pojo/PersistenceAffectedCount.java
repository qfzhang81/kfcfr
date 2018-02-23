package cn.kfcfr.core.pojo;

import java.io.Serializable;

/**
 * 持久化影响行数
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class PersistenceAffectedCount implements Serializable {
    private static final long serialVersionUID = -851048649293207582L;
    private Integer insertedCount;
    private Integer updatedCount;
    private Integer deletedCount;
    private Integer ignoreCount;
    private Integer errorCount;

    public PersistenceAffectedCount(int insertedCount, int updatedCount, int deletedCount, int ignoreCount, int errorCount) {
        this.insertedCount = insertedCount;
        this.updatedCount = updatedCount;
        this.deletedCount = deletedCount;
        this.ignoreCount = ignoreCount;
        this.errorCount = errorCount;
    }

    public Integer getInsertedCount() {
        return insertedCount;
    }

    public Integer getUpdatedCount() {
        return updatedCount;
    }

    public Integer getDeletedCount() {
        return deletedCount;
    }

    public Integer getIgnoreCount() {
        return ignoreCount;
    }

    public Integer getErrorCount() {
        return errorCount;
    }
}
