# Java + AI 面试题整理（2025-2026 高频方向）

> 说明：本整理基于 2025-2026 年大厂面试高频考点与真实面经，覆盖 Java 后端核心技术栈与 AI/大模型应用开发方向。建议按岗位目标选择性重点准备，原理 + 项目实战结合回答。

---

## 一、Java 后端面试题

### 1. Java 基础

1. `HashMap` 底层实现原理？JDK 1.7 与 1.8 的区别？

**参考答案：**

**核心原理**

`HashMap` 基于数组 + 链表 + 红黑树实现。底层是一个 `Node<K,V>[] table` 数组，通过 `(n - 1) & hash` 计算索引。发生哈希冲突时，同一桶中用链表存储；当链表长度超过 8 且数组长度超过 64 时，链表转为红黑树，查找复杂度从 \(O(n)\) 降到 \(O(\log n)\)。

**JDK 1.7 vs 1.8**

| 维度 | JDK 1.7 | JDK 1.8 |
|------|---------|---------|
| 数据结构 | 数组 + 链表 | 数组 + 链表 + 红黑树 |
| 插入方式 | 头插法 | 尾插法 |
| 扩容 | 先扩容后插入 | 先插入后扩容 |
| 并发安全 | 多线程下扩容可能形成循环链表，导致死循环 | 不会死循环，但仍可能丢数据 |
| hash 计算 | 4 次位运算 + 5 次异或 | 1 次位运算 + 1 次异或（高 16 位与低 16 位异或） |

**代码示例**

```java
// 简化版 put 流程
public V put(K key, V value) {
    return putVal(hash(key), key, value, false, true);
}

static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```

**面试要点**
- 重点讲清楚索引计算、冲突处理、链表转红黑树的阈值。
- JDK 1.7 并发扩容死循环是经典考点，要讲明白头插法 + 多线程同时扩容导致链表环。
- 时间复杂度：平均 \(O(1)\)，最坏 \(O(\log n)\)（红黑树）。

---

2. `HashMap` 的扩容机制？为什么扩容是 2 的幂次？

**参考答案：**

**扩容机制**

- 默认初始容量 16，负载因子 0.75，当元素个数超过 `capacity * loadFactor` 时触发扩容。
- 扩容为原来的 2 倍，创建新数组，将旧数组元素重新散列到新数组。
- JDK 1.8 中，由于容量是 2 的幂次，元素的新位置只可能在原索引或原索引 + 旧容量处，因此迁移时不需要重新计算 hash。

**为什么是 2 的幂次**

1. 索引计算高效：`hash % n` 可以替换为 `hash & (n - 1)`，位运算更快。
2. 分布均匀：保证低位全为 1，hash 值能充分参与索引计算。
3. 扩容迁移高效：只需要判断 hash 新增的高位是 0 还是 1。

**代码示例**

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap = oldCap << 1; // 容量翻倍
    // ...
}
```

**面试要点**
- 扩容是 `rehash` 过程，但不是重新计算完整 hash，而是用 `(hash & oldCap) == 0` 判断位置。
- 负载因子 0.75 是时间复杂度和空间复杂度的折中。

---

3. `ConcurrentHashMap` 1.7 分段锁与 1.8 CAS + synchronized 的区别？

**参考答案：**

**JDK 1.7：Segment 分段锁**

- 内部由多个 `Segment`（继承 ReentrantLock）组成，每个 Segment 管理一段桶数组。
- 默认 16 个 Segment，最多支持 16 线程并发写。
- 读取时不加锁，使用 volatile 保证可见性。

**JDK 1.8：CAS + synchronized**

- 取消 Segment，直接对桶数组的每个头节点加锁（细粒度锁）。
- 写操作：先 CAS 尝试插入，冲突时 synchronized 锁定头节点。
- 读操作：几乎无锁，依赖 volatile 和 Unsafe。
- 链表过长会转红黑树，进一步提高并发性能。

**对比总结**

| 维度 | 1.7 | 1.8 |
|------|-----|-----|
| 锁粒度 | Segment（段锁） | 头节点（桶锁） |
| 并发度 | 默认 16 | 理论上更高 |
| 数据结构 | 数组 + 链表 | 数组 + 链表 + 红黑树 |
| 锁类型 | ReentrantLock | synchronized + CAS |

**面试要点**
- 1.8 使用 synchronized 而不是 ReentrantLock，因为 synchronized 在 JDK 1.6 后优化了很多，且代码更简洁。
- `size()` 方法：1.7 需要多次统计 Segment，1.8 使用 CounterCell 数组和 baseCount 累加。

---

4. `ArrayList` 与 `LinkedList` 的区别？底层扩容机制？

**参考答案：**

**核心区别**

| 维度 | ArrayList | LinkedList |
|------|-----------|------------|
| 底层结构 | Object[] 数组 | 双向链表 |
| 随机访问 | \(O(1)\) | \(O(n)\) |
| 尾部插入 | 平均 \(O(1)\)，扩容时 \(O(n)\) | \(O(1)\) |
| 中间插入/删除 | \(O(n)\) | \(O(1)\)（找到位置后） |
| 内存占用 | 较少 | 较多（每个节点额外两个指针） |

**ArrayList 扩容机制**

- 默认初始容量 10。
- 扩容时：`newCapacity = oldCapacity + (oldCapacity >> 1)`，即 1.5 倍。
- 使用 `Arrays.copyOf` 复制元素。

**代码示例**

```java
// ArrayList 扩容核心逻辑
private void grow(int minCapacity) {
    int oldCapacity = elementData.length;
    int newCapacity = oldCapacity + (oldCapacity >> 1);
    elementData = Arrays.copyOf(elementData, newCapacity);
}
```

**面试要点**
- 频繁随机访问用 ArrayList，频繁插入删除用 LinkedList。
- 已知数据量时建议 `new ArrayList<>(size)`，避免多次扩容。

---

5. `String`、`StringBuilder`、`StringBuffer` 的区别？

**参考答案：**

**核心区别**

| 维度 | String | StringBuilder | StringBuffer |
|------|--------|---------------|--------------|
| 可变性 | 不可变（final char[]/byte[]） | 可变 | 可变 |
| 线程安全 | 安全（不可变） | 不安全 | 安全（synchronized） |
| 性能 | 低（每次修改创建新对象） | 高 | 较低（同步开销） |
| 使用场景 | 字符串常量、不可变场景 | 单线程字符串拼接 | 多线程字符串拼接 |

**String 不可变的好处**

1. 字符串常量池可以缓存，节省内存。
2. 适合作为 HashMap 的 key，hashCode 可缓存。
3. 线程安全，无需同步。
4. 防止字符串被篡改，增强安全性（如网络传输、文件路径）。

**代码示例**

```java
String s = "a" + "b"; // 编译期优化为 "ab"，但循环中拼接会创建大量对象

StringBuilder sb = new StringBuilder();
for (int i = 0; i < 100; i++) {
    sb.append(i);
}
String result = sb.toString();
```

**面试要点**
- 循环中避免用 `String +` 拼接，应使用 StringBuilder。
- JDK 9+ String 内部使用 byte[] + coder 编码标记，节省内存。

---

6. Java 中的 `==` 与 `equals()` 区别？`hashCode()` 与 `equals()` 的关系？

**参考答案：**

**`==` vs `equals()`**

- `==`：比较基本类型时值相等，比较引用类型时内存地址相等。
- `equals()`：默认与 `==` 相同，重写后通常比较对象内容（如 String、Integer）。

**`hashCode()` 与 `equals()` 的关系**

- 如果两个对象 `equals()` 为 true，则 `hashCode()` 必须相等。
- 如果 `hashCode()` 相等，`equals()` 不一定为 true（哈希冲突）。
- 重写 `equals()` 必须重写 `hashCode()`，否则在 HashMap/HashSet 中会出现逻辑错误。

**代码示例**

```java
public class Person {
    private String name;
    private int age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
```

**面试要点**
- `hashCode` 相等是 `equals` 相等的必要不充分条件。
- 在 HashMap 中，先比较 hashCode，再比较 equals。

---

7. 接口与抽象类的区别？Java 8 之后接口有哪些变化？

**参考答案：**

**接口 vs 抽象类**

| 维度 | 接口 | 抽象类 |
|------|------|--------|
| 继承/实现 | 多实现 | 单继承 |
| 方法 | JDK 8 前只能抽象方法 | 可以有抽象和具体方法 |
| 字段 | 默认 public static final | 任意 |
| 构造函数 | 没有 | 有 |
| 设计目的 | 定义行为规范（can-do） | 代码复用、模板（is-a） |

**Java 8 之后接口变化**

1. **默认方法（default）**：允许接口有方法实现，不破坏已有实现类。
2. **静态方法（static）**：属于接口本身，可直接调用。
3. **Java 9 私有方法（private）**：供默认方法和静态方法复用。

**代码示例**

```java
interface Animal {
    void eat();

    default void sleep() {
        System.out.println("sleeping");
    }

    static void info() {
        System.out.println("This is an animal");
    }
}
```

**面试要点**
- 默认方法解决了接口扩展时破坏实现类的问题。
- 一个类实现多个接口时，如果默认方法冲突，必须重写。

---

8. Java 泛型原理？类型擦除是什么？

**参考答案：**

**泛型原理**

泛型（Generics）在编译期进行类型检查，编译后会被擦除为原始类型（通常是 Object 或边界类型）。Java 的泛型是"伪泛型"，虚拟机运行时并不知道泛型参数的具体类型。

**类型擦除**

- 编译时：泛型参数参与类型检查。
- 编译后：泛型信息被擦除，替换为边界类型（无边界则为 Object）。
- 编译器会插入必要的强制类型转换代码。

**代码示例**

```java
// 编译前
List<String> list = new ArrayList<>();
list.add("hello");
String s = list.get(0);

// 编译后（反编译大致效果）
List list = new ArrayList();
list.add("hello");
String s = (String) list.get(0);
```

**面试要点**
- 泛型信息在运行时不可用，因此 `new T()`、`instanceof T` 不合法。
- 通配符：`? extends T`（上界，只读）、`? super T`（下界，只写）。
- PECS 原则：Producer extends，Consumer super。

---

9. Java 异常体系？Checked Exception 与 Unchecked Exception 区别？

**参考答案：**

**异常体系**

```
Throwable
├── Error（严重错误，不可恢复）
│   └── OutOfMemoryError, StackOverflowError
└── Exception（程序可处理）
    ├── Checked Exception（编译期检查）
    │   └── IOException, SQLException
    └── RuntimeException（运行时异常，Unchecked）
        └── NullPointerException, IllegalArgumentException
```

**Checked vs Unchecked**

| 维度 | Checked Exception | Unchecked Exception |
|------|-------------------|---------------------|
| 继承 | Exception | RuntimeException |
| 编译期 | 必须处理或声明 | 不强制 |
| 场景 | 外部不可控因素（IO、网络、数据库） | 编程错误 |
| 处理 | try-catch / throws | 通常避免发生 |

**代码示例**

```java
try {
    FileInputStream fis = new FileInputStream("file.txt");
} catch (FileNotFoundException e) { // Checked
    e.printStackTrace();
}

// Unchecked
String s = null;
int len = s.length(); // NullPointerException
```

**面试要点**
- 业务异常通常定义为 RuntimeException，避免污染方法签名。
- `finally` 块中不建议 return，会覆盖 try 中的返回值。

---

10. JDK 8/11/17/21 新特性了解哪些？

**参考答案：**

**JDK 8（最常用）**

- Lambda 表达式、Stream API、Optional、方法引用、默认方法、新的日期时间 API（java.time）。

**JDK 11**

- 局部变量类型推断 `var`、HTTP Client（标准库）、String 新增方法（`isBlank`、`lines`、`strip`）、ZGC 实验性引入。

**JDK 17（LTS）**

- 密封类（Sealed Classes）、模式匹配（instanceof Pattern Matching）、Records、Switch 表达式增强、移除 JavaFX 和 Applet。

**JDK 21（最新 LTS）**

- 虚拟线程（Virtual Threads，Project Loom）：轻量级线程，大幅提升并发能力。
- 序列集合（Sequenced Collections）。
- 分代 ZGC 成为默认垃圾回收器。
- Record Patterns、String Templates（预览）。

**代码示例**

```java
// 虚拟线程
Thread.startVirtualThread(() -> {
    System.out.println("Running in virtual thread: " + Thread.currentThread());
});

// Stream + Optional
List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5);
int sum = nums.stream()
              .filter(n -> n > 2)
              .mapToInt(Integer::intValue)
              .sum();
```

**面试要点**
- 如果公司用 JDK 17/21，重点准备 Records、Sealed Classes、Virtual Threads。
- 虚拟线程适合 IO 密集型场景，不适合 CPU 密集型任务。

---

### 11. 集合框架

11. `HashMap` 如何解决哈希冲突？链表长度超过 8 为什么转红黑树？

**参考答案：**

**解决哈希冲突的方式**

1. **链地址法**：冲突元素用链表连接（HashMap 采用）。
2. **开放寻址法**：线性探测、二次探测（ThreadLocalMap 采用）。
3. **再哈希法**：使用多个哈希函数。
4. **建立公共溢出区**。

HashMap 使用链地址法，同一桶中的元素以链表存储。

**为什么链表长度超过 8 转红黑树**

- 链表过长时查找退化为 \(O(n)\)，红黑树为 \(O(\log n)\)。
- 阈值选 8 是因为泊松分布：在默认负载因子 0.75 下，单个桶长度达到 8 的概率已经非常低（约 0.00000606），避免频繁在链表和红黑树之间转换。
- 红黑树节点大小约为链表节点的 2 倍，只有在冲突严重时才值得转换。

**代码示例**

```java
// HashMap 中链表转树的阈值
static final int TREEIFY_THRESHOLD = 8;
static final int UNTREEIFY_THRESHOLD = 6; // 树转链表的阈值
static final int MIN_TREEIFY_CAPACITY = 64; // 数组长度小于 64 时优先扩容
```

**面试要点**
- 转树需要同时满足：链表长度 ≥ 8 且数组长度 ≥ 64。
- 阈值 8 和 6 不对称，防止频繁在两种结构间切换。

---

12. `TreeMap` 与 `LinkedHashMap` 的应用场景？

**参考答案：**

**TreeMap**

- 基于红黑树实现，key 必须可比较（实现 Comparable 或传入 Comparator）。
- 元素按 key 排序，支持范围查询（`subMap`、`headMap`、`tailMap`）。
- 时间复杂度：查找、插入、删除均为 \(O(\log n)\)。

**LinkedHashMap**

- 继承 HashMap，维护双向链表记录插入顺序或访问顺序。
- 可配置 `accessOrder = true` 实现 LRU。
- 查找时间复杂度 \(O(1)\)。

**应用场景**

| 场景 | 推荐 |
|------|------|
| 需要排序 / 范围查询 | TreeMap |
| 需要保持插入顺序 | LinkedHashMap |
| 实现 LRU 缓存 | LinkedHashMap（accessOrder=true） |

**代码示例**

```java
// TreeMap 排序
TreeMap<Integer, String> treeMap = new TreeMap<>();
treeMap.put(3, "c");
treeMap.put(1, "a");
treeMap.put(2, "b");
System.out.println(treeMap.keySet()); // [1, 2, 3]

