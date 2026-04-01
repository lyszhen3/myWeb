## 类图

```mermaid
classDiagram
    class TXTDataConvertor {
        +readFile():void
    }
    class ExcelDataConvertor {
        +readFile():void
    }
    class CustomerDao {
        +addCustomers():void
    }
    note "没有使用依赖倒转" for CustomerDao
    CustomerDao ..> TXTDataConvertor
    CustomerDao ..> ExcelDataConvertor
    abstract class AbstractDataConvertor {
        +abstract readFile():void
    }
    class TXTDataConvertor2 {
        +readFile():void
    }
    class ExcelDataConvertor2 {
        +readFile():void
    }
    class CustomerDao2 {
        +addCustomers():void
    }
    note "<classNmae>TXTDataConvertor</className>" for CustomerDao2
    TXTDataConvertor2 --|> AbstractDataConvertor
    ExcelDataConvertor2 --|> AbstractDataConvertor
    CustomerDao2 ..> AbstractDataConvertor
```
