# QST_1201_Exam_1
青软实训第一次考试，上机部分

大数据班-第一次考试-上机题

---

题目1： 根据链表的定义，编写函数printLinkList(LinkList head)，实现打印一个给定链表的功能。（25分）
```
	public static class LinkList{
		public int value; // 存放节点的值
		public LinkList next = null; // 存放下一个节点
		LinkList(int value){
			this.value = value;
		}
		LinkList(int value, LinkList next){
			this.value = value;
			this.next = next;
		}
		public void setValue(int value){
			this.value = value;
		}
	}
	// 实现以下函数，打印链表head
	public void printLinkList(LinkList head){
	}
```

打印格式如下`1->2->5->3`

---

题目2：编写MapReduce，统计/user/hadoop/mapred_dev/ip_time 中去重后的IP数，越节省性能越好。（35分）

---

题目3：编写MapReduce，统计`/user/hadoop/mapred_dev_double/ip_time`与`/user/hadoop/mapred_dev_double/ip_time_2`当中，重复重现的IP数量(40分)



