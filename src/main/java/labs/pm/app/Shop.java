/*
 * Copyright © 2021 Shubham
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package labs.pm.app;


import static java.math.BigDecimal.valueOf;
import static java.time.LocalDate.now;
import static labs.pm.data.Rating.FIVE_STAR;
import static labs.pm.data.Rating.FOUR_STAR;
import static labs.pm.data.Rating.NOT_RATED;
import static labs.pm.data.Rating.ONE_STAR;
import static labs.pm.data.Rating.THREE_STAR;
import static labs.pm.data.Rating.TWO_STAR;

import java.util.Comparator;
import labs.pm.data.Product;
import labs.pm.data.ProductManager;

/**
 * {@code Shop} class represents an application that manages Products
 *
 * @author Shubham Sharma
 * @version 1.0.0
 */
public class Shop {

  public static void main(String[] args) {
    ProductManager pm = new ProductManager("en-GB");

    pm.createProduct(101, "Tea", valueOf(1.99), NOT_RATED, now().plusMonths(12));
    pm.reviewProduct(101, FOUR_STAR, "Best in town");
    pm.reviewProduct(101, FOUR_STAR, "Best in town");
    pm.reviewProduct(101, TWO_STAR, "Not so good");
    pm.reviewProduct(101, THREE_STAR, "Best in town");
    pm.reviewProduct(101, FOUR_STAR, "Best in town");
    //pm.printProductReport(101);

    pm.createProduct(102, "Coffee", valueOf(1.99), FOUR_STAR, now().plusDays(7));
    pm.reviewProduct(102, FIVE_STAR, "The Best");
    pm.reviewProduct(102, THREE_STAR, "Average coffee");
    pm.reviewProduct(102, ONE_STAR, "Don't drink");
    //pm.printProductReport(102);

    pm.createProduct(103, "Cake", valueOf(2.99), TWO_STAR, now().plusDays(2));
    pm.reviewProduct(103, FOUR_STAR, "Nice");
    pm.reviewProduct(103, THREE_STAR, "Nice");
    //pm.printProductReport(103);

    pm.createProduct(104, "Cookie", valueOf(2.99), NOT_RATED, now().plusDays(2));
    pm.reviewProduct(104, FOUR_STAR, "Nice");
    pm.reviewProduct(104, THREE_STAR, "Nice");
    //pm.printProductReport(104);

    pm.createProduct(105, "Hot Chocolate", valueOf(1.99), FIVE_STAR, now().plusDays(2));
    pm.reviewProduct(105, FOUR_STAR, "Nice");
    pm.reviewProduct(105, THREE_STAR, "Nice");
    //pm.printProductReport(105);

    pm.createProduct(106, "Chocolate", valueOf(2.99), NOT_RATED, now().plusDays(2));
    pm.reviewProduct(106, FOUR_STAR, "Nice");
    pm.reviewProduct(106, THREE_STAR, "Nice");
    //pm.printProductReport(106);

    Comparator<Product> ratingComparator = (p1, p2) -> p2.getRating().ordinal() - p1.getRating()
        .ordinal();
    Comparator<Product> priceComparator = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());

    pm.printProducts(priceComparator.thenComparing(ratingComparator));
  }
}
