/*
 * Copyright © 2021 Shubham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package labs.pm.data;

import static java.math.BigDecimal.ZERO;
import static java.time.LocalTime.now;
import static java.time.LocalTime.of;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public class Drink extends Product {

  Drink(int id, String name, BigDecimal price,
      Rating rating) {
    super(id, name, price, rating);
  }

  @Override
  public BigDecimal getDiscount() {
    LocalTime now = now();
    return (now.isAfter(of(17, 30)) && now.isBefore(of(18, 30))) ?
        super.getDiscount() : ZERO;

  }

  @Override
  public Drink applyRating(Rating rating) {
    return rating.equals(getRating()) ? this : new Drink(getId(), getName(), getPrice(), rating);
  }

  @Override
  public LocalDate getBestBeforeDate() {
    return LocalDate.now();
  }
}