// LinkedHashMap 实现 LRU
LinkedHashMap<Integer, String> lru = new LinkedHashMap<>(16, 0.75f, true) {
    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, String> eldest) {
        return size() > 3;
    }
};
```

**面试要点**
- TreeMap 不适合高并发写（非线程安全且无分段锁）。
- LinkedHashMap 的 accessOrder 模式下，get 会改变节点顺序。

---

13. `CopyOnWriteArrayList` 原理与适用场景？

**参考答案：**

**核心原理**

- 读操作不加锁，直接读取底层数组。
- 写操作（add/set/remove）先加 ReentrantLock，复制一份新数组，修改后替换引用。
- 写操作期间读操作读到的是旧数据，保证最终一致性。

**适用场景**

- 读多写少，如配置列表、黑名单、白名单等。
- 对数据实时一致性要求不高的场景。

**缺点**

- 写操作开销大（需要复制整个数组）。
- 内存占用在写操作时翻倍。
- 迭代器不支持写操作（会抛 UnsupportedOperationException）。

**代码示例**

```java
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("a");
list.add("b");

// 读不加锁
String first = list.get(0);

// 写加锁并复制数组
list.add("c");
```

**面试要点**
- 读写分离思想的经典应用。
- 与 `Collections.synchronizedList` 相比，读性能更好，写性能更差。

---

14. `BlockingQueue` 的实现类与应用场景？

**参考答案：**

**主要实现类**

| 实现类 | 特点 |
|--------|------|
| `ArrayBlockingQueue` | 有界数组，FIFO，一把锁控制读写 |
| `LinkedBlockingQueue` | 可选有界链表，两把锁分离读写，吞吐量更高 |
| `PriorityBlockingQueue` | 支持优先级排序，无界 |
| `DelayQueue` | 元素只有到期才能取出，用于定时任务 |
| `SynchronousQueue` | 不存储元素，直接传递，线程一对一交接 |
| `LinkedTransferQueue` | 可 transfer，支持直接交付给消费者 |

**应用场景**

- 生产者-消费者模式。
- 线程池的任务队列（如 `LinkedBlockingQueue`）。
- 延迟任务调度（`DelayQueue`）。
- 流量削峰。

**代码示例**

```java
BlockingQueue<String> queue = new LinkedBlockingQueue<>(100);

// 生产者
queue.put("task"); // 队列满时阻塞

// 消费者
String task = queue.take(); // 队列空时阻塞
```

**面试要点**
- `ArrayBlockingQueue` 和 `LinkedBlockingQueue` 的核心区别：有界 vs 可选有界、单锁 vs 双锁。
  - `LinkedBlockingQueue` put时 last = last.next = newNode
  - `LinkedBlockingQueue` take时 head = head.nex    t
- `SynchronousQueue` 容量为 0，常用于 Executors.newCachedThreadPool。

---

1.  手写一个 LRU 缓存？

**参考答案：**

**核心思路**

利用 `LinkedHashMap` 的 accessOrder 模式，重写 `removeEldestEntry` 方法。

**代码示例**

```java
class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }
}

// 使用示例
LRUCache<Integer, String> cache = new LRUCache<>(2);
cache.put(1, "a");
cache.put(2, "b");
cache.get(1);       // 1 变为最近使用
cache.put(3, "c");  // 淘汰 2
System.out.println(cache.keySet()); // [1, 3]
```

**面试要点**
- 时间复杂度：get/put 均为 \(O(1)\)。
- 也可用 HashMap + 双向链表手写，面试中有时会要求不能直接用 LinkedHashMap。
- 线程安全版本可用 `Collections.synchronizedMap` 或 `ConcurrentHashMap` + 双向链表。

---

### 16. 并发编程

16. 线程的几种创建方式？线程池 7 个核心参数如何调优？

**参考答案：**

**线程创建方式**

1. 继承 `Thread` 类。
2. 实现 `Runnable` 接口。
3. 实现 `Callable` 接口（可返回结果）。
4. 使用线程池（推荐）。

**线程池 7 大参数**

```java
public ThreadPoolExecutor(
    int corePoolSize,           // 核心线程数
    int maximumPoolSize,        // 最大线程数
    long keepAliveTime,         // 非核心线程空闲存活时间
    TimeUnit unit,              // 时间单位
    BlockingQueue<Runnable> workQueue, // 任务队列
    ThreadFactory threadFactory,       // 线程工厂
    RejectedExecutionHandler handler   // 拒绝策略
)
```

**调优建议**

| 场景 | corePoolSize | maximumPoolSize | 队列 |
|------|-------------|-----------------|------|
| CPU 密集型 | CPU 核数 + 1 | 同上 | 较小有界队列 |
| IO 密集型 | 2 * CPU 核数 | 更大 | 较大有界队列 |
| 任务执行时间长 | 不宜过大 | 适中 | 有界队列，防止 OOM |

**拒绝策略**

- `AbortPolicy`：直接抛异常（默认）。
- `CallerRunsPolicy`：由调用线程执行任务。
- `DiscardPolicy`：静默丢弃。
- `DiscardOldestPolicy`：丢弃队列最老任务。

**代码示例**

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
    4, 8, 60, TimeUnit.SECONDS,
    new ArrayBlockingQueue<>(100),
    Executors.defaultThreadFactory(),
    new ThreadPoolExecutor.CallerRunsPolicy()
);
```

**面试要点**
- 优先使用线程池，不要手动创建线程。
- 任务提交流程：核心线程 → 队列 → 非核心线程 → 拒绝策略。

---

17. `synchronized` 锁升级过程？偏向锁、轻量级锁、重量级锁？

**参考答案：**

**锁升级过程**

```
无锁 → 偏向锁 → 轻量级锁 → 重量级锁
```

**偏向锁**

- 假设锁只被一个线程使用，mark word 记录线程 ID。
- 同一线程再次获取锁只需 CAS 替换线程 ID，无需重量级同步。
- JDK 15 后默认禁用偏向锁（JEP 374）。

**轻量级锁**

- 多线程竞争但冲突不激烈时，线程在栈帧中创建 Lock Record，通过 CAS 尝试将 mark word 指向 Lock Record。
- 自旋等待，避免线程阻塞唤醒开销。

**重量级锁**

- 自旋失败或竞争激烈时，升级为重量级锁，依赖操作系统 Mutex。
- 未获取锁的线程进入阻塞状态，需要用户态/内核态切换。

**代码示例**

```java
synchronized (obj) {
    // 同步代码块
}
```

**面试要点**
- 锁升级是单向的，不会降级（偏向锁可撤销但不是降级）。
- JDK 1.6 后引入锁升级优化，不要再说 synchronized 只是重量级锁。
- 对象头中的 mark word 存储锁状态信息。

---

18. `volatile` 关键字的作用？为什么不能保证原子性？

**参考答案：**

**volatile 两大作用**

1. **可见性**：一个线程修改了 volatile 变量，其他线程立即可见（通过内存屏障实现）。
2. **有序性**：禁止指令重排序。

**为什么不能保证原子性**

`i++` 操作实际分为三步：读取 i、i+1、写回 i。volatile 只能保证每次读取的是最新值，但不能保证这三步不被其他线程打断。

**代码示例**

```java
public class VolatileDemo {
    private volatile int count = 0;

    public void increment() {
        count++; // 非原子操作，多线程下仍可能丢失更新
    }
}
```

**面试要点**
- volatile 适合作为状态标志位，如 `boolean running = true`。
- 需要原子性时用 `AtomicInteger` 或 `synchronized`。

---

19. `CAS` 原理与 ABA 问题？如何解决？

**参考答案：**

**CAS 原理**

Compare And Swap，比较并交换。包含三个值：内存值 V、预期值 A、新值 B。当 V == A 时，将 V 更新为 B，否则不操作。

底层依赖 CPU 的原子指令（如 `cmpxchg`），Java 中通过 `Unsafe` 类实现。

**ABA 问题**

线程 1 读取值为 A，线程 2 将其改为 B 又改回 A，线程 1 的 CAS 仍能成功，但实际值已被修改过。

**解决方案**

- 使用 `AtomicStampedReference`：增加版本号/时间戳。
- 使用 `AtomicMarkableReference`：增加布尔标记。

**代码示例**

```java
AtomicInteger ai = new AtomicInteger(0);
ai.compareAndSet(0, 1); // CAS 更新

// 解决 ABA
AtomicStampedReference<Integer> asr = new AtomicStampedReference<>(100, 0);
asr.compareAndSet(100, 101, 0, 1);
```

**面试要点**
- CAS 优点：无锁、高性能。
- CAS 缺点：ABA 问题、自旋消耗 CPU、只能保证一个变量的原子性。

---

20. `AQS` 原理？`ReentrantLock`、`CountDownLatch`、`Semaphore` 底层实现？

**参考答案：**

**AQS 原理**

AbstractQueuedSynchronizer，抽象队列同步器。核心是一个 `int state` 变量和一个 FIFO 双向队列（CLH 变体）。

- 独占模式：`ReentrantLock`
- 共享模式：`CountDownLatch`、`Semaphore`、`ReadWriteLock`

**ReentrantLock**

- 独占锁，state 表示重入次数。
- 支持公平锁与非公平锁（默认非公平）。
- 底层依赖 AQS 的 `tryAcquire` / `tryRelease`。

**CountDownLatch**

- 共享锁，state 初始化为计数 N。
- 调用 `countDown()` 时 state 减 1。
- `await()` 阻塞直到 state 为 0。

**Semaphore**

- 共享锁，state 表示可用许可数。
- `acquire()` 减 1，`release()` 加 1。

**代码示例**

```java
// ReentrantLock
ReentrantLock lock = new ReentrantLock();
lock.lock();
try {
    // 临界区
} finally {
    lock.unlock();
}

// CountDownLatch
CountDownLatch latch = new CountDownLatch(3);
// 每个子线程执行完调用 latch.countDown()
latch.await(); // 主线程等待

// Semaphore
Semaphore semaphore = new Semaphore(10);
semaphore.acquire();
try {
    // 限流执行
} finally {
    semaphore.release();
}
```

**面试要点**
- AQS 是 JUC 包的核心框架。
- `ReentrantLock` 比 `synchronized` 更灵活，可中断、可超时、可公平、可绑定多个 Condition。

---

21. `ThreadLocal` 原理与内存泄漏问题？为什么必须 `remove()`？

**参考答案：**

**原理**

- 每个 `Thread` 对象内部有一个 `ThreadLocalMap`。
- `ThreadLocal` 作为 key（弱引用），value 是实际存储的对象。
- 通过 `threadLocal.get()` 实际是从当前线程的 ThreadLocalMap 中取值。

**内存泄漏原因**

- `ThreadLocal` 是弱引用，但 value 是强引用。
- 如果 ThreadLocal 被回收，key 变为 null，value 仍被 ThreadLocalMap 引用，无法被 GC。
- 在线程池场景下，线程长期存活，value 一直无法释放，导致内存泄漏。

**为什么必须 remove()**

- 显式清除 Entry，断开 value 的引用链。

**代码示例**

```java
private static final ThreadLocal<SimpleDateFormat> DATE_FORMAT =
    ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

try {
    SimpleDateFormat sdf = DATE_FORMAT.get();
    // use sdf
} finally {
    DATE_FORMAT.remove(); // 必须 remove
}
```

**面试要点**
- ThreadLocal 适用于线程隔离的场景，如日期格式化、数据库连接、用户上下文。
- 在线程池中必须使用 remove()，否则可能拿到上一个任务的残留数据。

---

22. `CompletableFuture` 用法与异步编排？

**参考答案：**

**核心用法**

`CompletableFuture` 是 JDK 8 引入的异步编程工具，支持链式调用、组合、异常处理。

**常用方法**

| 方法 | 说明 |
|------|------|
| `supplyAsync` | 有返回值异步执行 |
| `runAsync` | 无返回值异步执行 |
| `thenApply` | 串行处理结果 |
| `thenCompose` | 串行另一个 CompletableFuture |
| `thenCombine` | 合并两个异步结果 |
| `allOf` | 等待所有完成 |
| `anyOf` | 任意一个完成 |
| `exceptionally` / `handle` | 异常处理 |

**代码示例**

```java
CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "hello");
CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "world");

CompletableFuture<String> result = f1.thenCombine(f2, (s1, s2) -> s1 + " " + s2)
    .thenApply(String::toUpperCase)
    .exceptionally(ex -> "ERROR");

System.out.println(result.join()); // HELLO WORLD
```

**面试要点**
- 默认使用 `ForkJoinPool.commonPool()`，建议自定义线程池。
- `thenApply` 同步执行，`thenApplyAsync` 异步执行。
- 避免回调地狱，适合微服务并行调用场景。

---

23. 什么是伪共享（False Sharing）？如何避免？

**参考答案：**

**伪共享**

CPU 缓存以缓存行（Cache Line，通常 64 字节）为单位加载数据。如果两个独立变量位于同一缓存行，一个线程修改其中一个变量会导致整个缓存行失效，其他线程读取另一个变量时也需要重新从内存加载，严重影响性能。

