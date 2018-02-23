package cn.kfcfr.core.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 持久化影响记录
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
public class PersistenceAffectedRecord<T> implements Serializable {
    private static final long serialVersionUID = -851048649293207582L;
    private List<T> insertedRecords;
    private List<T> updatedRecords;
    private List<T> deletedRecords;
    private List<T> ignoreRecords;
    private List<T> errorRecords;

    public PersistenceAffectedRecord(List<T> insertedRecords, List<T> updatedRecords, List<T> deletedRecords, List<T> ignoreRecords, List<T> errorRecords) {
        if (insertedRecords == null || updatedRecords == null || deletedRecords == null || ignoreRecords == null || errorRecords == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        this.insertedRecords = insertedRecords;
        this.updatedRecords = updatedRecords;
        this.deletedRecords = deletedRecords;
        this.ignoreRecords = ignoreRecords;
        this.errorRecords = errorRecords;
    }

    public int getInsertedCount() {
        if (insertedRecords == null) return 0;
        return insertedRecords.size();
    }

    public int getUpdatedCount() {
        if (updatedRecords == null) return 0;
        return updatedRecords.size();
    }

    public int getDeletedCount() {
        if (deletedRecords == null) return 0;
        return deletedRecords.size();
    }

    public int getIgnoreCount() {
        if (ignoreRecords == null) return 0;
        return ignoreRecords.size();
    }

    public int getErrorCount() {
        if (errorRecords == null) return 0;
        return errorRecords.size();
    }

    public List<T> getInsertedRecords() {
        return insertedRecords;
    }

    public List<T> getUpdatedRecords() {
        return updatedRecords;
    }

    public List<T> getDeletedRecords() {
        return deletedRecords;
    }

    public List<T> getIgnoreRecords() {
        return ignoreRecords;
    }

    public List<T> getErrorRecords() {
        return errorRecords;
    }
}
