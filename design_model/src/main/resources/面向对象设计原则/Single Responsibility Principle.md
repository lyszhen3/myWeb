## 类图

```mermaid
classDiagram
    class CustomerDataChart {
        +getConnection()
        +findCustomers()
        +createChart()
        +displayChart()
    }
    note "一个类负责多职责" for CustomerDataChart
    class DButil {
        +getConnection
    }
    class CustomerDao {
        -util:DButil
        +findCustomers():List
    }
    class CustomerDataChart2 {
        -dao:CustomerDao
        +createChart()
        +displayChart()
    }
    CustomerDataChart2 --> CustomerDao
    CustomerDao --> DButil
```