**如何避免**

1. **缓存行填充（Padding）**：在变量前后填充无用字段，使其独占缓存行。
2. **使用 `@Contended` 注解**（JDK 8）：JVM 自动填充。
3. **避免多线程频繁修改相邻字段**。

**代码示例**

```java
// JDK 7 以前的手动填充
class PaddedLong {
    public volatile long value;
    public long p1, p2, p3, p4, p5, p6, p7; // 填充 56 字节
}

// JDK 8 使用 @Contended
class ContendedLong {
    @sun.misc.Contended
    public volatile long value;
}
```

**面试要点**
- 伪共享是高性能并发中的隐藏性能杀手。
- `Disruptor` 框架大量使用缓存行填充。

---

24. Java 内存模型（JMM）？happens-before 规则？

**参考答案：**

**JMM 核心**

Java Memory Model 定义了多线程环境下共享变量的访问规则，解决可见性、原子性、有序性问题。

- 所有变量存储在主内存中。
- 每个线程有自己的工作内存，保存变量的副本。
- 线程对变量的操作在工作内存中进行，再同步回主内存。

**happens-before 规则**

1. 程序次序规则：同一个线程内，前面的操作 happens-before 后面的操作。
2. 锁定规则：`unlock` happens-before 后面对同一把锁的 `lock`。
3. volatile 规则：对 volatile 变量的写 happens-before 后面对该变量的读。
4. 传递规则：A happens-before B，B happens-before C，则 A happens-before C。
5. 线程启动规则：`Thread.start()` happens-before 线程中的每个动作。
6. 线程终止规则：线程中的所有操作 happens-before 检测到线程终止（如 `join()` 返回）。
7. 中断规则：`interrupt()` happens-before 检测到中断事件。
8. 对象终结规则：构造函数执行 happens-before `finalize()`。

**面试要点**
- JMM 不是 JVM 内存结构，是抽象规范。
- happens-before 保证可见性，不一定保证物理上的执行顺序。

---

25. `Fork/Join` 框架原理？

**参考答案：**

**核心思想**

分而治之 + 工作窃取（Work-Stealing）。

- 大任务递归拆分成小任务，直到足够简单直接计算。
- 每个工作线程维护一个双端队列，当自己队列空时，从其他线程队列尾部"窃取"任务。

**核心类**

- `ForkJoinPool`：线程池。
- `RecursiveTask<T>`：有返回值任务。
- `RecursiveAction`：无返回值任务。

**代码示例**

```java
class SumTask extends RecursiveTask<Long> {
    private final long[] array;
    private final int start, end;
    private static final int THRESHOLD = 10000;

    SumTask(long[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        if (end - start <= THRESHOLD) {
            long sum = 0;
            for (int i = start; i < end; i++) sum += array[i];
            return sum;
        }
        int mid = (start + end) / 2;
        SumTask left = new SumTask(array, start, mid);
        SumTask right = new SumTask(array, mid, end);
        left.fork();
        long rightResult = right.compute();
        long leftResult = left.join();
        return leftResult + rightResult;
    }
}
```

**面试要点**
- 适合 CPU 密集型、可拆分任务。
- 任务拆分粒度要合适，过细会增加调度开销。
- `CompletableFuture` 和 `parallelStream` 底层也用 ForkJoinPool。

---

### 26. JVM

26. JVM 内存模型？堆、栈、方法区、程序计数器、本地方法栈分别放什么？

**参考答案：**

**JVM 运行时数据区**

| 区域 | 线程私有/共享 | 存储内容 |
|------|-------------|----------|
| 程序计数器 | 私有 | 当前线程执行的字节码行号指示器 |
| 虚拟机栈 | 私有 | 栈帧，局部变量、操作数栈、动态链接、方法返回地址 |
| 本地方法栈 | 私有 | Native 方法执行信息 |
| 堆 | 共享 | 对象实例、数组 |
| 方法区（元空间 Metaspace） | 共享 | 类信息、常量、静态变量、即时编译器编译后的代码 |

**JDK 8 变化**

- 永久代（PermGen）被移除，方法区使用本地内存的 Metaspace。
- 字符串常量池移入堆中。

**代码示例**

```java
public class Demo {
    private static int staticVar = 1; // 方法区/元空间
    private int instanceVar = 2;      // 堆

    public void method() {
        int localVar = 3;             // 虚拟机栈
        Object obj = new Object();    // obj 引用在栈，对象在堆
    }
}
```

**面试要点**
- 堆是 GC 的主要区域。
- 栈溢出（StackOverflowError）通常由递归过深引起。
- 元空间大小默认只受限于本地内存。

---

27. 垃圾回收算法有哪些？

**参考答案：**

**基础算法**

1. **标记-清除（Mark-Sweep）**
   - 先标记垃圾对象，再清除。
   - 缺点：产生内存碎片。

2. **复制算法（Copying）**
   - 内存分为两块，存活对象复制到另一块。
   - 优点：无碎片。缺点：内存利用率 50%。
   - 年轻代 Eden + Survivor 使用此算法。

3. **标记-整理（Mark-Compact）**
   - 标记后把存活对象向一端移动，清理边界外内存。
   - 优点：无碎片。缺点：移动对象开销大。
   - 老年代常用。

4. **分代收集**
   - 年轻代：复制算法。
   - 老年代：标记-清除或标记-整理。

**代码示例**

```java
// JVM 参数指定年轻代与老年代比例
-XX:NewRatio=2  // 老年代:年轻代 = 2:1
-XX:SurvivorRatio=8 // Eden:S0:S1 = 8:1:1
```

**面试要点**
- 年轻代对象朝生夕死，适合复制算法。
- 老年代对象存活率高，适合标记-整理。

---

28. CMS、G1、ZGC、Shenandoah 垃圾回收器的区别与适用场景？

**参考答案：**

| 收集器 | 算法 | 停顿时间 | 适用场景 |
|--------|------|---------|----------|
| CMS | 标记-清除 | 低停顿 | 老年代，追求低延迟 |
| G1 | 标记-整理 + 复制 | 可预测停顿 | 大堆内存，平衡吞吐与延迟 |
| ZGC | 染色指针 + 读屏障 | < 10ms | 超大堆（TB 级），极低延迟 |
| Shenandoah |  Brooks 指针 + 读屏障 | < 10ms | 低延迟，与 ZGC 类似 |

**CMS 工作流程**

1. 初始标记（STW，很短）
2. 并发标记
3. 重新标记（STW）
4. 并发清除

**G1 特点**

- 将堆划分为多个 Region。
- 维护优先列表，优先回收价值最大的 Region。
- 可设置期望停顿时间 `-XX:MaxGCPauseMillis`。

**面试要点**
- CMS 已废弃（JDK 14 移除）。
- G1 是 JDK 9+ 的默认垃圾回收器。
- ZGC 适合对延迟要求极高的金融、交易系统。

---

29. 什么是 STW？如何降低 GC 停顿？

**参考答案：**

**STW（Stop The World）**

GC 过程中暂停所有应用线程的时间。任何 GC 算法都有 STW，只是长短不同。

**降低 STW 的方法**

1. 选择合适的 GC 收集器（G1/ZGC/Shenandoah）。
2. 增大堆内存，减少 GC 频率。
3. 优化代码，减少对象创建和内存泄漏。
4. 调整新生代与老年代比例。
5. 使用对象池、缓存时注意生命周期。
6. 避免大对象频繁创建。

**代码示例**

```bash
# 启用 G1 并设置目标停顿时间
java -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -jar app.jar

# 启用 ZGC
java -XX:+UseZGC -jar app.jar
```

**面试要点**
- 低延迟系统优先考虑 ZGC/G1。
- STW 不可避免，只能尽量缩短。

---

30. JVM 调优常用参数？如何排查 OOM？

**参考答案：**

**常用参数**

```bash
-Xms4g -Xmx4g          # 堆初始/最大内存
-Xmn1g                 # 新生代大小
-XX:MetaspaceSize=256m # 元空间初始大小
-XX:MaxMetaspaceSize=512m
-XX:+UseG1GC           # 使用 G1
-XX:MaxGCPauseMillis=200
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=/path/to/dump
-XX:+PrintGCDetails
-XX:+PrintGCDateStamps
-Xloggc:/path/to/gc.log
```

**排查 OOM**

1. 查看日志，确定 OOM 类型（Heap Space、Metaspace、GC overhead 等）。
2. 生成堆转储文件（Heap Dump）。
3. 使用 MAT、JProfiler、VisualVM 分析大对象和内存泄漏。
4. 结合 GC 日志分析 GC 频率和停顿时间。
5. 检查代码中是否有内存泄漏：静态集合、未关闭资源、ThreadLocal 未 remove 等。

**面试要点**
- 生产环境务必开启 HeapDumpOnOutOfMemoryError。
- 调优前先监控，不要凭感觉调参。

---

31. 类加载机制？双亲委派模型？如何打破双亲委派？

**参考答案：**

**类加载过程**

1. **加载**：读取 .class 文件，生成 Class 对象。
2. **验证**：文件格式、元数据、字节码、符号引用验证。
3. **准备**：为静态变量分配内存并设置默认值。
4. **解析**：将符号引用替换为直接引用。
5. **初始化**：执行 `<clinit>()` 方法，赋值静态变量。
6. **使用**
7. **卸载**

**双亲委派模型**

- 类加载器收到加载请求时，先委派给父加载器。
- 父加载器无法加载时，子加载器才尝试加载。
- 加载器层次：Bootstrap → Extension → Application → 自定义。

**打破双亲委派**

1. **Tomcat**：Web 应用隔离，每个 Web 应用有自己的类加载器，先加载自己的类。
2. **SPI 机制**：如 JDBC，由 BootstrapClassLoader 加载的类需要加载实现类，但实现类在应用类路径下，因此使用 `Thread.currentThread().getContextClassLoader()`。
3. **OSGi**：模块化类加载器。

**代码示例**

```java
// 自定义类加载器
class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 自定义加载逻辑
        byte[] bytes = loadClassData(name);
        return defineClass(name, bytes, 0, bytes.length);
    }
}
```

**面试要点**
- 双亲委派保证核心类库安全，避免重复加载。
- 打破双亲委派是为了实现类隔离和 SPI 扩展。

---

32. JVM 如何定位 CPU 飙高、内存泄漏、死锁？

**参考答案：**

**CPU 飙高**

1. `top` 找到高 CPU 进程。
2. `top -Hp pid` 找到高 CPU 线程。
3. `jstack pid` 导出线程栈，将线程 ID 转为 16 进制定位代码。

**内存泄漏**

1. `jmap -dump:format=b,file=... pid` 生成堆 dump。
2. 使用 MAT 分析 dominator tree、leak suspects。
3. 查看 GC 日志，确认 Full GC 频繁但内存不下降。

**死锁**

1. `jstack -l pid` 查看死锁信息（Found one Java-level deadlock）。
2. 使用 `jconsole`、`jvisualvm` 图形化检测。

**代码示例**

```bash
# 查看线程 ID 16 进制
printf "%x\n" 12345

# 导出堆 dump
jmap -dump:live,format=b,file=heap.hprof pid

# 查看死锁
jstack -l pid | grep -A 50 "deadlock"
```

**面试要点**
- 线上问题排查三板斧：日志、jstack、jmap。
- 排查前先保留现场，避免重启丢失信息。

---

33. 对象的内存布局？对象头包含什么？

**参考答案：**

**对象内存布局**

```
对象头（Header）
├── Mark Word（32/64 位，含锁状态、hashCode、GC 年龄等）
├── Klass Pointer（指向类元数据）
└── 数组长度（仅数组对象有）
实例数据（Instance Data）
对齐填充（Padding，8 字节对齐）
```

**Mark Word 内容**

- 哈希码（identity hashCode）
- GC 分代年龄（4 bit，最大 15）
- 锁状态标志
- 偏向线程 ID / 锁记录指针 / 重量级锁指针

**代码示例**

```java
// 使用 JOL 工具查看对象布局
<dependency>
    <groupId>org.openjdk.jol</groupId>
    <artifactId>jol-core</artifactId>
    <version>0.17</version>
</dependency>

System.out.println(ClassLayout.parseInstance(new Object()).toPrintable());
```

**面试要点**
- 对象头在 64 位 JVM 下默认 12 字节（开启压缩指针）。
- 锁信息存储在对象头中，是 synchronized 优化的基础。

---

### 34. Spring / Spring Boot

34. Spring IoC 与 AOP 原理？

**参考答案：**

**IoC（控制反转）**

- 对象的创建和依赖关系由 Spring 容器管理，而不是由对象自身控制。
- 通过 DI（依赖注入）实现，方式：构造器注入、Setter 注入、字段注入。
- Spring 容器读取配置，实例化 Bean，维护 Bean 生命周期。

**AOP（面向切面编程）**

- 将横切关注点（日志、事务、权限）从业务逻辑中剥离。
- 核心概念：切面（Aspect）、连接点（JoinPoint）、切入点（Pointcut）、通知（Advice）、织入（Weaving）。
- 底层通过动态代理实现。

**代码示例**

```java
@Service
public class UserService {
    @Autowired
    private UserDao userDao; // 依赖注入
}

@Aspect
@Component
public class LogAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void log() {
        System.out.println("method before");
    }
}
```

**面试要点**
- 推荐使用构造器注入，便于测试且避免循环依赖。
- AOP 代理对象调用同类方法时，通知不会生效（自调用问题）。

---

35. Spring Bean 生命周期？作用域有哪些？

**参考答案：**

**Bean 生命周期**

1. 实例化（构造方法）。
2. 属性赋值（依赖注入）。
3. 如果实现 `BeanNameAware`、`BeanFactoryAware`、`ApplicationContextAware`，调用对应方法。
4. 调用 `BeanPostProcessor.postProcessBeforeInitialization`。
5. 调用 `@PostConstruct` / `InitializingBean.afterPropertiesSet` / init-method。
6. 调用 `BeanPostProcessor.postProcessAfterInitialization`（AOP 代理在此生成）。
7. Bean 使用。
8. 容器关闭时调用 `@PreDestroy` / `DisposableBean.destroy` / destroy-method。

**作用域**

