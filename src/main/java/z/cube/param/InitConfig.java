package z.cube.param;

import javax.sql.DataSource;

public class InitConfig {
    private String     propertiesFileNames;
    private String     tableName;
    private String     keyColumn;
    private String     valueColumn;
    private DataSource dataSource;

    public InitConfig() {
    }

    public InitConfig(String propertiesFileNames, String tableName, String keyColumn, String valueColumn,
                      DataSource dataSource) {
        this.propertiesFileNames = propertiesFileNames;
        this.tableName = tableName;
        this.keyColumn = keyColumn;
        this.valueColumn = valueColumn;
        this.dataSource = dataSource;
    }

    public String getPropertiesFileNames() {
        return propertiesFileNames;
    }

    public void setPropertiesFileNames(String propertiesFileNames) {
        this.propertiesFileNames = propertiesFileNames;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getKeyColumn() {
        return keyColumn;
    }

    public void setKeyColumn(String keyColumn) {
        this.keyColumn = keyColumn;
    }

    public String getValueColumn() {
        return valueColumn;
    }

    public void setValueColumn(String valueColumn) {
        this.valueColumn = valueColumn;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
