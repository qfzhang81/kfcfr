package cn.kfcfr.persistence.mybatis.mapper;

import org.apache.commons.lang3.StringUtils;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 实体类属性与数据库字段名转换
 * Created by zhangqf on 2017/6/9.
 */
@SuppressWarnings(value = {"unchecked", "WeakerAccess"})
class TkEntityColumnMapConvert {
    private static final Map<Class<?>, Map<String, EntityColumn>> entityColumnMap = new HashMap<>();

    /***
     * 获取实体类属性对应的数据库字段名
     * @param entityClass 实体类
     * @param propertyName 属性名
     * @return 数据库字段名
     */
    public String getEntityColumnName(Class<?> entityClass, String propertyName) {
        EntityColumn entityColumn = getEntityColumn(entityClass,propertyName);
        if (entityColumn != null) {
            return entityColumn.getColumn();
        }
        return "";
    }

    /***
     * 获取实体类属性的对应关系
     * @param entityClass 实体类
     * @param propertyName 属性名
     * @return 对应关系
     */
    public EntityColumn getEntityColumn(Class<?> entityClass, String propertyName) {
        if (entityClass == null || StringUtils.isEmpty(propertyName)) return null;
        Map<String, EntityColumn> entityColumns = entityColumnMap.get(entityClass);
        if (entityColumns == null) {
            //没有被读取过，读取至内存
            entityColumns = initEntityColumnMap(entityClass);
            synchronized (MapperHelper.class) {
                if (!entityColumnMap.containsKey(entityClass)) {
                    entityColumnMap.put(entityClass, entityColumns);
                }
            }
        }
        //读取指定的字段名
        return entityColumns.get(propertyName.toLowerCase());
    }

    /***
     * 获取实体类的所有属性对应的数据库字段名，得到映射关系
     * @param entityClass 实体类
     * @return 映射关系对应
     */
    private Map<String, EntityColumn> initEntityColumnMap(Class<?> entityClass) {
        Map<String, EntityColumn> entityColumns = new HashMap<>();
        Set<EntityColumn> columns = EntityHelper.getColumns(entityClass);
        if (columns != null && columns.size() > 0) {
            for (EntityColumn column : columns) {
                entityColumns.put(column.getProperty().toLowerCase(), column);
            }
        }
        return entityColumns;
    }
}