| 作用域 | 说明 |
|--------|------|
| singleton | 默认，每个 Spring 容器一个实例 |
| prototype | 每次获取新建一个实例 |
| request | 每个 HTTP 请求一个 |
| session | 每个 HTTP Session 一个 |
| application | 整个 ServletContext 一个 |

**面试要点**
- singleton 的 Bean 要注意线程安全。
- prototype 作用域的 Bean 销毁不由容器管理。

---

36. Spring AOP 中 JDK 动态代理与 CGLIB 的区别？

**参考答案：**

| 维度 | JDK 动态代理 | CGLIB |
|------|-------------|-------|
| 目标类 | 必须实现接口 | 不需要接口 |
| 实现方式 | 实现相同接口 | 继承目标类生成子类 |
| 性能 | 反射调用，略慢 | 使用 FastClass，较快 |
| final 方法 | 支持 | 不能代理 final 方法 |
| Spring 默认 | 目标有接口时用 JDK | 无接口时用 CGLIB |

**代码示例**

```java
// JDK 动态代理
InvocationHandler handler = (proxy, method, args) -> {
    System.out.println("before");
    return method.invoke(target, args);
};
Object proxy = Proxy.newProxyInstance(
    target.getClass().getClassLoader(),
    target.getClass().getInterfaces(),
    handler
);
```

**面试要点**
- Spring Boot 2.x 默认使用 CGLIB，即使目标实现了接口。
- 自调用问题：代理对象内的方法调用不会经过 AOP 代理。

---

37. Spring 事务传播机制？

**参考答案：**

**七种传播行为**

| 传播行为 | 说明 |
|----------|------|
| REQUIRED | 默认，当前无事务则新建，有则加入 |
| SUPPORTS | 有事务则加入，无则以非事务执行 |
| MANDATORY | 必须有事务，否则抛异常 |
| REQUIRES_NEW | 挂起当前事务，新建独立事务 |
| NOT_SUPPORTED | 挂起当前事务，以非事务执行 |
| NEVER | 必须无事务，否则抛异常 |
| NESTED | 在当前事务中创建 savepoint 嵌套事务 |

**常见坑**

- `@Transactional` 只对 public 方法生效。
- 自调用不会触发事务代理。
- 异常被捕获后事务不会回滚。
- 默认只回滚 RuntimeException 和 Error。

**代码示例**

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void methodB() {
    // 独立事务
}

@Transactional(rollbackFor = Exception.class)
public void methodA() throws Exception {
    // 捕获异常后需要手动回滚
}
```

**面试要点**
- `REQUIRED` 与 `REQUIRES_NEW` 最常用，也是面试最爱考的。
- `NESTED` 依赖数据库 savepoint 支持。

---

38. Spring Boot 自动配置原理？

**参考答案：**

**核心机制**

1. `@SpringBootApplication` 包含 `@EnableAutoConfiguration`。
2. `@EnableAutoConfiguration` 通过 `AutoConfigurationImportSelector` 读取 `META-INF/spring.factories`（Spring Boot 2.7+ 改为 `META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports`）。
3. 根据类路径、配置项、条件注解（`@ConditionalOnClass`、`@ConditionalOnProperty` 等）决定是否加载自动配置类。
4. 自动配置类向容器注册 Bean。

**条件注解**

- `@ConditionalOnClass`：类路径存在指定类。
- `@ConditionalOnMissingBean`：容器中没有指定 Bean。
- `@ConditionalOnProperty`：配置项满足条件。

**代码示例**

```java
@Configuration
@ConditionalOnClass(DataSource.class)
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceProperties properties) {
        return DataSourceBuilder.create()
            .url(properties.getUrl())
            .build();
    }
}
```

**面试要点**
- 自动配置不是魔法，本质是条件化的 Bean 注册。
- 可以通过 `spring.autoconfigure.exclude` 排除不需要的自动配置。

---

39. Spring Boot Starter 自定义流程？

**参考答案：**

**自定义 Starter 步骤**

1. 创建 Maven/Gradle 项目，依赖 `spring-boot-autoconfigure`。
2. 编写自动配置类，使用 `@Configuration` 和条件注解。
3. 编写属性配置类，使用 `@ConfigurationProperties`。
4. 在 `META-INF/spring/` 下创建 `org.springframework.boot.autoconfigure.AutoConfiguration.imports` 文件，写入自动配置类全限定名。
5. 打包发布，其他项目引入即可自动生效。

**代码示例**

```java
@Configuration
@ConditionalOnClass(MyService.class)
@EnableConfigurationProperties(MyProperties.class)
public class MyAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MyService myService(MyProperties props) {
        return new MyService(props.getName());
    }
}

@ConfigurationProperties(prefix = "my.starter")
public class MyProperties {
    private String name;
    // getter/setter
}
```

**面试要点**
- Starter 的核心是自动配置 + 约定大于配置。
- 命名规范：`xxx-spring-boot-starter`。

---

40. Spring MVC 请求处理流程？

**参考答案：**

**流程**

1. 请求到达 `DispatcherServlet`。
2. `HandlerMapping` 根据 URL 找到对应的 Handler（Controller 方法）。
3. `HandlerAdapter` 调用 Handler 执行业务逻辑。
4. Handler 返回 `ModelAndView` 或响应体。
5. 如果有视图，`ViewResolver` 解析视图。
6. 渲染视图并返回客户端。

**核心组件**

- `DispatcherServlet`：前端控制器。
- `HandlerMapping`：URL 映射。
- `HandlerAdapter`：适配器。
- `HandlerExceptionResolver`：异常处理。
- `ViewResolver`：视图解析。

**代码示例**

```java
@RestController
public class UserController {
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getById(id);
    }
}
```

**面试要点**
- Spring Boot 中 `@RestController` 默认使用 `RequestMappingHandlerAdapter`。
- 拦截器（Interceptor）在 Handler 执行前后、视图渲染前后介入。

---

41. Spring Cloud 核心组件？

**参考答案：**

**核心组件**

| 功能 | 组件 |
|------|------|
| 服务注册与发现 | Eureka、Nacos、Consul |
| 负载均衡 | Ribbon / Spring Cloud LoadBalancer |
| 服务调用 | OpenFeign |
| 熔断降级 | Hystrix / Sentinel |
| 网关 | Gateway / Zuul |
| 配置中心 | Config / Nacos |
| 链路追踪 | Sleuth + Zipkin / SkyWalking |

**面试要点**
- Nacos 同时支持注册中心和配置中心。
- Gateway 基于 WebFlux，性能优于 Zuul 1.x。
- Sentinel 是阿里开源，功能比 Hystrix 更丰富。

---

42. Spring 循环依赖如何解决？三级缓存机制？

**参考答案：**

**循环依赖场景**

A 依赖 B，B 依赖 A。Spring 通过三级缓存解决单例 Bean 的循环依赖（构造器注入除外）。

**三级缓存**

1. **singletonObjects**：一级缓存，存放完全初始化好的 Bean。
2. **earlySingletonObjects**：二级缓存，存放提前暴露的 Bean（已实例化但未填充属性）。
3. **singletonFactories**：三级缓存，存放用于生成提前暴露 Bean 的 ObjectFactory。

**解决流程**

1. 创建 A，实例化后放入三级缓存。
2. A 填充属性时发现需要 B。
3. 创建 B，实例化后放入三级缓存。
4. B 填充属性时发现需要 A，从三级缓存获取 A 的早期引用。
5. B 初始化完成，放入一级缓存。
6. A 继续填充属性，初始化完成，放入一级缓存。

**代码示例**

```java
// 三级缓存源码核心
private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
private final Map<String, Object> earlySingletonObjects = new ConcurrentHashMap<>(16);
private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
```

**面试要点**
- 构造器注入的循环依赖无法解决，可用 `@Lazy` 延迟加载。
- 三级缓存主要是为了解决 AOP 代理对象的循环依赖（需要 ObjectFactory）。
- prototype 作用域 Bean 不支持循环依赖。

---

### 43. 数据库与缓存

43. MySQL 为什么用 B+ 树做索引？

**参考答案：**

**B+ 树优势**

1. **磁盘 IO 次数少**：B+ 树高度低，每个节点可存储更多关键字。
2. **顺序访问高效**：叶子节点通过指针相连，适合范围查询和排序。
3. **稳定查询性能**：所有查询都到叶子节点，路径长度相同。
4. **空间利用率高**：非叶子节点只存键值，不存数据，一页能存更多索引。

**与 B 树、红黑树对比**

| 树 | 特点 |
|----|------|
| B 树 | 非叶子节点也存数据，范围查询需回溯 |
| B+ 树 | 数据只在叶子节点，叶子节点链表连接 |
| 红黑树 | 二叉树，高度高，磁盘 IO 多 |

**面试要点**
- InnoDB 主键索引是聚簇索引，叶子节点存整行数据。
- 二级索引叶子节点存主键值，回表查询。

---

44. MySQL 事务隔离级别？MVCC 机制？RR 如何避免幻读？

**参考答案：**

**隔离级别**

| 级别 | 问题 |
|------|------|
| READ UNCOMMITTED | 脏读、不可重复读、幻读 |
| READ COMMITTED | 不可重复读、幻读 |
| REPEATABLE READ | 幻读（InnoDB 基本解决） |
| SERIALIZABLE | 无 |

**MVCC（多版本并发控制）**

- 每行记录有隐藏字段：DB_TRX_ID（事务 ID）、DB_ROLL_PTR（回滚指针）。
- Undo Log 保存历史版本。
- Read View 决定事务可见哪个版本。

**RR 如何避免幻读**

- 快照读（普通 SELECT）：通过 MVCC 保证可重复读。
- 当前读（SELECT FOR UPDATE）：通过间隙锁（Gap Lock）和临键锁（Next-Key Lock）防止幻读。

**代码示例**

```sql
-- 查看隔离级别
SELECT @@transaction_isolation;

-- 当前读
SELECT * FROM user WHERE age > 20 FOR UPDATE;
```

**面试要点**
- InnoDB 的 RR 级别在快照读下基本无幻读，但当前读需要锁机制配合。
- MVCC 解决了读写冲突，写冲突由锁解决。

---

45. MySQL 死锁如何排查与预防？

**参考答案：**

**排查**

1. `SHOW ENGINE INNODB STATUS` 查看最近一次死锁信息。
2. 开启死锁日志：`innodb_print_all_deadlocks=ON`。
3. 结合业务日志定位 SQL。

**预防**

1. 固定加锁顺序。
2. 尽量使用索引，避免全表扫描导致间隙锁范围过大。
3. 缩短事务长度，减少锁持有时间。
4. 使用低隔离级别（如 READ COMMITTED）。
5. 批量操作分批处理。

**面试要点**
- 死锁是互相等待，不是单线程锁等待。
- InnoDB 会自动检测死锁并回滚代价较小的事务。

---

46. MySQL 主从复制原理？复制延迟解决方案？

**参考答案：**

**复制原理**

1. Master 将变更写入 binlog。
2. Slave 的 IO 线程读取 binlog 并写入 relay log。
3. Slave 的 SQL 线程重放 relay log。

**复制方式**

- 异步复制：Master 不等待 Slave，性能高但可能丢失数据。
- 半同步复制：至少一个 Slave 收到并写入 relay log 后 Master 才返回。
- 组复制（MGR）：基于 Paxos，强一致。

**延迟解决方案**

1. 读写分离时，强制走主库查询关键数据。
2. 使用缓存缓解读压力。
3. 拆分大事务，减少 SQL 线程重放压力。
4. 并行复制（MySQL 5.7+）。
5. 提升从库硬件性能。

**面试要点**
- binlog 格式：Statement、Row、Mixed，Row 模式最常用。
- GTID 可以简化主从切换。

---

47. Redis 为什么单线程还这么快？

**参考答案：**

**原因**

1. **纯内存操作**：数据在内存中，读写速度极快。
2. **单线程避免上下文切换**：没有多线程竞争锁的开销。
3. **IO 多路复用**：基于 epoll/kqueue/select 处理大量连接。
4. **高效数据结构**：SDS、跳表、压缩列表、整数集合等。
5. **RESP 协议简单**：解析开销小。

**Redis 6.0+**

- 网络 IO 多线程，但命令执行仍是单线程。

**面试要点**
- 单线程指命令执行单线程，不是整个 Redis 只有一个线程。
- 单线程避免了锁竞争，但不适合 CPU 密集型命令。

---

48. Redis 持久化 RDB 与 AOF 对比？

**参考答案：**

| 维度 | RDB | AOF |
|------|-----|-----|
| 机制 | 快照，保存某一时刻全量数据 | 记录每次写命令 |
| 文件大小 | 紧凑，体积小 | 较大，可重写压缩 |
| 恢复速度 | 快 | 慢 |
| 数据安全 | 可能丢失最后一次快照后数据 | 取决于刷盘策略，最多丢 1 秒 |
| 性能 | 快照时 fork 子进程，有短暂阻塞 | 持续写盘，性能略低 |

**混合持久化（Redis 4.0+）**

- AOF 文件前半部分是 RDB 全量数据，后半部分是增量 AOF 命令。
- 兼顾恢复速度和数据安全。

**代码示例**

```conf
# AOF 每秒刷盘
appendonly yes
appendfsync everysec

