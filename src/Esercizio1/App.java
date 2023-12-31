package Esercizio1;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
	public static void main(String[] args) {
		Logger log = LoggerFactory.getLogger(App.class);
		Random iannice = new Random();

		// ESERCIZIO1
		List<Product> products = new ArrayList<>();
		products.add(new Product(iannice.nextLong(), "Il signore degli Anelli", "Books", 150.00));
		products.add(new Product(iannice.nextLong(), "Il signore degli Anelli", "Film", 150.00));
		products.add(new Product(iannice.nextLong(), "Harry Potter", "Books", 200.00));
		products.add(new Product(iannice.nextLong(), "Harry Potter", "Film", 200.00));
		products.add(new Product(iannice.nextLong(), "Il codice da Vinci", "Books", 50.00));

		List<Product> filtPDT = (List<Product>) products.stream()
				.filter(p -> p.getCategory().equals("Books") && p.getPrice() > 100).collect(Collectors.toList());

		for (Product product : filtPDT) {
			System.out.println("ESERCIZIO 1");
			log.info(product.toString());
			System.out.println("----------------------------------------------------");
		}

		// ESERCIZIO2-------------------------------------------------------------
		List<Order> orders = createOrders();

		List<Order> ords = orders.stream()
				.filter(o -> o.getProducts().stream().anyMatch(p -> p.getCategory().equals("Baby")))
				.collect(Collectors.toList());

		for (Order order : orders) {
			System.out.println("ESERCIZIO2");
			log.info("\t" + order.toString());
			System.out.println("Products:");
			for (Product product : order.getProducts()) {
				double roundedPrice = Math.round(product.getPrice() * 100.0) / 100.0;
				product.setPrice(roundedPrice);
				log.info("\t" + product.toString());
				System.out.println("-------------------------------------------------------");
			}
		}

		// ESERCIZIO3-----------------------------------------------------------------
		List<Product> pdt = createProductList();

		List<Product> pdtList = pdt.stream().filter(p -> p.getCategory().equals("Boys")).map(p -> {
			double discount = p.getPrice() * 0.1;
			double discountPrice = Double.parseDouble(String.format("%.2f", p.getPrice() - discount).replace(",", "."));
			return new Product(p.getId(), p.getName(), p.getCategory(), discountPrice);
		}).collect(Collectors.toList());

		System.out.println("Esercizio3");
		System.out.println("Prodotti scontati: ");
		for (Product pdts : pdtList) {
			log.info(pdts.toString());
		}

		// ESERCIZIO4--------------------------------------------------------------------
		Random random = new Random();
		List<Order> ordini = new ArrayList<>();

		ordini.add(new Order(random.nextLong(), "In corso", LocalDate.of(2021, 3, 10), LocalDate.of(2021, 3, 15),
				List.of(new Product(random.nextLong(), "prodotto1", "Boys", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto2", "Girls", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto3", "Boys", random.nextDouble() * 100)),
				new Customer(random.nextLong(), "Cliente1", 2)));

		ordini.add(new Order(random.nextLong(), "In corso", LocalDate.of(2021, 2, 5), LocalDate.of(2021, 2, 10),
				List.of(new Product(random.nextLong(), "prodotto4", "Boys", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto5", "Boys", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto6", "Boys", random.nextDouble() * 100)),
				new Customer(random.nextLong(), "Cliente2", 1)));

		ordini.add(new Order(random.nextLong(), "In corso", LocalDate.of(2021, 4, 1), LocalDate.of(2021, 4, 5),
				List.of(new Product(random.nextLong(), "prodotto7", "Girls", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto8", "Girls", random.nextDouble() * 100),
						new Product(random.nextLong(), "prodotto9", "Girls", random.nextDouble() * 100)),
				new Customer(random.nextLong(), "Cliente3", 2)));

		List<Product> pdt4 = ordini.stream().filter(order -> order.getCustomer().getTier() == 2)
				.filter(order -> order.getOrderDate().isAfter(LocalDate.of(2021, 1, 31))
						&& order.getOrderDate().isBefore(LocalDate.of(2021, 4, 2)))
				.flatMap(order -> order.getProducts().stream()).collect(Collectors.toList());

		System.out.println("ESERCIZIO 4");
		for (Product product : pdt4) {
			double roundedPrice = Math.round(product.getPrice() * 100.0) / 100.0;
			product.setPrice(roundedPrice);
			log.info(product.toString());
		}

	}

	// ESERCIZIO2 ----------------------------------------
	private static List<Order> createOrders() {
		List<Order> orders = new ArrayList<>();
		Random guerra = new Random();
		LocalDate today = LocalDate.now();

		Customer customer1 = new Customer(guerra.nextLong(), "Alice", 8);
		Customer customer2 = new Customer(guerra.nextLong(), "Bob", 5);
		Customer customer3 = new Customer(guerra.nextLong(), "Charlie", 2);

		Order task1 = new Order(guerra.nextLong(), "New", today.minusDays(3), today.plusDays(3),
				createProducts("Baby", 5), customer1);
		Order task2 = new Order(guerra.nextLong(), "New", today.minusDays(5), today.plusDays(5),
				createProducts("Baby", 5), customer2);
		Order task3 = new Order(guerra.nextLong(), "New", today.minusDays(7), today.plusDays(7),
				createProducts("Toys", 5), customer3);

		orders.add(task1);
		orders.add(task2);
		orders.add(task3);

		return orders;
	}

	private static List<Product> createProducts(String category, int quantita) {
		List<Product> products = new ArrayList<>();
		Random num = new Random();

		for (int i = 1; i <= quantita; i++) {
			Long id = Long.valueOf(i);
			double price = Math.round(num.nextDouble() * 100.0) / 100.0;
			Product pdt = new Product(id, "Product" + i, category, price);
			products.add(pdt);
		}
		return products;
	}

	// (----------------------------------------------------------------------------)
	// ESERCIZIO3
	private static List<Product> createProductList() {
		List<Product> pdt = new ArrayList<>();
		Random id = new Random();
		Random price = new Random();

		pdt.add(new Product(id.nextLong(), "T-Shirt", "Boys", price.nextDouble(100.0)));
		pdt.add(new Product(id.nextLong(), "Jeans", "Girls", price.nextDouble(100.0)));
		pdt.add(new Product(id.nextLong(), "Sneakers", "Boys", price.nextDouble(100.0)));
		pdt.add(new Product(id.nextLong(), "Skirt", "Girls", price.nextDouble(100.0)));

		return pdt;
	}

	// (----------------------------------------------------------------------------)
}
