## Лаб 10: Өгөгдлийн урсгалын тест(DU хосууд)

Оюутны нэр: Ё.Болормаа
Оюутны код: B222270050

## Орчин

bolopoo@Ubuntu:~/Downloads/lab10-dataflow$ java --version
openjdk 17.0.20.1 2026-08-18
OpenJDK Runtime Environment (build 17.0.20.1+1-1-26.04-Ubuntu)
OpenJDK 64-Bit Server VM (build 17.0.20.1+1-1-26.04-Ubuntu, mixed mode, sharing)

bolopoo@Ubuntu:~/Downloads/lab10-dataflow$ mvn --version
Apache Maven 3.9.12
Maven home: /usr/share/maven
Java version: 17.0.20.1, vendor: Ubuntu, runtime: /usr/lib/jvm/java-17-openjdk-amd64
Default locale: en_US, platform encoding: UTF-8
OS name: "linux", version: "7.0.0-38-generic", arch: "amd64", family: "unix"


## Хэсэг А: gcd

### Def/use 

| Мөр | Код | Def | Use |
|---|---|---|---|
| 13 | 'gcd(int x, int y)' | x, y | | 
| 15 | 'while (y != 0)' | | y (p-use) |
| 16 | 'tmp = x % y;' | tmp | x (c), y (c) |
| 17 | 'x = y;' | x | y (c) |
| 18 | 'y = tmp;'  | y | tmp (c) |
| 20 | 'return x; ' | | x (c) |

### DU хосын хүснэгт

| Хувьсагч | Def | Use | Төрөл | Def-clear зам | Тестийн ID | Хамрагдсан |
|---|---|---|---|---|---|---|
| x | 13 | 16 | c-use | 13 -> 15(T) -> 16 | T2, T3 | тийм |
| x | 13 | 20 | c-use | 13 -> 15(F)-> 20 | T1 | тийм |
| x | 17 | 16 | c-use | 17 -> 18 -> 15(T) -> 16 | T2 | тийм |
| x | 17 | 20 | c-use | 17 -> 18 -> 15(F) -> 20 | T2, T3 | тийм |
| y | 13 | 15 | p-use | 13->15 (T ба F) | T1 (F), T2 (T) | тийм |
| y | 13 | 16 | c-use | 13->15(T)->16 | T2, T3 | тийм |
| y | 13 | 17 | c-use | 13->15(T)->16->17 | T2, T3 | тийм |
| y | 18 | 15 | p-use | 18->15 | T2, T3 | тийм |
| y | 18 | 16 | c-use | 18->15(T)->16 | T2 | тийм |
| y | 18 | 17 | c-use | 18->15(T)->16->17 | T2 | тийм |
| tmp | 16 | 18 | c-use | 16->17->18 | T2, T3 | тийм |

Нийт 11 хос. Мөр 17 (`x = y`) нь x-ийн def бөгөөд use биш, тиймээс x-ийн (13,17) гэсэн хос байхгүй. Мөр 17 нь 13-ын x-ийг ална (kill), тиймээс (13,20) зөвхөн давталтад ороогүй замаар л def-clear байна. Лекцийн хүснэгтэд дутуу байсан хосууд: x(17,16), y(13,17), y(18,16), y(18,17), tmp(16,18).


### Тестүүд (GcdTest.java)
 
| ID | Оролт | Хариу | Хамарсан хос |
|---|---|---|---|
| T1 | gcd(7,0) | 7 | x(13,20), y(13,15) F |
| T2 | gcd(12,8) | 4 | бусад бүх хос |
| T3 | gcd(0,5) | 5 | нэмэлт: 1 давталттай тохиолдол |


- 'gcd(7,0)' нь давталтад огт ордоггүй тул x(13,20) хосыг ГАНЦААРАА хамарна. Өөр ямар ч оролт үүнийг хамрахгүй, учир нь давталтад орвол мөр 17 нь x-ийг ална.
- 'gcd(12,8)' нь 12,8 -> 8,4 -> 4,0 гэж 2 давталт хийнэ. Давталт шаарддаг хосууд: x(13,16), x(17,16), x(17,20), y(13,16), y(13,17), y(18,15), y(18,16), y(18,17), tmp(16,18). Үүнээс дор хаяж 2 давталт шаарддаг нь x(17,16), y(18,16), y(18,17), учир нь def нь эхний давталтаас гарсан утгыг дараагийн давталт хэрэглэнэ.


## Хэсэг B: bonus

### B2: Def/use жагсаалт (файлын мөрийн дугаар)

| Мөр | Код | Def | Use |
|---|---|---|---|
| 31 | 'bonus(int[] attendance, int threshold)' | attendance, threshold | |
| 32 | 'if (attendance == null \|\| threshold < 0)' | | attendance (p), threshold (p) |
| 35 | 'int present = 0;' | present | |
| 36 | 'int streak = 0;' | streak | |
| 37 | 'int best = 0;' | best | |
| 38 | 'for (int a : attendance)' | a | attendance (p) |
| 39 | 'if (a > 0)' | | a (p) |
| 40 | 'present = present + 1;' | present | present (c) |
| 41 | 'streak = streak + 1;' | streak | streak (c) |
| 42 | 'if (streak > best)' | | streak (p), best (p) |
| 43 | 'best = streak;' | best | streak (c) |
| 46 | 'streak = 0;' | streak | |
| 49 | 'int score = 0;' | score | |
| 50 | 'if (present >= threshold)' | | present (p), threshold (p) |
| 51 | 'score = 5;' | score | |
| 53 | 'if (best >= 5)' | | best (p) |
| 54 | 'score = score + 3;' | score | score (c) |
| 56 | 'return score;' | | score (c) |

Мөр 32-т `||`-ийн богино залгалтаас болж threshold нь зөвхөн attendance != null үед шалгагдана.

### B4 шат 1: ALL-DEFS

All-defs-ийг хангахад **2 тест** хэрэгтэй.

- B1: 'bonus({1,0}, 2)' нь present=1 < 2 тул 50-р мөрийн нөхцөл худал, score-ийн 49-р def нь 56-р мөрөнд хүрнэ.
- B2: 'bonus({1,0,1,1,1,1,1}, 3)' нь present=6 >= 3 тул 51-р def үүсэж, best=5 >= 5 тул 54-р def үүсэж 56-д хүрнэ. Дунд нь 0 байгаа тул 46-р def нь дараагийн 41-р мөрөнд use-д хүрнэ.

Яагаад 1 тестээр болохгүй вэ: score-ийн 49 ба 51 def-ийг нэг гүйцэтгэлд use-д хүргэх боломжгүй. Мөр 51 ажиллавал 49-ийн утгыг дарж бичнэ (kill), тэгэхээр 49 нь use-д хүрэхгүй. Харин 49 нь use-д хүрэхийн тулд 51 ажиллахгүй байх ёстой, өөрөөр хэлбэл 'present < threshold'  хэрэгтэй, 51 нь `present >= threshold` хэрэгтэй. Эдгээр нөхцөл нэг гүйцэтгэлд зэрэг биелэхгүй тул хамгийн цөөндөө 2 тест.

### JaCoCo (results/jacoco-alldefs.csv)

| Үзүүлэлт | Дутуу | Хамрагдсан |
|---|---|---|
| Branch | 2 | 14 |
| Line | 2 | 22 |

All-defs хангагдсан ч branch 100% биш: line 32-ын `attendance == null`, `threshold < 0` нөхцөлүүдийн true салаа (exception шидэх) хамрагдаагүй. Эдгээрийн тест нь all-DU-pairs шатанд нэмэгдэнэ.
EOF
