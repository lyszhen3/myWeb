## 类图

```mermaid
classDiagram
    class DBUtil {
        +getConnection():Connection
    }
    class CustomerDAO {
        +addCustomer():void
    }
    note "super.getConnection();" for CustomerDAO
    CustomerDAO --|> DBUtil
    class DBUtil2 {
        +getConnection():Connection
    }
    class OracleDBUtil {
        +getConnection():Connection
    }
    OracleDBUtil --|> DBUtil2
    class CustomerDAO2 {
        -util:DBUtil2
        +addCustomer():void
    }
    note "util.getConnection();" for CustomerDAO2
    CustomerDAO2 ..> DBUtil2
```
