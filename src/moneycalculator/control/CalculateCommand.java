package moneycalculator.control;

import moneycalculator.model.Money;
import moneycalculator.view.MoneyDialog;
import moneycalculator.view.MoneyDisplay;

public class CalculateCommand implements Command {

    private final MoneyDisplay moneyDisplay;

    private final MoneyDialog moneyDialog;

    public CalculateCommand(MoneyDisplay moneyDisplay, MoneyDialog moneyDialog) {
        this.moneyDisplay = moneyDisplay;
        this.moneyDialog = moneyDialog;
    }

    @Override
    public void execute() {
        moneyDisplay.display(moneyDialog.get());
        moneyDialog.on((Money money) -> {
            moneyDisplay.display(money);
        });
    }

}
