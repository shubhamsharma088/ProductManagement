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
import static java.time.LocalDate.now;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Food extends Product {

  private final LocalDate bestBeforeTime;

  Food(int id, String name, BigDecimal price,
      Rating rating, LocalDate bestBeforeTime) {
    super(id, name, price, rating);
    this.bestBeforeTime = bestBeforeTime;
  }

  public LocalDate getBestBeforeDate() {
    return bestBeforeTime;
  }

  @Override
  public BigDecimal getDiscount() {
    return bestBeforeTime.isEqual(now()) ?
        super.getDiscount() : ZERO;

  }

  @Override
  public Food applyRating(Rating rating) {
    return rating.equals(getRating()) ? this
        : new Food(getId(), getName(), getPrice(), rating, bestBeforeTime);
  }

  @Override
  public String toString() {
    return super.toString() + bestBeforeTime;
  }
}
