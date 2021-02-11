package com.es.phoneshop.model.product;

import java.math.BigDecimal;
import java.util.Date;

public class PriceHistory {
    private BigDecimal price;
    private Date date;

    public PriceHistory(BigDecimal price, Date date) {
        this.price = price;
        this.date = date;
    }

    public PriceHistory() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceHistory that = (PriceHistory) o;

        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        return date != null ? date.equals(that.date) : that.date == null;
    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