# 开启混合持久化
aof-use-rdb-preamble yes
```

**面试要点**
- 生产环境通常 RDB + AOF 同时开启。
- `appendfsync always` 最安全但最慢，`no` 最快但不安全。

---

49. Redis 分布式锁实现？

**参考答案：**

**SETNX 方式**

```bash
SET resource_name my_random_value NX PX 30000
```

- `NX`：只有 key 不存在时才设置。
- `PX`：设置过期时间，防止死锁。
- `my_random_value`：释放时校验，防止误删他人锁。

**Redisson**

- 提供可重入锁、看门狗自动续期、公平锁、读写锁等。
- 看门狗机制：默认锁 30 秒，每 10 秒续期。

**Redlock**

- 在多个独立 Redis 节点上加锁，超过半数成功且总耗时小于超时时间才认为加锁成功。
- 实现复杂，争议较大。

**代码示例**

```java
RLock lock = redisson.getLock("order:lock");
try {
    lock.lock();
    // 业务逻辑
} finally {
    lock.unlock();
}
```

**面试要点**
- 必须设置过期时间，防止服务宕机导致死锁。
- 释放锁时必须判断锁是否属于自己。
- Redisson 是生产环境首选。

---

50. 缓存穿透、击穿、雪崩的区别与解决方案？

**参考答案：**

| 问题 | 现象 | 解决方案 |
|------|------|----------|
| 缓存穿透 | 查询不存在数据，绕过缓存直达 DB | 布隆过滤器、缓存空值、参数校验 |
| 缓存击穿 | 热点 key 过期，大量请求打向 DB | 互斥锁、逻辑过期、永不过期 |
| 缓存雪崩 | 大量 key 同时过期，DB 压力剧增 | 随机过期时间、多级缓存、熔断降级 |

**代码示例**

```java
// 缓存空值
String value = redis.get(key);
if (value == null) {
    value = db.query(key);
    if (value == null) {
        redis.setex(key, 60, ""); // 缓存空值
    } else {
        redis.setex(key, 600, value);
    }
}
```

**面试要点**
- 穿透是查询不存在数据，击穿是热点 key 失效，雪崩是批量失效。
- 布隆过滤器有误判率，适合拦截明显不存在的数据。

---

51. 缓存与数据库一致性如何保证？

**参考答案：**

**Cache-Aside（旁路缓存）**

- 读：先读缓存，未命中读 DB 并写入缓存。
- 写：先更新 DB，再删缓存。

**双写一致性方案**

| 方案 | 优点 | 缺点 |
|------|------|------|
| 先删缓存再更新 DB | 简单 | 并发下缓存旧数据 |
| 先更新 DB 再删缓存 | 相对可靠 | 删除失败会不一致 |
| 延时双删 | 降低不一致概率 | 实现复杂 |
| Canal 监听 binlog | 最终一致 | 引入额外组件 |

**推荐**

- 强一致场景：使用分布式锁或读写串行化。
- 最终一致场景：先更新 DB，再删缓存，配合消息队列或 Canal 补偿。

**面试要点**
- 删缓存而不是更新缓存，避免并发更新导致脏数据。
- 不需要追求绝对一致，根据业务容忍度选择方案。

---

### 52. 分布式与微服务

52. 分布式事务方案对比？

**参考答案：**

| 方案 | 原理 | 优点 | 缺点 |
|------|------|------|------|
| 2PC | 准备 + 提交，协调者统一管理 | 强一致 | 同步阻塞、单点故障 |
| 3PC | 2PC + CanCommit，引入超时 | 改善阻塞 | 实现复杂 |
| TCC | Try-Confirm-Cancel，业务层实现 | 性能高 | 业务侵入大 |
| Saga | 长事务拆分为本地事务，失败补偿 | 适合长事务 | 暂时不一致 |
| 本地消息表 | 本地事务 + 消息表 + 定时补偿 | 最终一致 | 需要幂等 |
| 最大努力通知 | 异步通知 + 查询对账 | 简单 | 延迟大 |
| Seata AT | 代理数据源，自动解析 SQL 生成反向 SQL | 低侵入 | 性能有损耗 |

**面试要点**
- 没有完美的方案，根据一致性和性能要求选择。
- 金融支付等场景用强一致，普通业务用最终一致。

---

53. 接口幂等性设计？

**参考答案：**

**幂等性定义**

同一操作执行多次，结果与执行一次相同。

**设计方案**

1. **Token 机制**：客户端先申请 Token，请求时携带，服务端消费 Token。
2. **唯一键/唯一索引**：数据库唯一约束防止重复插入。
3. **状态机**：订单状态不可逆，重复请求无效。
4. **乐观锁**：版本号控制。
5. **分布式锁**：Redis/ ZooKeeper 锁。

**代码示例**

```java
// Redis Token 幂等
String token = request.getHeader("idempotency-token");
Boolean success = redisTemplate.delete(token); // 原子删除
if (!success) {
    throw new DuplicateRequestException();
}
// 执行业务
```

**面试要点**
- 幂等性通常针对写操作，特别是网络超时重试场景。
- Token 机制需要保证生成和删除的原子性。

---

54. 分布式 ID 生成方案？Snowflake 雪花算法？

**参考答案：**

**常见方案**

1. 数据库自增 ID：简单，但单点、性能瓶颈。
2. UUID：无序、字符串、占用空间大。
3. Redis 自增：性能好，但依赖 Redis。
4. 雪花算法：趋势递增、高性能、可自定义。
5. 美团 Leaf、百度 UidGenerator：改进版雪花算法。

**Snowflake 结构**

```
0 | 0000000000 0000000000 0000000000 0000000000 0 | 00000 | 000000000000
符号位 | 41 位时间戳（毫秒） | 10 位机器 ID | 12 位序列号
```

**面试要点**
- 雪花算法依赖时钟，时钟回拨会生成重复 ID。
- 41 位时间戳可用约 69 年。

---

55. Kafka 消息投递语义？

**参考答案：**

| 语义 | 含义 | 实现方式 |
|------|------|----------|
| at-most-once | 最多一次，可能丢消息 | 不 ack 或自动提交 offset |
| at-least-once | 至少一次，可能重复 | 消费后处理再 ack |
| exactly-once | 恰好一次 | 幂等 + 事务 / 业务去重 |

**Kafka 实现 exactly-once**

- Producer 幂等性：`enable.idempotence=true`。
- 事务：跨分区的原子写入。
- Consumer 端：手动提交 offset + 业务幂等。

**面试要点**
- 真正的 exactly-once 需要生产端、Broker 端、消费端共同配合。
- 业务上通常用 at-least-once + 幂等实现。

---

56. Netty Reactor 模型与 NIO 基础？

**参考答案：**

**NIO 三要素**

- Channel：通道，负责连接和数据传输。
- Buffer：缓冲区，数据读写的中介。
- Selector：多路复用器，一个线程管理多个 Channel。

**Reactor 模型**

1. **单线程 Reactor**：一个线程同时处理 accept、read、write、业务逻辑。
2. **多线程 Reactor**：一个线程处理 accept，线程池处理 IO 和业务。
3. **主从 Reactor**：主线程处理 accept，从线程池处理 read/write，业务线程池处理逻辑。

**Netty 模型**

- 主从 Reactor 多线程模型。
- BossGroup 处理连接，WorkerGroup 处理 IO。
- Pipeline 责任链处理入站/出站事件。

**面试要点**
- Netty 高性能原因：零拷贝、内存池、对象池、ByteBuf、无锁化串行设计。
- 适合高并发网络编程，如 RPC、IM、游戏服务器。

---

57. Elasticsearch 倒排索引原理？

**参考答案：**

**倒排索引**

- 传统索引：文档 → 词项。
- 倒排索引：词项 → 文档列表。

**结构**

```
term    | doc freq | postings list
"java"  | 3        | [doc1, doc3, doc5]
"redis" | 2        | [doc2, doc4]
```

**优化**

- 分词（Analyzer）：将文本切分为 term。
- 压缩 posting list（FOR、Roaring Bitmap）。
- 跳表加速合并。

**面试要点**
- ES 适合全文检索、日志分析、聚合查询。
- 倒排索引是 ES 高效搜索的核心。

---

58. 微服务拆分原则？服务间通信方式？

**参考答案：**

**拆分原则**

1. 按业务能力拆分（DDD 限界上下文）。
2. 高内聚、低耦合。
3. 数据独立性，每个服务拥有自己的数据库。
4. 避免过度拆分，警惕分布式事务复杂度。

**服务间通信**

| 方式 | 特点 |
|------|------|
| REST/HTTP | 简单通用，适合外部调用 |
| gRPC | 基于 HTTP/2 + Protobuf，高性能 |
| 消息队列 | 异步解耦，最终一致 |
| Dubbo | 基于 TCP，适合内部 RPC |

**面试要点**
- 拆分不是目的，是为了独立演进和扩展。
- 服务粒度没有标准答案，根据团队能力和业务复杂度决定。

---

59. 限流、熔断、降级的设计与实现？

**参考答案：**

**限流**

- 控制单位时间内请求数量。
- 算法：计数器、滑动窗口、漏桶、令牌桶。
- 实现：Sentinel、Guava RateLimiter、Nginx limit_req。

**熔断**

- 当失败率达到阈值，快速失败，避免拖垮系统。
- 状态：Closed → Open → Half-Open。
- 实现：Hystrix、Sentinel。

**降级**

- 系统压力大时，关闭非核心功能，保证核心功能可用。
- 如商品详情页不展示推荐列表，只展示基本信息。

**代码示例**

```java
// Sentinel 限流
FlowRule rule = new FlowRule();
rule.setResource("hello");
rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
rule.setCount(10); // QPS 10
FlowRuleManager.loadRules(Collections.singletonList(rule));
```

**面试要点**
- 限流是防御，熔断是止损，降级是保核心。
- 三者通常配合使用。

---

60. 注册中心选型？Nacos / Consul / Eureka 对比？

**参考答案：**

| 维度 | Eureka | Consul | Nacos |
|------|--------|--------|-------|
| 开发方 | Netflix | HashiCorp | 阿里 |
| 一致性 | AP | CP/AP 可选 | AP/CP 可选 |
| 配置中心 | 不支持 | 支持 | 支持 |
| 健康检查 | 客户端心跳 | TCP/HTTP/gRPC | TCP/HTTP/MYSQL |
| 多数据中心 | 支持较弱 | 原生支持 | 支持 |
| 生态 | Spring Cloud Netflix | Spring Cloud / K8s | Spring Cloud Alibaba |

**面试要点**
- Eureka 2.x 停止维护，新项目多用 Nacos。
- Nacos 同时作为注册中心和配置中心，生态完善。
- Consul 适合与 Kubernetes 配合使用。

---

## 二、AI / 大模型面试题

### 1. Transformer 与注意力机制

1. Self-Attention 的计算流程？时间/空间复杂度？为什么是 \(O(n^2)\)？

**参考答案：**

**计算流程**

给定输入序列 \(X \in \mathbb{R}^{n \times d}\)，通过三个权重矩阵得到 Query、Key、Value：

\[Q = XW_Q, \quad K = XW_K, \quad V = XW_V\]

然后计算注意力输出：

\[\text{Attention}(Q, K, V) = \text{softmax}\left(\frac{QK^T}{\sqrt{d_k}}\right)V\]

**复杂度分析**

- \(QK^T\) 计算：\((n \times d) \cdot (d \times n) = n^2 d\)，时间复杂度 \(O(n^2 d)\)。
- Attention Score 矩阵大小 \(n \times n\)，空间复杂度 \(O(n^2)\)。

**为什么是 \(O(n^2)\)**

每个 token 都要与序列中所有其他 token 计算相似度，token 数翻倍，计算量翻 4 倍。

**面试要点**
- \(\sqrt{d_k}\) 是为了防止点积值过大导致 softmax 梯度消失。
- Self-Attention 的 \(O(n^2)\) 复杂度是长文本扩展的主要瓶颈。

---

2. Multi-Head Attention 的作用？MHA、MQA、GQA 的区别？

**参考答案：**

**Multi-Head Attention 作用**

将 Q、K、V 投影到多个子空间，分别计算 Attention，最后拼接。让模型关注不同位置和不同语义子空间的信息。

\[\text{MultiHead}(Q,K,V) = \text{Concat}(\text{head}_1, ..., \text{head}_h)W_O\]

**MHA / MQA / GQA 对比**

| 类型 | 说明 | 参数量 / KV Cache |
|------|------|-------------------|
| MHA（Multi-Head Attention） | 每个头有独立的 Q、K、V | 大 |
| MQA（Multi-Query Attention） | 所有头共享同一组 K、V | KV Cache 最小 |
| GQA（Grouped-Query Attention） | K、V 分组共享，介于 MHA 和 MQA 之间 | 平衡 |

**面试要点**
- MQA/GQA 主要是为推理加速、减少 KV Cache 显存占用。
- LLaMA 2/3、Mistral 等模型广泛使用 GQA。

---

3. Transformer 中为什么使用 Layer Norm 而不是 Batch Norm？

**参考答案：**

**Batch Norm 问题**

- 对 batch 维度求均值方差，NLP 中序列长度变化大，batch 统计不稳定。
- 推理时依赖训练时的 moving statistics，对序列长度敏感。
- 分布式训练时 batch 维度可能被切分，实现复杂。

**Layer Norm 优势**

- 对每个样本、每个时间步的特征维度做归一化，不依赖 batch。
- 训练和推理逻辑一致。
- 更适合序列建模和自回归生成。

**代码示例**

```python
import torch.nn as nn
nn.LayerNorm(normalized_shape=hidden_size)
```

**面试要点**
- Pre-Norm 和 Post-Norm：GPT 用 Post-Norm，LLaMA 用 Pre-Norm更稳定。
- RMSNorm 是 Layer Norm 的简化版本，去掉了均值归一化，LLaMA 使用。

---

4. 位置编码有哪些？绝对位置编码 vs 相对位置编码？RoPE 原理？

**参考答案：**

**位置编码类型**

1. **绝对位置编码**：为每个位置生成固定向量，加到词嵌入上（Transformer 原始论文使用）。
2. **可学习位置编码**：BERT 使用，把位置当作可训练参数。
3. **相对位置编码**：建模 token 之间的相对距离，如 Transformer-XL、T5。
4. **RoPE（Rotary Position Embedding）**：通过旋转矩阵注入位置信息，LLaMA、ChatGLM 使用。

**RoPE 原理**

将二维词嵌入 \(x_m, x_n\) 分别乘以旋转矩阵 \(R_m, R_n\)，使得内积 \(q_m^T k_n\) 只与相对位置 \(m-n\) 有关：

\[f(q, m) = R_m q, \quad f(k, n) = R_n k\]

**面试要点**
- RoPE 的优势：外推性好、相对位置显式编码、与 Attention 计算自然结合。
- 长文本扩展常用 RoPE 外推或插值（NTK-aware scaling）。

---

5. Transformer 的三种架构区别？为什么 LLM 多用 Decoder-only？

**参考答案：**

**三种架构**

| 架构 | 代表 | 特点 |
|------|------|------|
| Encoder-only | BERT | 双向 Attention，适合理解任务 |
| Decoder-only | GPT、LLaMA | 自回归，单向 Attention，适合生成 |
| Encoder-Decoder | T5、BART | 编码器双向，解码器自回归，适合翻译/摘要 |

**为什么 LLM 多用 Decoder-only**

1. 自回归生成天然适合文本生成。
2. 单向 Attention 计算效率更高（可用 KV Cache 加速）。
3. 模型 Scaling 效果好，涌现能力更强。
4. 训练目标简单（下一个 token 预测）。

**面试要点**
- BERT 用 MLM（掩码语言模型），GPT 用 CLM（因果语言模型）。
- Decoder-only 在长文本生成上有天然优势。

---

6. Feed Forward Network（FFN）的作用？

**参考答案：**

**作用**

1. 对每个 token 独立进行非线性变换，增加模型表达能力。
2. 将 Attention 提取的特征投影到更高维空间再降维，增加非线性。

**结构**

\[\text{FFN}(x) = \sigma(xW_1 + b_1)W_2 + b_2\]

通常中间维度是输入维度的 4 倍（如 512 → 2048）。

**面试要点**
- FFN 的计算量占 Transformer 总计算量的很大一部分。
- 激活函数：ReLU、GELU（GPT/BERT）、SwiGLU（LLaMA 2/3）。

---

7. 什么是 Flash Attention？优化点在哪里？

**参考答案：**

**核心思想**

通过 tiling 和 recomputation，在 SRAM（高速缓存）中分块计算 Attention，避免将巨大的 \(n \times n\) Attention Score 矩阵写入 HBM（显存）。

**优化点**

1. **IO-Aware**：减少 HBM 读写次数。
2. **分块计算**：每次只加载一小块 Q、K、V 到 SRAM。
3. **Softmax  online 算法**：分块计算时动态维护 softmax 的归一化因子。
4. **反向传播时重计算**：不保存中间 Attention 矩阵，反向时重新计算。

**效果**

- 显存从 \(O(n^2)\) 降到 \(O(n)\)。
- 计算量不变，但因内存访问减少，实际训练/推理速度大幅提升。

**面试要点**
- Flash Attention 不是减少计算量，而是减少显存 IO。
- FlashAttention-2/3 进一步优化了 warp 级并行和异步。

---

8. BERT 与 GPT 的核心区别？预训练任务分别是什么？

**参考答案：**

| 维度 | BERT | GPT |
|------|------|-----|
| 架构 | Encoder-only | Decoder-only |
| Attention | 双向 | 单向（因果） |
| 预训练任务 | MLM + NSP | 下一个 token 预测 |
| 适用 | 理解任务 | 生成任务 |

**BERT 预训练任务**

- **MLM（Masked Language Model）**：随机 mask 15% token，预测被 mask 的词。
- **NSP（Next Sentence Prediction）**：判断两个句子是否相邻（后续版本如 RoBERTa 已去掉）。

**GPT 预训练任务**

- 自回归语言建模：给定前文，预测下一个 token。

**面试要点**
- BERT 不适合生成，因为它不是自回归。
- GPT 不适合需要双向上下文理解的 NLU 任务（早期）。

---

9. 为什么大模型需要 Warmup、Learning Rate Decay？

**参考答案：**

**Warmup**

- 训练初期模型参数随机，梯度大且不稳定。
- Warmup 让学习率从小逐渐增大，避免早期参数更新过大破坏预训练权重。

**Learning Rate Decay**

- 训练后期接近最优解，需要用小学习率精细调整。
- 常用 cosine decay、linear decay、polynomial decay。

**代码示例**

```python
from transformers import get_cosine_schedule_with_warmup

