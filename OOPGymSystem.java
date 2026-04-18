import java.time.LocalDate;
import java.util.ArrayList;

enum Gender { male, female }

interface shippable {
    double getWeight();
    String Name();
}

interface Expiriable {
    LocalDate getExpiriationDate();
}

abstract class Person {
    protected String name;
    protected int age;
    protected Gender gender;

    abstract public String getName();
    abstract public int getAge();
    abstract public Gender getGender();
    abstract public void setName(String name);
    abstract public void setAge(int age);
    abstract public void setGender(Gender gender);
}

abstract class Subscription {
    public abstract double getDiscount();
    public abstract String getType();
}

class GoldSubscription extends Subscription {
    private String Type = "Gold";
    private double Discount = 10.0 / 100;

    public double getDiscount() {
        return Discount;
    }

    public String getType() {
        return Type;
    }
}

class SelverSubscription extends Subscription {
    private String Type = "Selver";
    private double Discount = 3.0 / 100;

    public double getDiscount() {
        return Discount;
    }

    public String getType() {
        return Type;
    }
}

class Member extends Person {
    private double money;
    Subscription subscription;
    private double subscriptionPrice;

    Member(String name, int age, Gender gender, double money, Subscription subscription) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.money = money;
        this.subscription = subscription;
        setSubscriptionPrice();
    }

    public String getName() {
        return name;
    }

    public double getMony() {
        return money;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public double getMoney() {
        return money;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public double getSubscriptionPrice() {
        return subscriptionPrice;
    }

    public void setSubscriptionPrice() {
        subscriptionPrice = GymSystem.Price - GymSystem.Price * subscription.getDiscount();
    }
}

class Coach extends Person {

    public Coach(String name, int age, Gender gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

abstract class Product {
    protected String name;
    protected double price;
    protected int amount;

    public abstract String getName();
    public abstract int getAmount();
    public abstract void setAmount(int amount);
    public abstract double getPrice();
    public abstract void Setname(String name);
    public abstract void SetPrice(double price);

    public boolean isShippable() {
        return false;
    }

    public ShippingRecord getShippingDetails() {
        return null;
    }
}

class Creatine extends Product implements shippable, Expiriable {
    private double Wight;
    private LocalDate Expiry;

    public Creatine(String name, double price, double wight, LocalDate expiry, int amount) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.Wight = wight;
        this.Expiry = expiry;
    }

    public String getName() {
        return name;
    }

    public boolean isShippable() {
        return true;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void Setname(String name) {
        this.name = name;
    }

    public void SetPrice(double price) {
        this.price = price;
    }

    public double getWight() {
        return Wight;
    }

    public void setWight(double wight) {
        Wight = wight;
    }

    public LocalDate getExpiry() {
        return Expiry;
    }

    public void setExpiry(LocalDate expiry) {
        Expiry = expiry;
    }

    public double getWeight() {
        return Wight;
    }

    public String Name() {
        return name;
    }

    public LocalDate getExpiriationDate() {
        return Expiry;
    }

    public ShippingRecord getShippingDetails() {
        return new ShippingRecord(name, price, Wight);
    }
}

class HandGrip extends Product implements shippable {
    private double Wight;

    public HandGrip(String name, double price, double wight, int amount) {
        this.name = name;
        this.amount = amount;
        this.price = price;
        this.Wight = wight;
    }

    public boolean isShippable() {
        return true;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void Setname(String name) {
        this.name = name;
    }

    public void SetPrice(double price) {
        this.price = price;
    }

    public double getWight() {
        return Wight;
    }

    public void setWight(double wight) {
        Wight = wight;
    }

    public double getWeight() {
        return Wight;
    }

    public String Name() {
        return name;
    }

    public ShippingRecord getShippingDetails() {
        return new ShippingRecord(name, price, Wight);
    }
}

class Cart {
    ArrayList<Product> products;

    public Cart() {
        products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new NullPointerException("Product is null");
        }
        products.add(product);
    }
}

class ChekOut {
    Shipping shipping;

    public ChekOut() {
        this.shipping = new Shipping();
    }

    public void paying(Cart cart, Member member) {
        if (cart == null || member == null) {
            throw new NullPointerException("Cart or Member is null");
        }

        double total = 0;

        for (var p : cart.products) {
            if (p instanceof Expiriable) {
                Expiriable e = (Expiriable) p;
                if (e.getExpiriationDate().isBefore(LocalDate.now())) {
                    continue;
                }
            }

            if (p.getAmount() <= 0) continue;

            total += p.getPrice() * p.getAmount();

            ShippingRecord sr = p.getShippingDetails();
            if (sr != null) {
                total += shipping.add(sr);
            }
        }

        if (total > member.getMoney()) {
            System.out.println("Not enough money!");
            return;
        }

        for (var p : cart.products) {
            if (p.getAmount() <= 0) continue;
            p.setAmount(p.getAmount() - 1);
        }

        member.setMoney(member.getMoney() - total);
        System.out.println("Payment is Done!");
    }
}

class ShippingRecord {
    String ProductName;
    double Price;
    double Wight;

    public ShippingRecord(String name, double price, double weight) {
        this.ProductName = name;
        this.Price = price;
        this.Wight = weight;
    }

    public void setProductName(String ProductName, double Price, double Wight) {
        this.ProductName = ProductName;
        this.Price = Price;
        this.Wight = Wight;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getProductName() {
        return ProductName;
    }

    public double getPrice() {
        return Price;
    }

    public double getWight() {
        return Wight;
    }

    public void setWight(double wight) {
        Wight = wight;
    }
}

class Shipping {
    ArrayList<ShippingRecord> shippings = new ArrayList<>();
    private double fee = 10;

    public double add(ShippingRecord sr) {
        double shippingCost = sr.getWight() * fee;
        sr.setPrice(sr.getPrice() + shippingCost);
        shippings.add(sr);
        return shippingCost;
    }

    public void getFeePrice() {
        System.out.println("Cost of Fee is : " + fee + " pound per 1KG");
    }
}

class GymSystem {
    ArrayList<Member> members;
    ArrayList<Coach> coaches;
    ArrayList<Product> products;
    ArrayList<Subscription> subscriptions;
    public static double Price = 500;

    public GymSystem(ArrayList<Member> members, ArrayList<Coach> coaches, ArrayList<Product> products, ArrayList<Subscription> subscriptions, double price) {
        this.members = members;
        this.coaches = coaches;
        this.subscriptions = subscriptions;
        this.products = products;
    }

    public GymSystem() {
        members = new ArrayList<>();
        coaches = new ArrayList<>();
        products = new ArrayList<>();
        subscriptions = new ArrayList<>();
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public void setCoach(Coach coach) {
        coaches.add(coach);
    }

    public void setProduct(Product product) {
        products.add(product);
    }
}

class Main {
    public static void main(String[] args) {
        Creatine c = new Creatine("maxMusle", 100.9, 9, LocalDate.now().plusMonths(6), 10);
        Member m = new Member("ali", 28, Gender.male, 900, new GoldSubscription());
        Coach co = new Coach("ali", 28, Gender.male);
        GymSystem gymSystem = new GymSystem();
        System.out.println(m.getSubscriptionPrice());
        gymSystem.addMember(m);
        gymSystem.setCoach(co);
        gymSystem.setProduct(c);
        Cart cart = new Cart();
        cart.addProduct(c);
        ChekOut chekOut = new ChekOut();
        chekOut.paying(cart, m);
    }
}
