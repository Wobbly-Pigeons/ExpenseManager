package wobbly.pigeons.expensemanager.currency.rest;

public class Currency {

    private String code;
    private Double value;

    public Currency() {
    }

    public Currency(String code, Double value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", value=" + value +
                '}';
    }
}