scheduler = get_cosine_schedule_with_warmup(
    optimizer,
    num_warmup_steps=1000,
    num_training_steps=10000
)
```

**面试要点**
- Warmup 步数通常为总步数的 1%-10%。
- 微调时 Warmup 更关键，因为基座模型已收敛。

---

10. LLM 为什么会出现"复读机"问题？如何解决？

**参考答案：**

**原因**

1. 训练数据中重复内容导致模型学到重复模式。
2. 解码时 greedy decoding 容易陷入局部循环。
3. 模型对前文过度自信，不断输出相似 token。

**解决方案**

1. **重复惩罚（Repetition Penalty）**：对已经出现过的 token 降低概率。
2. **调整解码参数**：提高 temperature、使用 Top-p / Top-k 采样。
3. **数据去重**：训练前对语料进行去重。
4. **修改 loss / 训练目标**：加入重复惩罚项。
5. **后处理**：检测并截断重复片段。

**代码示例**

```python
from transformers import AutoModelForCausalLM, AutoTokenizer

model.generate(
    input_ids,
    repetition_penalty=1.2,  # 重复惩罚
    temperature=0.7,
    top_p=0.9,
    do_sample=True
)
```

**面试要点**
- 复读机问题在 greedy search 中最明显。
- 实际生产中会结合多种策略。

---

### 11. 大模型训练与微调

11. 预训练（Pre-training）与微调（SFT）的区别？

**参考答案：**

| 维度 | Pre-training | SFT |
|------|--------------|-----|
| 目的 | 学习通用语言知识和世界知识 | 学习特定任务或对话格式 |
| 数据 | 海量无标注文本 | 高质量指令/对话数据 |
| 目标 | 下一个 token 预测 | 监督学习，最小化输出损失 |
| 计算量 | 极大 | 相对较小 |
| 产出 | Base 模型 | Instruct/Chat 模型 |

**面试要点**
- SFT 不会给模型注入新知识，主要是激活已有知识并学会遵循指令格式。
- 预训练数据质量影响模型上限，SFT 数据质量影响模型可用性。

---

12. LoRA / QLoRA / Adapter / Prefix Tuning 原理与对比？

**参考答案：**

**LoRA（Low-Rank Adaptation）**

- 冻结原始权重 \(W_0\)，引入低秩矩阵 \(A, B\) 进行微调：

\[W = W_0 + BA, \quad A \in \mathbb{R}^{r \times d}, B \in \mathbb{R}^{d \times r}\]

- 只训练 A、B，参数量大幅减少。

**QLoRA**

- LoRA + 4-bit 量化（NF4）+ 双量化，进一步降低显存占用。
- 可以在消费级 GPU 上微调 7B/13B 模型。

**Adapter**

- 在 Transformer 层之间插入小型全连接层（bottleneck），只训练 Adapter。

**Prefix Tuning**

- 在输入前添加可训练的前缀向量，冻结主模型参数。

**对比**

| 方法 | 训练参数 | 推理开销 | 效果 |
|------|---------|---------|------|
| LoRA | 较少 | 可合并，无额外开销 | 好 |
| QLoRA | 极少（训练时） | 同 LoRA | 好 |
| Adapter | 较少 | 有额外前向开销 | 较好 |
| Prefix | 较少 | 增加序列长度 | 一般 |

**面试要点**
- LoRA 的 rank 通常取 8、16、64，越大表达能力越强但参数越多。
- 多个 LoRA 可以动态切换，适合多任务场景。

---

13. 全参数微调与参数高效微调（PEFT）的适用场景？

**参考答案：**

**全参数微调**

- 调整模型所有参数。
- 适用：数据量大、任务与预训练差异大、有充足算力。

**PEFT**

- 只调整少量参数或新增少量参数。
- 适用：数据量小、算力有限、需要快速适配多任务、防止灾难性遗忘。

**面试要点**
- 大部分业务场景用 LoRA/QLoRA 即可。
- 全参数微调容易过拟合小数据集。

---

14. 指令微调数据构建要点？数据质量 vs 数据量？

**参考答案：**

**构建要点**

1. **多样性**：覆盖不同任务类型、领域、语言、难度。
2. **质量优先**：标注准确、格式统一、无有害内容。
3. **长度分布**：避免全是短答案或长答案。
4. **系统提示词一致**：明确模型角色和行为边界。
5. **加入拒绝样本**：教模型拒绝不当请求。

**质量 vs 数据量**

- 研究表明：几千条高质量指令数据往往比几十万条低质量数据效果更好。
- 数据清洗、去重、过滤比单纯堆量更重要。

**面试要点**
- 数据质量 > 数据量是 2024-2025 年的重要共识。
- Self-Instruct、Alpaca、ShareGPT 是早期高质量数据集代表。

---

15. RLHF 全流程？PPO 与 DPO 的区别？

**参考答案：**

**RLHF 三阶段**

1. **SFT**：用高质量指令数据监督微调。
2. **Reward Model（RM）训练**：收集人类偏好数据，训练奖励模型给回答打分。
3. **RL 优化（PPO）**：用 PPO 算法最大化奖励，同时加 KL 约束防止模型偏离太远。

**PPO vs DPO**

| 维度 | PPO | DPO |
|------|-----|-----|
| 是否需要 RM | 需要单独训练 | 不需要，直接优化策略 |
| 训练稳定性 | 较复杂，容易不稳定 | 更简单稳定 |
| 计算量 | 大 | 小 |
| 原理 | 在线强化学习 | 将奖励函数隐式嵌入 Bradley-Terry 损失 |

**DPO 损失**

\[\mathcal{L}_{DPO} = -\log \sigma\left(\beta \log \frac{\pi_\theta(y_w|x)}{\pi_{ref}(y_w|x)} - \beta \log \frac{\pi_\theta(y_l|x)}{\pi_{ref}(y_l|x)}\right)\]

**面试要点**
- DPO 通过对比偏好数据直接优化，省去了 RM 和在线采样。
- PPO 上限可能更高，但 DPO 更实用。

---

16. 大模型训练中的显存优化方法？

**参考答案：**

**优化方法**

1. **ZeRO（DeepSpeed）**：把优化器状态、梯度、参数分片到不同 GPU。
2. **Gradient Checkpointing**：反向传播时重计算前向激活值，用计算换显存。
3. **Mixed Precision Training**：FP16/BF16 减少显存占用，加速计算。
4. **Offload**：把优化器状态或参数卸载到 CPU/NVMe。
5. **3D 并行**：数据并行 + 张量并行 + 流水线并行。
6. **QLoRA / 4-bit 量化训练**。

**面试要点**
- ZeRO-1/2/3 分别分片优化器状态、梯度、参数。
- Gradient Checkpointing 会增加 20%-30% 训练时间。

---

17. 数据并行、模型并行、流水线并行、张量并行的区别？

**参考答案：**

| 并行方式 | 切分对象 | 特点 |
|----------|---------|------|
| 数据并行（DP） | 数据批次 | 每个 GPU 存完整模型，适合模型小、数据大 |
| 模型并行（MP） | 模型参数 | 参数分散到多个 GPU |
| 张量并行（TP） | 层内张量 | 如把 Attention/FFN 按列/行切分 |
| 流水线并行（PP） | 模型层 | 不同 GPU 负责不同层，像流水线 |

**组合**

- 大规模训练常用 3D 并行：DP + TP + PP。
- Megatron-LM 擅长 TP，DeepSpeed 擅长 ZeRO/PP。

**面试要点**
- TP 通信量大，适合单节点内部 GPU 之间。
- PP 有气泡（pipeline bubble）问题，可用 interleaved schedule 缓解。

---

18. DeepSpeed / Megatron-LM / FSDP 的核心能力？

**参考答案：**

| 框架 | 核心能力 | 适用场景 |
|------|---------|----------|
| DeepSpeed | ZeRO、Offload、3D 并行、Inference、RLHF | 训练超大模型 |
| Megatron-LM | 张量并行、流水线并行、Transformer 优化 | NVIDIA GPU 大规模训练 |
| FSDP（PyTorch） | 全分片数据并行，易用 | 替代传统 DDP |

**面试要点**
- DeepSpeed 的 ZeRO-3 和 FSDP 类似，都是参数分片。
- Megatron 对 Transformer 结构做了专门优化。

---

19. 什么是 MoE（Mixture of Experts）？Switch Transformer 的优势？

**参考答案：**

**MoE**

- 把模型中的 FFN 层替换为多个专家网络（Experts）。
- 每个 token 只激活部分专家，通过门控网络（Gating Network）路由。

**Switch Transformer**

- 每个 token 只路由到 1 个专家（Top-1）。
- 优势：计算量不随专家数量线性增加，可以用更大总参数量但保持相同激活计算量。

**面试要点**
- MoE 可以在不增加推理成本的情况下扩大模型容量。
- 挑战：负载均衡、通信开销、专家崩溃（部分专家过载）。

---

20. 大模型训练 loss 不收敛 / 震荡怎么排查？

**参考答案：**

**排查思路**

1. **数据问题**：检查数据格式、tokenization 是否正确、是否有脏数据。
2. **学习率**：学习率过大导致震荡，过小收敛慢。
3. **Batch Size**：太小噪声大，太大泛化差。
4. **梯度**：梯度爆炸/消失，检查梯度裁剪、归一化。
5. **Loss 曲线**：看是整体不收敛还是某阶段突然恶化。
6. **模型实现**：检查 attention mask、position encoding、label shift 是否正确。

**解决手段**

- 降低学习率、增加 Warmup、使用 BF16、检查数据流水线、加梯度裁剪。

**面试要点**
- 先确认数据没问题，再调超参。
- 可视化 per-sample loss 有助于定位脏数据。

---

### 21. 推理优化

21. KV Cache 原理？为什么能加速自回归生成？

**参考答案：**

**原理**

自回归生成中，每个新 token 都要与前面所有 token 计算 Attention。但前面 token 的 Key 和 Value 在生成过程中是不变的，因此可以缓存起来，避免重复计算。

**加速原因**

- 无需重新计算历史 token 的 K、V。
- 每次只需要计算当前 token 的 Q，与缓存的 K、V 做 Attention。
- 时间复杂度从 \(O(n^2)\) 降到 \(O(n)\)（每次生成）。

**面试要点**
- KV Cache 显存占用为 \(2 \times n \times h \times d\)（K 和 V）。
- 长文本推理时 KV Cache 是显存瓶颈，因此有 KV Cache 压缩、量化、分页等技术。

---

22. 解码策略：Greedy、Beam Search、Top-k、Top-p、Temperature 区别？

**参考答案：**

| 策略 | 说明 | 特点 |
|------|------|------|
| Greedy | 每次选概率最高的 token | 确定性高，容易复读 |
| Beam Search | 维护 top-k 个候选序列 | 适合短序列、翻译 |
| Top-k | 只从概率最高的 k 个 token 中采样 | 控制多样性 |
| Top-p（Nucleus） | 从累积概率达到 p 的最小集合中采样 | 更灵活 |
| Temperature | 对 logits 除以 T | T<1 更确定，T>1 更随机 |

**代码示例**

```python
model.generate(
    input_ids,
    do_sample=True,
    temperature=0.7,
    top_k=50,
    top_p=0.9
)
```

**面试要点**
- 开放式生成多用采样（Top-p + Temperature）。
- 需要确定性答案时可用 Greedy 或 Beam Search。

---

23. 大模型量化方案？

**参考答案：**

| 量化方案 | 精度 | 特点 |
|----------|------|------|
| INT8 | 8-bit | 精度损失小，推理加速明显 |
| INT4/INT3 | 4/3-bit | 显存占用极低，适合端侧 |
| GPTQ | 4-bit | 逐层量化，Post-Training Quantization |
| AWQ | 4-bit | 保护激活值重要的权重通道 |
| GGUF | 多种位宽 | llama.cpp 使用，CPU 推理 |
| SmoothQuant | 8-bit | 将激活值难度迁移到权重，适合 LLM |

**面试要点**
- 量化分为 PTQ（训练后量化）和 QAT（训练时量化）。
- AWQ 通常比 GPTQ 精度更高。
- QLoRA 训练时也使用量化的基座模型。

---

24. vLLM 的 PagedAttention 原理？

**参考答案：**

**核心思想**

借鉴操作系统虚拟内存和分页机制，将 KV Cache 划分为固定大小的 block（page），按需分配，而非预分配连续显存。

**优势**

1. 减少显存碎片，提高显存利用率。
2. 支持更大的 batch size 和更长的上下文。
3. 方便实现 KV Cache 共享（如 beam search）。

**面试要点**
- PagedAttention 是 vLLM 高吞吐的核心。
- block 大小通常为 16/32/64 token。

---

25. 推理引擎对比：vLLM、TGI、TensorRT-LLM、FasterTransformer？

**参考答案：**

| 引擎 | 特点 | 适用场景 |
|------|------|----------|
| vLLM | PagedAttention、高吞吐、开源生态好 | 通用大模型服务 |
| TGI（HuggingFace） | 易用、支持流式、量化 | HuggingFace 生态 |
| TensorRT-LLM | NVIDIA 优化极致、需要转换 | NVIDIA GPU 生产环境 |
| FasterTransformer | 老牌 NVIDIA 优化库 | 已被 TensorRT-LLM 替代趋势 |

**面试要点**
- 选型考虑：硬件、模型结构、性能要求、生态。
- vLLM 适合快速部署，TensorRT-LLM 适合极致性能。

---

26. 如何降低大模型推理延迟与提高吞吐？

**参考答案：**

**降低延迟**

1. 使用更高效的 Attention 算法（FlashAttention、PagedAttention）。
2. 量化（INT8/INT4）。
3. 减少解码步数：投机采样（Speculative Decoding）。
4. 模型蒸馏、剪枝。
5. 使用高性能推理引擎（TensorRT-LLM）。

**提高吞吐**

1. 动态 batching / continuous batching。
2. 增大 batch size。
3. KV Cache 优化。
4. 多卡并行部署。

**面试要点**
- 延迟和吞吐往往此消彼长，需根据场景权衡。
- Continuous Batching 可以显著提升 GPU 利用率。

---

27. 长文本推理的优化方法？

**参考答案：**

1. **位置编码外推**：RoPE 外推、NTK-aware、YaRN、Dynamic NTK。
2. **KV Cache 压缩**：H2O、StreamingLLM、SnapKV，保留重要 token。
3. **稀疏 Attention**：Longformer、BigBird、Ring Attention。
4. **上下文压缩**：LLMLingua 压缩 prompt。
5. **RAG**：不用把全部文本塞进上下文。

**面试要点**
- 长文本难点在于 \(O(n^2)\) Attention 和 KV Cache 显存。
- 实际中常结合 RAG 和外推技术。

---

28. 流式输出（Streaming）实现原理？

**参考答案：**

**原理**

大模型自回归生成是一个 token 一个 token 输出，流式就是每生成一个 token 就立即返回给客户端，而不是等全部生成完。

**实现**

- Server-Sent Events（SSE）或 WebSocket。
- 服务端维护生成状态，逐步返回 delta token。
- 客户端逐步拼接显示。

**代码示例**

```python
# OpenAI API 风格流式
for chunk in client.chat.completions.create(
    model="gpt-4",
    messages=messages,
    stream=True
):
    print(chunk.choices[0].delta.content, end="")
