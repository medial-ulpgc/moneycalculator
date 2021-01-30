package moneycalculator.view;

import moneycalculator.model.Money;

public interface MoneyDialog {

    Money get();

    void on(DialogChange dialogChange);

    public interface DialogChange {

        void execute(Money money);
    }

}
