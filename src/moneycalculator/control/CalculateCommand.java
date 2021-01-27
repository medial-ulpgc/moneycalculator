package moneycalculator.control;

import java.util.function.Supplier;
import moneycalculator.view.MoneyDialog;
import moneycalculator.view.MoneyDisplay;

public class CalculateCommand implements Command {

    private final Supplier<MoneyDisplay> moneyDisplay;

    private final Supplier<MoneyDialog> moneyDialog;

    public CalculateCommand(Supplier<MoneyDisplay> moneyDisplay, Supplier<MoneyDialog> moneyDialog) {
        this.moneyDisplay = moneyDisplay;
        this.moneyDialog = moneyDialog;
    }

    @Override
    public void execute() {
        moneyDisplay.get().display(moneyDialog.get().get());
    }

}