```

**面试要点**
- 流式可以改善用户体验，降低首 token 延迟感知。
- 需要处理特殊 token（如 `<|endoftext|>`）的终止判断。

---

### 29. RAG（检索增强生成）

29. RAG 的基本架构？Retriever + Generator 如何协同？

**参考答案：**

**基本架构**

```
用户 Query → 检索模块（Retriever）→ 召回相关文档 → 拼接 Prompt → 生成模型（Generator）→ 答案
```

**Retriever**

- 把文档切分并编码为向量，存入向量数据库。
- 把用户 query 编码为向量，做相似度检索。

**Generator**

- 大模型根据 query + 召回文档生成回答。

**协同**

- Retriever 负责"找证据"，Generator 负责"组织语言"。
- 两者质量互相影响，召回差则生成差。

**面试要点**
- RAG 适合知识更新频繁、需要可解释性和可追溯性的场景。
- 向量检索不是唯一方式，还可以结合关键词、图谱、结构化查询。

---

30. 向量检索原理？Embedding 模型选型？

**参考答案：**

**向量检索原理**

1. 用 Embedding 模型将文本编码为稠密向量。
2. 计算 query 向量与文档向量的相似度（余弦相似度、点积）。
3. 返回 Top-K 最相似的文档。

**Embedding 模型选型**

| 模型 | 特点 |
|------|------|
| text-embedding-ada-002 / text-embedding-3 | OpenAI，通用效果好 |
| BGE（BAAI） | 中文效果好，开源 |
| M3E | 中文开源 |
| GTE、E5 | 英文开源，效果强 |
| Jina、Nomic | 轻量、多语言 |

**面试要点**
- 选型要考虑语言、领域、向量维度、推理成本。
- 特定领域建议微调 Embedding 模型。

---

31. 文档 Chunk 切分策略？

**参考答案：**

**常见策略**

1. **固定长度切分**：按 token 数或字符数切分，简单但可能切断语义。
2. **重叠切分（Overlap）**：相邻 chunk 有重叠，减少边界信息丢失。
3. **按段落/句子切分**：保持语义完整。
4. **递归切分**：先按大粒度切，再按小粒度切。
5. **语义切分**：用模型判断语义边界。

**面试要点**
- chunk 大小影响召回精度和上下文利用率。
- 通常 256-1024 token，需要实验调优。
- 代码、表格、列表等结构化内容需要特殊处理。

---

32. 混合检索与 Rerank 的作用？

**参考答案：**

**混合检索**

- 结合稠密向量检索（语义）和稀疏检索（关键词，如 BM25）。
- 优势互补：向量检索擅长同义改写，BM25 擅长精确匹配。

**Rerank**

- 用更重的 cross-encoder 模型对召回结果重新排序。
- 计算 query 与每个文档的交互分数，精度更高但速度更慢。

**典型流程**

```
Query → 向量检索 Top-100 + BM25 Top-100 → 合并去重 → Rerank Top-10 → 生成模型
```

**面试要点**
- 检索召回率优先，Rerank 重排序保证精度。
- Rerank 模型如 BGE-Reranker、Cohere Rerank。

---

33. RAG 中幻觉（Hallucination）的来源与缓解方法？

**参考答案：**

**幻觉来源**

1. 召回文档不相关或信息不足。
2. 模型过度依赖自身知识，忽视检索内容。
3. 多个文档信息矛盾。
4. 模型编造引用。

**缓解方法**

1. 提高召回质量（混合检索、Rerank）。
2. 在 prompt 中明确约束"只基于给定资料回答"。
3. 引用溯源（citation），让模型标注答案来源。
4. 拒绝回答机制：资料不足时明确告知。
5. 后校验：用另一个模型检查答案与文档一致性。

**面试要点**
- RAG 不是完全消除幻觉，而是降低幻觉并提供可追溯性。

---

34. GraphRAG 与 LightRAG 的思路？与传统 RAG 的区别？

**参考答案：**

**GraphRAG**

- 先构建知识图谱，把文档中的实体、关系抽取出来。
- 检索时不仅检索文本片段，还利用图谱关系进行多跳推理。

**LightRAG**

- 微软提出的轻量级 GraphRAG，用双层检索（低级检索 + 高级检索）降低成本。
- 仍然构建图，但检索和生成更轻量。

**与传统 RAG 区别**

| 维度 | 传统 RAG | GraphRAG |
|------|---------|----------|
| 粒度 | 文本 chunk | 实体、关系、社区 |
| 推理 | 弱 | 支持多跳推理 |
| 成本 | 低 | 高（建图、检索） |
| 适用 | 简单问答 | 复杂关系型问答 |

**面试要点**
- GraphRAG 适合需要全局理解、关系推理的场景。
- 成本高，不是传统 RAG 的替代，而是补充。

---

35. RAG 落地最难的地方在哪里？

**参考答案：**

1. **文档预处理**：格式混乱、表格、图片、代码解析困难。
2. **召回质量**：query 与文档表述不一致、多语言、专业术语。
3. **生成忠实度**：模型不看资料或乱答。
4. **评估困难**：没有标准答案，难以量化效果。
5. **成本控制**：Embedding、向量库、大模型调用都有成本。
6. **数据更新**：新知识如何及时进入向量库。

**面试要点**
- RAG 的工程难点大于算法难点。
- 需要从数据、检索、生成、评估全链路优化。

---

36. 如何评估 RAG 效果？

**参考答案：**

**检索评估**

- Recall@K、Precision@K、MRR、NDCG。

**生成评估**

- **Faithfulness**：答案是否忠于检索文档。
- **Answer Relevance**：答案与问题的相关性。
- **Context Relevance**：检索文档与问题的相关性。
- **RAGAS**：综合评估框架。

**人工评估**

- 答案正确性、完整性、可读性、引用准确性。

**面试要点**
- 自动评估 + 人工评估结合。
- 评估数据集构建是落地的关键。

---

37. RAG vs Fine-tuning：什么时候选哪个？

**参考答案：**

| 场景 | 推荐 |
|------|------|
| 知识频繁更新 | RAG |
| 需要可解释性/溯源 | RAG |
| 数据量小 | RAG |
| 需要改变模型风格、格式、专业能力 | SFT |
| 有大量高质量标注数据 | SFT |
| 需要模型掌握特定推理模式 | SFT |

**面试要点**
- RAG 解决"知道什么"，SFT 解决"怎么回答"。
- 实际中常结合使用：RAG 提供上下文 + SFT 优化回答风格。

---

### 38. Agent 智能体

38. 什么是 Agent？ReAct 循环的核心思想？

**参考答案：**

**Agent**

Agent 是能够感知环境、做出决策并执行动作的智能体。在大模型场景下，Agent = LLM + 工具 + 记忆 + 规划 + 执行。

**ReAct 循环**

Reasoning + Acting，让模型交替进行推理和行动：

```
Thought（思考）→ Action（行动）→ Observation（观察）→ Thought → ...
```

通过显式推理步骤，模型决定调用什么工具、如何根据工具结果继续。

**面试要点**
- Agent 不只是 prompt + API 调用，而是有状态、有循环、有工具调用的系统。
- ReAct 提高了可解释性和任务完成率。

---

39. Function Calling 原理？大模型如何调用外部工具？

**参考答案：**

**原理**

Function Calling 不是模型真的执行函数，而是模型根据输入理解意图，输出一个结构化的函数调用请求（函数名 + 参数）。外部系统执行后，将结果返回给模型，模型再生成最终回答。

**流程**

```
User Query → LLM 判断需要工具 → 输出 JSON 工具调用 → 系统执行 → 返回结果 → LLM 生成自然语言回答
```

**代码示例**

```json
{
  "name": "get_weather",
  "arguments": {
    "city": "Beijing"
  }
}
```

**面试要点**
- Function Calling 的准确性取决于模型训练和数据格式。
- 需要校验模型输出的参数合法性。

---

40. MCP（Model Context Protocol）是什么？解决了什么问题？

**参考答案：**

**MCP**

Anthropic 提出的开放协议，定义了模型如何与外部数据源、工具、服务交互的标准接口。

**解决问题**

1. 每个 Agent/工具都要单独适配不同模型和平台。
2. 上下文、工具调用、状态管理没有统一标准。
3. 跨系统复用困难。

**MCP 价值**

- 类似"AI 世界的 USB-C"，统一接口。
- 让模型、工具、数据源可以互联互通。

**面试要点**
- MCP 是 2024-2025 年 Agent 生态的重要协议。
- 了解 Server、Client、Tool、Resource 等核心概念。

---

41. Agent 的记忆系统设计？

**参考答案：**

**记忆类型**

1. **短期记忆（Short-term）**：当前对话上下文，受窗口限制。
2. **长期记忆（Long-term）**：历史对话摘要、用户画像、偏好。
3. **Episodic Memory**：具体事件、经验片段。
4. **Semantic Memory**：结构化知识、事实。

**实现方式**

- 向量数据库存储长期记忆。
- 检索时根据当前 query 召回相关记忆。
- 定期对历史对话做摘要，压缩上下文。

**面试要点**
- 记忆设计直接影响 Agent 的个性化和连贯性。
- 需要平衡记忆丰富度和上下文长度限制。

---

42. Agent 的幻觉与任务漂移如何识别与应对？

**参考答案：**

**识别**

- 输出与工具返回结果不一致。
- 行动偏离初始目标。
- 重复无效 action。
- 自洽性检查失败。

**应对**

1. **严格 prompt 约束**：要求模型基于证据回答。
2. **工具校验**：每个 action 的结果都验证。
3. **状态监控**：记录 trajectory，检测循环或偏离。
4. **Human-in-the-loop**：关键步骤人工确认。
5. **反思（Reflection）**：让 Agent 自我检查并修正。

**面试要点**
- Agent 的幻觉比纯文本生成更难发现，需要系统级监控。

---

43. 多 Agent 编排模式？

**参考答案：**

**常见模式**

1. **主从模式（Manager-Worker）**：一个主 Agent 分配任务，多个子 Agent 执行。
2. **协作模式（Collaborative）**：多个 Agent 平等协作，共同完成任务。
3. **竞争模式（Competitive）**：多个 Agent 提出方案，由评委 Agent 选择最优。
4. **流水线模式（Pipeline）**：每个 Agent 负责一个阶段，顺序执行。

**框架**

- LangGraph、AutoGen、CrewAI、MetaGPT。

**面试要点**
- 多 Agent 的核心挑战是通信、协调、成本控制。
- 不是所有任务都需要多 Agent，简单任务单 Agent 更高效。

---

44. Agent 的成本控制与 Token 预算管理？

**参考答案：**

**成本控制方法**

1. **模型路由**：简单任务用便宜模型，复杂任务用强模型。
2. **上下文压缩**：摘要、过滤无关历史。
3. **限制循环次数**：防止无限 Loop。
4. **缓存结果**：相同 query 直接返回缓存。
5. **工具调用优化**：减少不必要的工具调用。

**Token 预算**

- 为每个 Agent / 每个任务设置 max_tokens。
- 监控 input/output token 比例。
- 长上下文模型虽然能力强，但成本高。

**面试要点**
- 成本是 Agent 落地的关键约束之一。
- 需要在效果、延迟、成本之间做权衡。

---

45. Agent 的可观测性如何设计？

**参考答案：**

**观测维度**

1. **Trace**：完整调用链，记录每个 Thought/Action/Observation。
2. **工具调用**：调用次数、成功率、延迟。
3. **上下文**：token 使用量、上下文长度。
4. **成本**：每次任务的总 token 花费。
5. **评估指标**：任务完成率、答案准确率、用户满意度。

**工具**

- LangSmith、Langfuse、Phoenix、Weights & Biases。

**面试要点**
- 可观测性是 Agent 从 demo 到生产的关键。
- 需要闭环：监控 → 发现问题 → 优化 prompt/工具/流程。

---

46. Agent 与 RAG 的关系？什么时候用 Agent，什么时候用 RAG？

**参考答案：**

**关系**

- RAG 是 Agent 的一种工具/能力。
- Agent 可以使用 RAG 检索信息，然后做推理、规划、执行。

**选择**

| 场景 | 方案 |
|------|------|
| 单次问答、知识查询 | RAG |
| 多步骤任务、需要调用工具 | Agent |
| 需要推理、决策、执行闭环 | Agent |
| 只需要基于文档回答 | RAG |

**面试要点**
- Agent 适合复杂动态任务，RAG 适合知识密集型静态问答。
- 两者可以结合：Agent 负责流程，RAG 负责知识检索。

---

### 47. RLHF / 对齐 / 安全

47. RLHF 三阶段：SFT、Reward Model、PPO？

**参考答案：**

已在前文提过，这里补充细节：

**Reward Model**

- 输入：prompt + response。
- 输出：标量奖励分数。
- 训练数据：人类标注的偏好对（A > B）。

**PPO 训练**

- 目标：最大化奖励，同时用 KL 散度约束策略不要偏离 SFT 模型太远。

\[\mathcal{L}_{PPO} = \mathbb{E}[r_\theta(x,y) - \beta \log \frac{\pi_\theta(y|x)}{\pi_{ref}(y|x)}]\]

**面试要点**
- RLHF 成本高、训练不稳定，但能让模型输出更符合人类偏好。

---

48. DPO 为什么可以替代 PPO？优缺点？

**参考答案：**

**DPO 优势**

1. 不需要训练 Reward Model。
2. 不需要在线采样，训练更稳定。
3. 实现简单，计算成本低。

**DPO 缺点**

1. 依赖偏好数据质量。
2. 对分布外数据表现可能不如 PPO。
3. 容易出现 over-optimization 到偏好数据的问题。

**面试要点**
- DPO 是离线算法，PPO 是在线算法。
- 工业界越来越多使用 DPO 或 DPO + PPO 组合。

---

49. Reward Hacking 是什么？如何避免？

**参考答案：**

**Reward Hacking**

模型找到 exploit Reward Model 的捷径，生成高奖励但质量差的内容。例如：
- 用冗长、重复、奉承的语言欺骗 RM。
- 生成符合 RM 偏好但不符合人类真实偏好的回答。

**避免方法**

1. 提高 Reward Model 质量，覆盖更多场景。
2. 加入长度惩罚、格式约束。
3. 多轮迭代训练 RM。
4. 用 RLHF + 人工抽检。
5. 使用 DPO 等更稳定的对齐方法。

**面试要点**
- Reward Hacking 是 RLHF 的核心风险之一。
- 对齐目标必须真正反映人类意图。

---

50. 大模型对齐中的安全性问题？

**参考答案：**

**安全问题**

1. **Prompt Injection**：用户输入中嵌入恶意指令。
2. **Jailbreak**：诱导模型绕过安全限制。
3. **数据泄露**：模型输出训练数据中的隐私信息。
4. **有害内容生成**：暴力、歧视、违法信息。

**防御手段**

1. 输入过滤和输出审核。
2. 安全对齐训练（RLHF、Constitutional AI、Red Teaming）。
3. 系统提示词加固。
4. 敏感词检测、模型分类器拦截。
5. 人工审核和 A/B 测试。

**面试要点**
- 安全和对齐是模型上线前必须通过的关卡。
- 没有 100% 安全，需要多层防御。

---

51. 模型评估指标？

**参考答案：**

| 指标 | 用途 |
|------|------|
| Perplexity | 语言模型整体概率建模能力 |
| BLEU | 机器翻译、文本生成，n-gram 匹配 |
| ROUGE | 摘要生成，召回率导向 |
| HumanEval / MBPP | 代码生成能力 |
| MT-Bench / AlpacaEval | 指令遵循、对话能力 |
| MMLU | 多任务知识理解 |
| GSM8K | 数学推理 |

**面试要点**
- 自动指标有局限，最终需要人工评估。
- 不同任务选不同指标，不要单一指标定论。

---

### 52. AI 编程与工程实践

52. 什么是 Vibe Coding？工程落地的核心能力是什么？

**参考答案：**

**Vibe Coding**

指开发者用自然语言描述意图，由 AI 工具（Cursor、Claude Code、Windsurf 等）自动生成和修改代码的开发方式。

**核心能力**

1. **需求表达**：把模糊意图转化为清晰的规格说明。
2. **上下文构建**：给 AI 提供足够的代码、文档、约束。
3. **代码审查**：能判断 AI 生成代码的正确性。
4. **工程纪律**：版本控制、测试、回滚、模块化。
5. **成本控制**：管理 Token 消耗。

**面试要点**
- Vibe Coding 不是替代工程师，而是改变工程师的工作方式。
- 越懂工程的人，越能把 AI 用出价值。

---

53. Cursor / Claude Code / Copilot 等大模型编程工具的差异？

**参考答案：**

| 工具 | 特点 |
|------|------|
| GitHub Copilot | 代码补全强，IDE 集成深 |
| Cursor | 基于 VS Code，Composer、Agent 模式、代码编辑能力强 |
| Claude Code | Anthropic 出品，Agent 能力强，适合复杂任务和代码库理解 |
| Windsurf | Codeium 出品，强调 flow 状态 |

**面试要点**
- 选型看团队 IDE 生态、任务类型、成本。
- 工具只是手段，关键是输出质量可控。

---

54. AI 编程中如何保证代码安全？

**参考答案：**

1. **防止源码泄露**：不上传核心代码到公共 AI 服务，使用私有化部署。
2. **Agent Loop 安全**：限制 AI 的执行权限，禁止自动执行危险操作（如 rm、drop database）。
3. **系统提示词保护**：防止 prompt injection 篡改 AI 行为。
4. **代码审查**：AI 生成的代码必须经过人工 review。
5. **沙箱执行**：测试 AI 生成的代码时隔离环境。
6. **依赖安全**：检查 AI 引入的第三方依赖。

**面试要点**
- AI 编程的最大风险是"看起来对，实际错"。
- 安全策略要覆盖输入、处理、输出、执行全链路。

---

55. Spec-Driven Development（规约驱动开发）是什么？

**参考答案：**

**定义**

从模糊的 Prompt 转向明确的规格（Spec）、计划、任务和验证。把需求以结构化方式定义清楚，再让 AI 基于 Spec 生成代码。

**价值**

1. 降低 AI 理解偏差。
2. 便于人工审查和验收。
3. 支持自动化测试和验证。
4. 让 AI 生成结果更可预测。

**面试要点**
- Spec-Driven 是对 Vibe Coding 随意性的工程化修正。
- 是未来 AI 辅助开发的重要方向。

---

56. AI 辅助开发的 Token 成本控制方法？

**参考答案：**

1. **精简上下文**：只传必要文件，避免把整个代码库塞给 AI。
2. **使用缓存**：OpenAI prompt caching、Anthropic prompt caching。
3. **模型分层**：简单任务用便宜模型。
4. **本地模型**：代码补全用本地小模型。
5. **复用 Embedding**：用 RAG 检索相关代码，而不是全文传递。
6. **监控与分析**：统计 token 消耗，优化高频场景。

**面试要点**
- Token 成本在高频开发中会迅速累积。
- 成本控制要从架构设计阶段考虑。

---

57. AI 编程场景下如何做好代码审查与工程纪律？

**参考答案：**

1. **强制 Code Review**：AI 生成代码必须人工 review。
2. **自动化测试**：单元测试、集成测试、回归测试。
3. **小步迭代**：不要一次性让 AI 改太多文件。
4. **版本控制**：每次 AI 修改单独 commit，便于回滚。
5. **明确边界**：核心算法、安全相关代码人工主导。
6. **文档同步**：AI 修改代码后同步更新注释和文档。

**面试要点**
- AI 不能替代工程流程，而是嵌入工程流程。
- 审查重点放在逻辑正确性、边界条件、安全性。

---

58. 如何将 AI 能力集成到现有 Java / Spring Boot 系统？

**参考答案：**

**方案**

1. **Spring AI**：Spring 官方提供的 AI 集成框架，支持 OpenAI、Ollama、Huggingface 等。
2. **直接调用 REST API**：用 WebClient/RestTemplate 调用大模型 API。
3. **本地部署**：用 Ollama、vLLM 部署开源模型，通过 HTTP 调用。
4. **向量库集成**：Spring AI 支持 Redis、PgVector、Milvus、Chroma 等。

**代码示例**

```java
// Spring AI ChatClient
ChatClient chatClient = ChatClient.builder(openAiChatModel).build();

