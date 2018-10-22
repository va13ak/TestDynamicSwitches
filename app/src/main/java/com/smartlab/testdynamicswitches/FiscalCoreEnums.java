package com.smartlab.testdynamicswitches;


public class FiscalCoreEnums {

    public interface EnumWithValueAndDescription {
        String getDescription();
        int getValue();
    }

    /// Признак агента [Flags]
    public enum AgentTag implements EnumWithValueAndDescription {
        /// Агентом не является
        None(0, "Агентом не является"),

        /// Банковский платёжный агент
        BankPayAgent(1, "Банковский платёжный агент"),

        /// Банковский платёжный субагент
        BankPaySubAgent(2, "Банковский платёжный субагент"),

        /// Платёжный агент
        PayAgent(4, "Платёжный агент"),

        /// Платёжный субагент
        PaySubAgent(8, "Платёжный субагент"),

        /// Поверенный
        Attorney(16, "Поверенный"),

        /// Комиссионер
        CommissionAgent(32, "Комиссионер"),

        /// Иной агент
        Agent(64, "Иной агент");

        private String description;
        private int value;

        AgentTag(int value, String description) {
            this.description = description;
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public int getValue() {
            return value;
        }
    }

    /// Код системы налогообложения
    /// Используется при регистрации и перерегистрации [Flags]
    public enum TaxCode implements EnumWithValueAndDescription {
        /// Общая
        Common(0x01, "ОСН"),

        /// Упрощённая Доход
        Simplified(0x02, "УСН доход"),

        /// Упрощённая Доход минус Расход
        SimplifiedWithExpense(0x04, "УСН доход - расход"),

        /// Единый налог на вмененный доход
        ENVD(0x08, "ЕНВД"),

        /// Единый сельскохозяйственный налог
        CommonAgricultural(0x10, "ЕСН"),

        /// Патентная система налогообложения
        Patent(0x20, "Патент");

        private String description;
        private int value;

        TaxCode(int value, String description) {
            this.description = description;
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public int getValue() {
            return value;
        }
    }

    /// Режимы работы [Flags]
    public enum OperatingMode implements EnumWithValueAndDescription {
        /// Основной режим работы
        Default(0x00, "0 Основной"),

        /// Шифрование
        Encryption(0x01, "1  Шифрование"),

        /// Автономный режим
        Autonomous(0x02, "2  Автономный режим"),

        /// Автоматический режим
        Automatic(0x04, "4  Автоматический режим"),

        /// Применение в сфере услуг
        Service(0x08, "8  Применение в сфере услуг"),

        /// Режим БСО (иначе - режим чеков)
        BSOMode(0x10, "16 Режим БСО (иначе – режим чеков)"),

        /// Применение в Интернет
        InternetUsing(0x20, "32 Применение в Интернет");

        private String description;
        private int value;

        OperatingMode(int value, String description) {
            this.description = description;
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public int getValue() {
            return value;
        }
    }

    public static <T extends Enum<T> & EnumWithValueAndDescription> T getElementByValue(T[] aValues, int value) {
        for (T element: aValues) {
            if (element.getValue() == value) {
                return element;
            }
        }
        return null;
    }

    public static <T extends Enum<T> & EnumWithValueAndDescription> String getElementsDescriptionByValue(T[] aValues, int value) {
        T element = getElementByValue(aValues, value);
        if (element != null) {
            return element.getDescription();
        }
        return null;
    }
}
