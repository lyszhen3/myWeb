## 类图

```mermaid
classDiagram
    class CharDisplay {
        +display(String type):void
    }
    note "没有开闭原则" for CharDisplay
    class PieChart {
        +display():void
    }
    class BarChart {
        +display():void
    }
    CharDisplay ..> PieChart
    CharDisplay ..> BarChart
    class CharDisplay2 {
        -chart:AbstractChart
        +setChart(AbstractChart abstractChart):void
        +dispay():void
    }
    note "char.display()" for CharDisplay2
    abstract class AbstractChart {
        +abstract display():void
    }
    class PieChart2 {
        +display():void
    }
    class BarChart2 {
        +display():void
    }
    PieChart2 --|> AbstractChart
    BarChart2 --|> AbstractChart
    CharDisplay2 ..> AbstractChart
```
