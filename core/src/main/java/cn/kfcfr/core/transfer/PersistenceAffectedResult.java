package cn.kfcfr.core.transfer;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 持久化影响行数
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess", "unused"})
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class PersistenceAffectedResult<T> implements Serializable {
    private static final long serialVersionUID = -851048649293207582L;
    private Integer insertedCount;
    private Integer updatedCount;
    private Integer deletedCount;
    private Integer ignoreCount;
    private Integer errorCount;
    private List<T> insertedRecords;
    private List<T> updatedRecords;
    private List<T> deletedRecords;
    private List<T> ignoreRecords;
    private List<T> errorRecords;

    public PersistenceAffectedResult(int insertedCount, int updatedCount, int deletedCount, int ignoreCount, int errorCount) {
        this.insertedCount = insertedCount;
        this.updatedCount = updatedCount;
        this.deletedCount = deletedCount;
        this.ignoreCount = ignoreCount;
        this.errorCount = errorCount;
        this.insertedRecords = null;
        this.updatedRecords = null;
        this.deletedRecords = null;
        this.ignoreRecords = null;
        this.errorRecords = null;
    }

    public PersistenceAffectedResult(List<T> insertedRecords, List<T> updatedRecords, List<T> deletedRecords, List<T> ignoreRecords, List<T> errorRecords) {
        if (insertedRecords == null || updatedRecords == null || deletedRecords == null || ignoreRecords == null || errorRecords == null) {
            throw new IllegalArgumentException("Parameters cannot be null.");
        }
        this.insertedCount = null;
        this.updatedCount = null;
        this.deletedCount = null;
        this.ignoreCount = null;
        this.errorCount = null;
        this.insertedRecords = insertedRecords;
        this.updatedRecords = updatedRecords;
        this.deletedRecords = deletedRecords;
        this.ignoreRecords = ignoreRecords;
        this.errorRecords = errorRecords;
    }

    public int getInsertedCount() {
        if (insertedCount != null) return insertedCount;
        return insertedRecords.size();
    }

    public int getUpdatedCount() {
        if (updatedCount != null) return updatedCount;
        return updatedRecords.size();
    }

    public int getDeletedCount() {
        if (deletedCount != null) return deletedCount;
        return deletedRecords.size();
    }

    public int getIgnoreCount() {
        if (ignoreCount != null) return ignoreCount;
        return ignoreRecords.size();
    }

    public int getErrorCount() {
        if (errorCount != null) return errorCount;
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
