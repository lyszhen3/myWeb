## 类图

```mermaid
classDiagram
    class EmailSender {
        +send(CommonCustomer customer):void
        +send(VIPCustomer customer):void
    }
    note "未使用里氏替换" for EmailSender
    class CommonCustomer {
        -name:String
        -email:String
    }
    class VIPCustomer {
        -name:String
        -email:String
    }
    EmailSender ..> CommonCustomer
    EmailSender ..> VIPCustomer
    class EmailSender2 {
        +send(AbstractCustomer customer):void
    }
    abstract class AbstractCustomer {
        #name:String
        #emial:String
    }
    CommonCustomer2 --|> AbstractCustomer
    VIPCustomer2 --|> AbstractCustomer
    EmailSender2 ..> AbstractCustomer
```
