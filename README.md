## CuncarCollectionNetoLess
### Задача 1. Программа-анализатор
#### Описание
Ваш коллега из первой задачи до сих пор ломает голову над математической статистикой. Благодаря уже известному вам генератору, он создаёт из символов "abc" 10 000 текстов, длиной 100 000 каждый.
#### Реализация
1. Создайте в статических полях три потокобезопасные блокирующие очереди.
2. Создайте поток, который наполнял бы эти очереди текстами.
3. Создайте по потоку для каждого из трёх символов 'a', 'b' и 'c', которые разбирали бы свою очередь и выполняли подсчёты.