String response = chatClient.prompt()
    .user("帮我解释 HashMap 原理")
    .call()
    .content();
```

**面试要点**
- 集成时要考虑异步、超时、重试、限流、降级。
- 敏感数据需要脱敏或私有化部署。

---

59. 大模型 API 调用中的异常处理、重试、限流、降级策略？

**参考答案：**

**异常处理**

- 分类处理：网络超时、服务端错误（5xx）、限流（429）、内容过滤。
- 记录详细日志和原始请求。

**重试**

- 指数退避（Exponential Backoff）。
- 对 429、5xx 重试，对 4xx 不重试。
- 设置最大重试次数和总超时。

**限流**

- 客户端令牌桶限流，避免触发服务端限流。
- 多模型配额管理。

**降级**

- 主模型失败时切换到备用模型。
- 返回缓存结果或简化回答。
- 关键路径要有兜底文案。

**代码示例**

```java
// Resilience4j 重试 + 降级
Retry retry = Retry.ofDefaults("llm-retry");
Supplier<String> decorated = Retry.decorateSupplier(retry, this::callLLM);
String result = Try.ofSupplier(decorated)
    .recover(throwable -> "服务暂时不可用，请稍后重试")
    .get();
```

**面试要点**
- 大模型 API 不稳定是常态，必须做容错设计。
- 监控响应时间、成功率、token 消耗。

---

60. 构建一个企业级 AI 应用需要哪些基础设施？

**参考答案：**

**基础设施层**

1. **模型服务**：模型部署、推理优化、多模型路由。
2. **向量数据库**：Milvus、PgVector、Redis、Chroma。
3. **Agent 框架**：LangChain、LangGraph、Spring AI、AutoGen。
4. **数据管道**：文档解析、Embedding、数据清洗、索引更新。
5. **评估体系**：自动评估、人工评估、A/B 测试。
6. **可观测性**：Trace、日志、监控、成本分析。
7. **安全合规**：内容审核、权限控制、数据隐私。

**面试要点**
- 企业级 AI 应用不是只调 API，而是完整工程体系。
- 需要根据业务场景选择技术栈，避免过度工程。

---

## 三、面试建议

### Java 后端岗位
- 重点：Java 基础 + 集合 + 并发 + JVM + Spring + MySQL + Redis
- 加分项：分布式事务、微服务、消息队列、Netty、ES、云原生

### AI / 大模型应用开发岗位
- 重点：Transformer 原理 + RAG + Agent + 推理优化 + 微调基础
- 加分项：有大模型项目落地经验、掌握 LangChain/LangGraph/Spring AI、懂成本控制与工程化

### 通用准备策略
1. 不要只背答案，要理解"面试官为什么这么问"。
2. 每个知识点尽量结合项目经历回答。
3. 准备 1-2 个完整项目，能讲清楚技术选型、遇到的问题、优化过程。
4. 大厂面试喜欢层层追问，准备好深挖细节。

---

## 四、推荐学习资源

- Java：JavaGuide、卡码笔记 Java 面经、即答侠 Java 面试题
- 大模型：llm_interview_note（GitHub）、卡码笔记大模型面经汇总
- 实践项目：tiny-llm-zh、tiny-rag、tiny-mcp、llama3-from-scratch-zh

---

*整理时间：2026-08-21*