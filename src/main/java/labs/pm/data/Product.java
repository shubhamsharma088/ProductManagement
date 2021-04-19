/*
 * Copyright © 2021 Shubham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package labs.pm.data;

import static java.math.RoundingMode.HALF_UP;
import static labs.pm.data.Rating.NOT_RATED;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * {@code Product} class represents properties and behaviour of product object in product management
 * system. <br> Each product has id, name and price. <br> Each product can have a discount,
 * calculated based on a DISCOUNT_RATE
 *
 * @author Shubham Sharma
 * @version 1.0.0
 */
public abstract class Product implements Rateable<Product> {

  private final int id;
  private final String name;
  private final BigDecimal price;
  private final Rating rating;

  protected Product(final int id, final String name, final BigDecimal price, final Rating rating) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.rating = rating;
  }

  protected Product(final int id, final String name, final BigDecimal price) {
    this(id, name, price, NOT_RATED);
  }

  @Override
  public Rating getRating() {
    return rating;
  }

  public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public BigDecimal getDiscount() {
    return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
  }

  public abstract LocalDate getBestBeforeDate();


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return id == product.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", price=" + price +
        ", rating=" + rating.getStars() +
        ", discount=" + getDiscount() +
        '}';
  }
}
