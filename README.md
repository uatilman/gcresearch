# Исследование работы Garbage Collector в Java.

Собрал несколько примеров, который позволили мне лучше понять механизм работы Garbage Collector в JAVA.

<p>
Для сборки и запуска можно использовать Makefile. <br>
Параметры jvm передаются в файле start.sh. <br>
Меню программы содержит 7 пунктов. <br>
Ниже привожу краткое описание и источники, на основе которых даннные пункты были созданы. <br>
</p>

### 1. Добавить объект в память
### 2. Удалить объекты из памяти

<p>
Для пунктов 1-2 используя VisualVM можно наглядно проследить использование памяти в Heap. 
В т.ч. заполнение Eden, и перетекание между Survivor1 и Survivor2 (вкладка Visual GC).</p>

<p>
Данные пункты и идея настоящей консольной утилиты основаны на следующей статье https://redstack.wordpress.com/2011/01/06/visualising-garbage-collection-in-the-jvm/,
<br>
в русскоязычном переводе https://habr.com/ru/post/112676/.
</p>


### 3. Сооздание недостижимых объектов в цикле
<p>
Пункт на каждой итерации бесконечноо цикла в цикле создает заданное в параметре size число объектов String (по умолчанию 5 млн объектов). На каждой итерации бесконечного цикла созданные ранее объекты становятся недостежимы. Используя VisualVM можно "анимировать" работу GC (рекомендуется делать птолько после понимания работы пунктов 1-2).
Для удаленного управления параметрами size и loop использована технология JMX. 
Для получения OutOfMemoryError увеличить параметр size. 
Для выхода из бесконечного цикла установить параметр loop в false.
</p>

<p>
Данный пример позаимствован из курса "Разработчик Java" на https://otus.ru/.
</p>

### 4. Пример полечения OutOfMemoryError в 1 строчу.
<p>
Пример из видео https://www.youtube.com/watch?v=AxL5LgoQyNs&t. <br>
Блог преподавателя http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html<br>
Канал https://www.youtube.com/channel/UCuIctN7x71qam9K_ZxS1W2A<br>
</p>

### 5. Пример генерирует 2 раза StackOverflowError,
 <p>
 при этом:<br>
 1й раз, рекурсивно вызывется метод, в котором ничего не создаеся, <br>
 2й раз, рекурсивно вызывется метод, в котором создаеся 10 примитивных long. <br>
 Для каждой рекурсии выводится расчет числа вызовов метода. <br>
 Данный пример косвенно позволяет оценить размер стека.
 </p>

<p>
Блог преподавателя http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html<br>
Канал https://www.youtube.com/channel/UCuIctN7x71qam9K_ZxS1W2A
</p>

### 6. Пример гененрирует MetaspaceOutOfMemoryError, создавая и загружая классы динаимчески.
<p>
В отличии от примера с переполением Permanent Generetion, преведенного на видео по ссылкам ниже, для переполнения Metaspace программу необходио запускать с параметром -XX:MaxMetaspaceSize, иначе Metaspace будет расширяться динамически и съест всю доступную память. Если запускать пример не на виртуальной машине, то это приведет (испробовано на Ubuntu 18.04.2 LTS) к полному подвисанию системы.
</p>

<p>
Пример из видео https://www.youtube.com/watch?v=Mv11WDpLupA <br>
Блог преподавателя http://kharkovitcourses.blogspot.com/2012/08/memory-permgen.html <br>
Канал https://www.youtube.com/channel/UCuIctN7x71qam9K_ZxS1W2A
</p>
