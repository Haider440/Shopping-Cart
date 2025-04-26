package MidTermPractice.Shopping;

import java.util.Scanner;

// Abstract class representing a general product
abstract class Product {
    private int id;
    private String name;
    private double price;

    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    // Abstract method for displaying product details
    public abstract void displayDetails();
}

// Class representing a physical product
class PhysicalProduct extends Product {
    public PhysicalProduct(int id, String name, double price) {
        super(id, name, price);
    }

    @Override
    public void displayDetails() {
        System.out.println(getId() + ") " + getName() + " - $" + getPrice());
    }
}

// Shopping cart system class with encapsulation for cart quantities
class ShoppingCartSystem {
    private Product[] products;
    private int[] cartQuantities;

    public ShoppingCartSystem(Product[] products) {
        this.products = products;
        this.cartQuantities = new int[products.length];
    }

    public void displayProducts() {
        System.out.println("-----------------------------------");
        System.out.println("\nAvailable Products:");
        for (Product product : products) {
            product.displayDetails(); // Polymorphism in action
        }
    }

    public void displayMenu() {
        System.out.println("\nOptions:");
        System.out.println("1. Add to Cart");
        System.out.println("2. View Cart");
        System.out.println("3. Checkout");
        System.out.println("4. Exit");
        System.out.print("Enter your choice: ");
    }

    // Method for handling user choices
    public boolean handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addToCart();
                break;
            case 2:
                viewCart();
                break;
            case 3:
                checkout();
                return false; // Exit after checkout
            case 4:
                return false; // Exit
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true; // Continue shopping
    }

    // Encapsulated method for modifying cart quantities
    public void addToCart() {
        System.out.print("Enter the product number to add to cart: ");
        int productNumber = getUserInput();
        System.out.print("Enter quantity: ");
        int quantity = getUserInput();

        if (productNumber > 0 && productNumber <= products.length) {
            updateCartQuantity(productNumber - 1, quantity);
            System.out.println("Added to cart: " + products[productNumber - 1].getName() + " (x" + quantity + ")");
        } else {
            System.out.println("Invalid product number.");
        }
    }

    // Method to access and update cart quantity, demonstrating encapsulation
    private void updateCartQuantity(int index, int quantity) {
        cartQuantities[index] += quantity;
    }

    public void viewCart() {
        System.out.println("\nYour Cart:");
        boolean isEmpty = true;
        double total = 0;
        for (int i = 0; i < products.length; i++) {
            if (cartQuantities[i] > 0) {
                isEmpty = false;
                double itemTotal = cartQuantities[i] * products[i].getPrice();
                total += itemTotal;
                System.out.println(products[i].getName() + " (x" + cartQuantities[i] + ") - $" + itemTotal);
            }
        }

        if (isEmpty) {
            System.out.println("Your cart is empty.");
        } else {
            System.out.println("Total: $" + total);
        }
    }

    public void checkout() {
        System.out.println("\nFinalizing your order:");
        boolean isEmpty = true;
        double total = 0;
        for (int i = 0; i < products.length; i++) {
            if (cartQuantities[i] > 0) {
                isEmpty = false;
                double itemTotal = cartQuantities[i] * products[i].getPrice();
                total += itemTotal;
                System.out.println(products[i].getName() + " (x" + cartQuantities[i] + ") - $" + itemTotal);
                cartQuantities[i] = 0; // Reset the cart quantities after checkout
            }
        }

        if (isEmpty) {
            System.out.println("Your cart is empty. Nothing to checkout.");
        } else {
            System.out.println("Total: $" + total);
            System.out.println("Order placed successfully!");
        }
    }

    // A simple method to get input through Scanner
    private int getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}

// Main class for the application
public class ShoppingCart {
    public static void main(String[] args) {
        Product[] products = {
            new PhysicalProduct(1, "Laptop", 1200.00),
            new PhysicalProduct(2, "Smartphone", 800.00),
            new PhysicalProduct(3, "Headphones", 150.00),
            new PhysicalProduct(4, "Keyboard", 100.00)
        };
        
        ShoppingCartSystem system = new ShoppingCartSystem(products);
        Scanner scanner = new Scanner(System.in);
        boolean shopping = true;

        System.out.println("Welcome to the Online Shopping Cart System!");

        while (shopping) {
            system.displayProducts();
            system.displayMenu();

            // Get user choice from scanner in main method
            int choice = scanner.nextInt();

            // Call handleUserChoice method
            shopping = system.handleUserChoice(choice);
        }

        System.out.println("Thank you for shopping with us!");
    }
}
