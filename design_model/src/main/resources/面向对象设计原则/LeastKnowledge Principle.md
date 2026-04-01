## 类图

```mermaid
classDiagram
    class Button {
    }
    note "未使用迪米特法则" for Button
    Button --> TextBox
    Button --> ComboBox
    Button --> List
    Button --> Label
    List -- ComboBox
    List -- TextBox
    ComboBox -- TextBox
    class Mediator {
    }
    Mediator --> Button2
    Mediator --> TextBox2
    Mediator --> List2
    Mediator --> Label2
    Mediator --> ComboBox2
```
