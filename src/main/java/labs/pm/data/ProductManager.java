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

import static java.text.MessageFormat.format;
import static java.text.NumberFormat.getCurrencyInstance;
import static java.time.format.DateTimeFormatter.ofLocalizedDate;
import static java.util.Collections.sort;
import static java.util.Locale.CHINA;
import static java.util.Locale.FRANCE;
import static java.util.Locale.UK;
import static java.util.Locale.US;
import static java.util.Objects.isNull;
import static java.util.ResourceBundle.getBundle;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class ProductManager {

  Map<Product, List<Review>> products = new HashMap<>();
  private static Map<String, ResourceFormatter> formatters =
      Map.of("en-GB", new ResourceFormatter(UK),
          "en-US", new ResourceFormatter(US),
          "fr-FR", new ResourceFormatter(FRANCE),
          "ru-RU", new ResourceFormatter(new Locale("ru", "RU")),
          "zh-CN", new ResourceFormatter(CHINA)
      );
  private ResourceFormatter formatter;

  public ProductManager(Locale locale) {
    this.changeLocale(locale.toLanguageTag());
  }

  public ProductManager(String languageTag) {
    changeLocale(languageTag);
  }

  public void changeLocale(String languageTag) {
    formatter = formatters.getOrDefault(languageTag, formatters.get("en-GB"));
  }

  public static Set<String> getSupportedLocales() {
    return formatters.keySet();
  }

  private static class ResourceFormatter {

    private ResourceBundle resources;
    private DateTimeFormatter dateTimeFormatter;
    private NumberFormat numberFormat;
    private Locale locale;

    public ResourceFormatter(Locale locale) {
      this.locale = locale;
      resources = getBundle("resources", locale);
      dateTimeFormatter = ofLocalizedDate(FormatStyle.SHORT).localizedBy(locale);
      numberFormat = getCurrencyInstance(locale);
    }

    private String formatProduct(Product product) {
      return format(getText("product"),
          product.getName(),
          numberFormat.format(product.getPrice()),
          product.getRating().getStars(),
          dateTimeFormatter.format(product.getBestBeforeDate()));
    }

    private String formatReview(Review review) {
      return format(getText("review"),
          review.getRating().getStars(), review.getComments());
    }

    private String getText(String key) {
      return resources.getString(key);
    }
  }

  public Product createProduct(int id, String name, BigDecimal price, Rating rating,
      LocalDate bestBeforeTime) {
    var product = new Food(id, name, price, rating, bestBeforeTime);

    products.putIfAbsent(product, new ArrayList<>());
    return product;
  }

  public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
    var product = new Drink(id, name, price, rating);
    products.putIfAbsent(product, new ArrayList<>());
    return product;
  }

  public Product reviewProduct(Product product, Rating rating, String comments) {
    int sum = 0;

    List<Review> reviews = products.getOrDefault(product, new ArrayList<>());
    products.remove(product, reviews);
    reviews.add(new Review(rating, comments));

    for (Review review : reviews) {
      sum += review.getRating().ordinal();
    }

    product = product
        .applyRating(Rateable.convert(Math.round((float) sum / reviews.size())));

    products.put(product, reviews);
    return product;
  }

  public Product reviewProduct(int productId, Rating rating, String comments) {
    return reviewProduct(findProduct(productId), rating, comments);
  }

  public void printProductReport(int productId) {
    printProductReport(findProduct(productId));
  }

  public void printProductReport(Product product) {

    List<Review> reviews = products.getOrDefault(product, new ArrayList<>());

    StringBuilder sb = new StringBuilder();
    sb.append(formatter.formatProduct(product));

    sb.append("\n");
    sort(reviews);
    for (Review review : reviews) {
      if (isNull(review)) {
        break;
      }
      sb.append(formatter.formatReview(review));
      sb.append('\n');
    }
    if (reviews.isEmpty()) {
      sb.append(formatter.getText("no.reviews"));
    }
    sb.append('\n');
    System.out.println(sb);
  }

  public void printProducts(Comparator<Product> comparator) {
    ArrayList<Product> productList = new ArrayList<>(this.products.keySet());
    productList.sort(comparator);
    StringBuilder sb = new StringBuilder();

    for (var product : productList) {
      sb.append(formatter.formatProduct(product)).append('\n');
    }
    System.out.println(sb);
  }

  public Product findProduct(int productId) {
    Product result = null;
    for (Product p : products.keySet()) {
      if (p.getId() == productId) {
        result = p;
        break;
      }
    }
    return result;
  }

}
