package moneycalculator.model;

import java.util.Objects;

public class Currency {
    private final String name;
    private final String code;
    private final String symbol;


    public Currency(String code,String name, String symbol) {
        this.code = code;
        this.name = name;
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return  code ;
    }
    
    @Override
    public int hashCode() {
        return this.code.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Currency other = (Currency) obj;
        return Objects.equals(this.code, other.code);
    }
    
}
