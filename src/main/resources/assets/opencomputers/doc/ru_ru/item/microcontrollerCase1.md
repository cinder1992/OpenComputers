# Корпус микроконтроллера

![Он такой милый.](oredict:oc:microcontrollerCase1)

Корпус микроконтроллера используется для создания [микроконтроллеров](../block/microcontroller.md) в [сборщике](../block/assembler.md). [Микроконтроллеры](../block/microcontroller.md) - это очень примитивные [компьютеры](../general/computer.md). Они содержат только очень ограниченный набор компонентов и используются в специфических задачах: реагирование на сигнал красного камня или обработка сетевых сообщений.

Они не имеют файловой системы и программируются только с помощью [EEPROM](eeprom.md). Он может быть заменен на другой, если объединить [микроконтроллер](../block/microcontroller.md) с другим чипом. При этом старый [EEPROM](eeprom.md) вернется в ваш инвентарь.

Для работы они также требуют энергию, но потребляют очень малое ее количество.

Микроконтроллер 1 уровня может использовать следующие компоненты:
- 1 [процессор](cpu1.md) первого уровня
- 1 [планка памяти](ram1.md) первого уровня
- 1 [EEPROM](eeprom.md)
- 2 карты расширения первого уровня
- 1 улучшение второго уровня

Микроконтроллер 2 уровня может использовать следующие компоненты:
- 1 [процессор](cpu1.md) первого уровня
- 2 [планки памяти](ram1.md) первого уровня
- 1 [EEPROM](eeprom.md)
- 1 карту расширения второго уровня
- 1 карту расширения первого уровня
- 1 улучшение третьего уровня

Микроконтроллер 3 уровня может использовать следующие компоненты:
- 1 [процессор](cpu3.md) третьего уровня
- 2 [планки памяти](ram5.md) третьего уровня
- 1 [EEPROM](eeprom.md)
- 3 карты расширения третьего уровня
- 9 улучшений третьего уровня