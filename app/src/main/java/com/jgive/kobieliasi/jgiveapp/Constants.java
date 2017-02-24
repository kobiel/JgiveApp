package com.jgive.kobieliasi.jgiveapp;

import android.provider.BaseColumns;

/**
 * Created by Kobi Eliasi on 07/01/2017.
 * Constants for DB
 */
final class Constants {
    private Constants(){
        throw new AssertionError("Can't create constants");
    }

    protected static abstract class Incomes implements BaseColumns {
        protected static final String TABLE_NAME = "incomesTable";
        protected static final String USER_NAME = "userName";
        protected static final String SOURCE = "source";
        protected static final String AMOUNT = "amount";
        protected static final String YEAR = "year";
        protected static final String MONTH = "month";
    }

    protected static abstract class Expenses implements BaseColumns {
        protected static final String TABLE_NAME = "expensesTable";
        protected static final String USER_NAME = "userName";
        protected static final String TARGET = "target";
        protected static final String AMOUNT = "amount";
        protected static final String YEAR = "year";
        protected static final String MONTH = "month";
    }

    protected static abstract class Donations implements BaseColumns {
        protected static final String TABLE_NAME = "donationsTable";
        protected static final String USER_NAME = "userName";
        protected static final String TARGET = "target";
        protected static final String AMOUNT = "amount";
        protected static final String YEAR = "year";
        protected static final String MONTH = "month";
    }

    protected static abstract class Provisions implements BaseColumns {
        protected static final String TABLE_NAME = "provisionsTable";
        protected static final String USER_NAME = "userName";
        protected static final String YEAR = "year";
        protected static final String MONTH = "month";
        protected static final String AMOUNT = "amount";
    }
}
