package com.jgive.kobieliasi.jgiveapp;

import android.provider.BaseColumns;

/**
 * Created by Kobi Eliasi on 07/01/2017.
 */
final class Constants {
    private Constants(){
        throw new AssertionError("Can't create constants");
    }

    public static abstract class Incomes implements BaseColumns {
        public static final String TABLE_NAME = "incomesTable";
        public static final String USER_NAME = "userName";
        public static final String SOURCE = "source";
        public static final String AMOUNT = "amount";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
    }

    public static abstract class Expenses implements BaseColumns {
        public static final String TABLE_NAME = "expensesTable";
        public static final String USER_NAME = "userName";
        public static final String TARGET = "target";
        public static final String AMOUNT = "amount";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
    }

    public static abstract class Donations implements BaseColumns {
        public static final String TABLE_NAME = "donationsTable";
        public static final String USER_NAME = "userName";
        public static final String TARGET = "target";
        public static final String AMOUNT = "amount";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
    }
}
