package Project;

import com.google.gson.GsonBuilder;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.bson.Document;

public class FinalProject {

    static int idDimensionalProduct = 0;
    static int idWeightedProduct = 0;
    static final String IP = "localhost";
    static final int PORT = 27017;
    static final String DB_NAME = "DBCourse";
    static final String COLLECTION_NAME = "Products";
    static MongoCollection<Document> collection;

    public static void main(String[] args) {
        initiateVariables();
        Scanner input = new Scanner(System.in);

        int choice;
        String nameProduct;
        String descriptionProduct;
        int priceProduct;
        int widthProduct;
        int heightProduct;
        int weightProduct;
        int choiceProduct;
        int inputComparisonOperator;
        int inputComparisonKey;
        Object valueComparison;
        do {
            showMenu();
            choice = input.nextInt();
            switch (choice) {
                case 1:
                    showAllProducts();
                    break;
                case 2:
                    System.out.println("1- Add Dimensional Product.");
                    System.out.println("2- Add Weighted Product.");
                    choiceProduct = input.nextInt();
                    switch (choiceProduct) {
                        case 1:
                            System.out.println("Add Name Dimensional Product : - ");
                            nameProduct = input.next();
                            System.out.println("Add description Dimensional Product : - ");
                            descriptionProduct = input.next();
                            System.out.println("Add price Dimensional Product : - ");
                            priceProduct = input.nextInt();
                            System.out.println("Add width Dimensional Product : - ");
                            widthProduct = input.nextInt();
                            System.out.println("Add  height Dimensional Product : - ");
                            heightProduct = input.nextInt();
                            idDimensionalProduct++;
                            addDimensionalProduct(new DimensionalProduct(idDimensionalProduct, nameProduct, descriptionProduct, priceProduct, widthProduct, heightProduct));
                            break;
                        case 2:
                            System.out.println("Add Name Weighted Product : - ");
                            nameProduct = input.next();
                            System.out.println("Add description Weighted Product : - ");
                            descriptionProduct = input.next();
                            System.out.println("Add  price Weighted Product : - ");
                            priceProduct = input.nextInt();
                            System.out.println("Add   weight Weighted Product : - ");
                            weightProduct = input.nextInt();
                            idWeightedProduct++;
                            addWeightedProduct(new WeightedProduct(weightProduct, idWeightedProduct, nameProduct, descriptionProduct, priceProduct));
                            break;
                        default:
                            System.out.println("NOt Valid Choise");
                    }
                    break;
                case 3:
                    deleteProduct();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    System.out.println("Enter the condition for Replace operation ");
                    System.out.println("==================Choose Key================================\n"
                            + "1.ID\n"
                            + "\n"
                            + "2.Name\n"
                            + "\n"
                            + "3. Description\n"
                            + "\n"
                            + "4. Price\n"
                            + "\n"
                            + "5. Weight (Application only for Wighted Products)\n"
                            + "\n"
                            + "6. Width (Application only for Dimensional Products)\n"
                            + "\n"
                            + "7. Height (Application only for Dimensional Products)\n"
                            + "==================================================");
                    System.out.println("Whats your Key? (Enter the number only : )");
                    inputComparisonKey = input.nextInt();

                    System.out.println("================= Choose operator =================================\n"
                            + "1.Greater Than\n"
                            + "\n"
                            + "2.Greater Than or Equals\n"
                            + "\n"
                            + "3. Less Than\n"
                            + "\n"
                            + "4. Less Than or Equals\n"
                            + "\n"
                            + "5. Equals\n"
                            + "\n"
                            + "6. Not Equals\n"
                            + "==================================================");
                    System.out.println("Whats your operator? (Enter the number only : )");
                    inputComparisonOperator = input.nextInt();
                    System.out.println("Enter Value : ");
                    valueComparison = input.next();
                    businessLogicReplaceProduct(inputComparisonOperator, inputComparisonKey, valueComparison);

//                    replaceProduct();
                    break;
                case 6:

                    System.out.println("Enter the condition for search operation ");
                    System.out.println("==================Choose Key================================\n"
                            + "1.ID\n"
                            + "\n"
                            + "2.Name\n"
                            + "\n"
                            + "3. Description\n"
                            + "\n"
                            + "4. Price\n"
                            + "\n"
                            + "5. Weight (Application only for Wighted Products)\n"
                            + "\n"
                            + "6. Width (Application only for Dimensional Products)\n"
                            + "\n"
                            + "7. Height (Application only for Dimensional Products)\n"
                            + "==================================================");
                    System.out.println("Whats your Key? (Enter the number only : )");
                    inputComparisonKey = input.nextInt();

                    System.out.println("================= Choose operator =================================\n"
                            + "1.Greater Than\n"
                            + "\n"
                            + "2.Greater Than or Equals\n"
                            + "\n"
                            + "3. Less Than\n"
                            + "\n"
                            + "4. Less Than or Equals\n"
                            + "\n"
                            + "5. Equals\n"
                            + "\n"
                            + "6. Not Equals\n"
                            + "==================================================");
                    System.out.println("Whats your operator? (Enter the number only : )");
                    inputComparisonOperator = input.nextInt();
                    System.out.println("Enter Value : ");
                    valueComparison = input.next();
                    businessLogicSearchProduct(inputComparisonOperator, inputComparisonKey, valueComparison);
                    break;
            }
        } while (choice != 7);

    }

    public static void initiateVariables() {
        //Connection (server IP, PORT)
        MongoClient connection = new MongoClient(IP, PORT);
        //Connection (Database)
        MongoDatabase database = connection.getDatabase(DB_NAME);
        //Connection (Table)
        collection = database.getCollection(COLLECTION_NAME);
    }

    public static void showMenu() {
        System.out.println("1- Show all Products.");
        System.out.println("2- Add Product.");
        System.out.println("3- Delete Product.");
        System.out.println("4- Update Product.");
        System.out.println("5- Replace Product.");
        System.out.println("6- Search Product.");
        System.out.println("7- Exit.");
        System.out.print("Enter your choice: ");
    }

    public static void showAllProducts() {

        for (Product p : getAllProduct()) {
            System.out.println("----------" + p.toString());
        }

    }

    public static void addDimensionalProduct(DimensionalProduct dp) {
        Document doc = new Document("id", dp.getId()).append("name", dp.getName()).append("description", dp.getDescription()).append("price", dp.getPrice()).append("width", dp.getWidth()).append("height", dp.getHeight());
        collection.insertOne(doc);
    }

    public static void addWeightedProduct(WeightedProduct wp) {
        Document doc = new Document("weight", wp.getWeight()).append("id", wp.getId()).append("name", wp.getName()).append("description", wp.getDescription()).append("price", wp.getPrice());
        collection.insertOne(doc);
    }
//    

    public static List<Product> getAllProduct() {
        List<Product> list = new ArrayList<>();
        for (Document doc : collection.find()) {

            System.out.println("-----" + doc);
            if (doc.containsKey("weight")) {
                list.add(new GsonBuilder().create().fromJson(doc.toJson(), WeightedProduct.class));
            } else {
                list.add(new GsonBuilder().create().fromJson(doc.toJson(), DimensionalProduct.class));
            }
        }
        return list;
    }

    public static void deleteProduct() {

    }

    public static void updateProduct() {

    }

    public static long replaceDimensionalProduct(String operator, String key, Object value, DimensionalProduct p) {
        Document doc = new Document("id", p.getId()).append("name", p.getName()).append("description", p.getDescription()).append("price", p.getPrice()).append("width", p.getWidth()).append("height", p.getHeight());

        UpdateResult result = null;
        switch (operator) {
            case Operators.GREATER_THAN:
                result = collection.replaceOne(Filters.gt(key, value), doc);
                break;
            case Operators.GREATER_THAN_OR_EQUALS:
                result = collection.replaceOne(Filters.gte(key, value), doc);
                break;
            case Operators.LESS_THAN:
                result = collection.replaceOne(Filters.lt(key, value), doc);
                break;
            case Operators.LESS_THAN_OR_EQUALS:
                result = collection.replaceOne(Filters.lte(key, value), doc);
                break;
            case Operators.EQUALS:
                result = collection.replaceOne(Filters.eq(key, value), doc);
                break;
            case Operators.NOT_EQUALS:
                result = collection.replaceOne(Filters.ne(key, value), doc);
                break;
        }

        return result.getModifiedCount();

    }

    public static long replaceWeightedProduct(String operator, String key, Object value, WeightedProduct wp) {
        Document doc = new Document("weight", wp.getWeight()).append("id", wp.getId()).append("name", wp.getName()).append("description", wp.getDescription()).append("price", wp.getPrice());

        UpdateResult result = null;

        switch (operator) {
            case Operators.GREATER_THAN:
                result = collection.replaceOne(Filters.gt(key, value), doc);
                break;
            case Operators.GREATER_THAN_OR_EQUALS:
                result = collection.replaceOne(Filters.gte(key, value), doc);
                break;
            case Operators.LESS_THAN:
                result = collection.replaceOne(Filters.lt(key, value), doc);
                break;
            case Operators.LESS_THAN_OR_EQUALS:
                result = collection.replaceOne(Filters.lte(key, value), doc);
                break;
            case Operators.EQUALS:
                result = collection.replaceOne(Filters.eq(key, value), doc);
                break;
            case Operators.NOT_EQUALS:
                result = collection.replaceOne(Filters.ne(key, value), doc);
                break;
        }

        return result.getModifiedCount();

    }

    public static List<Product> searchProduct(String operator, String key, Object value) {
        List<Product> list = new ArrayList<>();
        FindIterable<Document> iterator = null;
        switch (operator) {
            case Operators.GREATER_THAN:
                iterator = collection.find(Filters.gt(key, value));
                break;
            case Operators.GREATER_THAN_OR_EQUALS:
                iterator = collection.find(Filters.gte(key, value));

                break;
            case Operators.LESS_THAN:
                iterator = collection.find(Filters.lt(key, value));

                break;
            case Operators.LESS_THAN_OR_EQUALS:
                iterator = collection.find(Filters.lte(key, value));
                break;
            case Operators.EQUALS:
                iterator = collection.find(Filters.eq(key, value));
                System.out.println("-----------------iterator");
                break;
            case Operators.NOT_EQUALS:
                iterator = collection.find(Filters.ne(key, value));
                break;
        }

        for (Document doc : iterator) {
            if (doc.containsKey("weight")) {
                list.add(new GsonBuilder().create().fromJson(doc.toJson(), WeightedProduct.class));
            } else {
                list.add(new GsonBuilder().create().fromJson(doc.toJson(), DimensionalProduct.class));
            }
        }
        return list;

    }

    public static void businessLogicReplaceProduct(int inputComparisonOperator, int inputComparisonKey, Object valueComparison) {

        Scanner input = new Scanner(System.in);
        String nameProduct;
        String descriptionProduct;
        int priceProduct;
        int widthProduct;
        int heightProduct;
        int weightProduct;
        DimensionalProduct d = null;
        WeightedProduct w = null;
        int value;
        System.out.println("1- New Dimensional Product.");
        System.out.println("2- New Weighted Product.");
        int choiceProduct = input.nextInt();
        switch (choiceProduct) {
            case 1:
                System.out.println(" Name Dimensional Product : - ");
                nameProduct = input.next();
                System.out.println(" description Dimensional Product : - ");
                descriptionProduct = input.next();
                System.out.println(" price Dimensional Product : - ");
                priceProduct = input.nextInt();
                System.out.println(" width Dimensional Product : - ");
                widthProduct = input.nextInt();
                System.out.println(" height Dimensional Product : - ");
                heightProduct = input.nextInt();
                idDimensionalProduct++;
                d = new DimensionalProduct(idDimensionalProduct, nameProduct, descriptionProduct, priceProduct, widthProduct, heightProduct);
                break;
            case 2:
                System.out.println(" Name Weighted Product : - ");
                nameProduct = input.next();
                System.out.println(" description Weighted Product : - ");
                descriptionProduct = input.next();
                System.out.println("  price Weighted Product : - ");
                priceProduct = input.nextInt();
                System.out.println(" weight Weighted Product : - ");
                weightProduct = input.nextInt();
                idWeightedProduct++;
                w = new WeightedProduct(weightProduct, idWeightedProduct, nameProduct, descriptionProduct, priceProduct);
                break;
        }

        if (d != null || w != null) {
            switch (inputComparisonOperator) {
                case 1:
                    switch (inputComparisonKey) {
                        case 1:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN, "id", value, w);
                            }

                            break;
                        case 2:

                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN, "price", value, w);
                            }

                            break;
                        case 5:
                            value = Integer.valueOf((String) valueComparison);
                            replaceWeightedProduct(Operators.GREATER_THAN, "weight", value, w);

                            break;
                        case 6:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.GREATER_THAN, "width", value, d);
                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.GREATER_THAN, "height", value, d);
                            break;
                    }
                    break;
                case 2:
                    switch (inputComparisonKey) {
                        case 1:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN_OR_EQUALS, "id", value, w);
                            }

                            break;
                        case 2:

                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN_OR_EQUALS, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN_OR_EQUALS, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.GREATER_THAN_OR_EQUALS, "price", value, w);
                            }

                            break;
                        case 5:
                            value = Integer.valueOf((String) valueComparison);

                            replaceWeightedProduct(Operators.GREATER_THAN_OR_EQUALS, "weight", value, w);

                            break;
                        case 6:

                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "width", value, d);

                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.GREATER_THAN_OR_EQUALS, "height", value, d);
                            break;
                    }

                    break;
                case 3:
                    switch (inputComparisonKey) {
                        case 1:

                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN, "id", value, w);
                            }

                            break;
                        case 2:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN, "price", value, w);
                            }

                            break;
                        case 5:
                            value = Integer.valueOf((String) valueComparison);

                            replaceWeightedProduct(Operators.LESS_THAN, "weight", value, w);

                            break;
                        case 6:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.LESS_THAN, "width", value, d);

                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.LESS_THAN, "height", value, d);

                            break;
                    }

                    break;
                case 4:
                    switch (inputComparisonKey) {
                        case 1:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN_OR_EQUALS, "id", value, w);
                            }

                            break;
                        case 2:

                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN_OR_EQUALS, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN_OR_EQUALS, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.LESS_THAN_OR_EQUALS, "price", value, w);
                            }

                            break;
                        case 5:
                            value = Integer.valueOf((String) valueComparison);
                            replaceWeightedProduct(Operators.LESS_THAN_OR_EQUALS, "weight", value, w);

                            break;
                        case 6:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "width", value, d);

                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.LESS_THAN_OR_EQUALS, "height", value, d);

                            break;
                    }

                    break;
                case 5:

                    switch (inputComparisonKey) {
                        case 1:

                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.EQUALS, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.EQUALS, "id", value, w);
                            }

                            break;
                        case 2:

                            if (d != null) {
                                replaceDimensionalProduct(Operators.EQUALS, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.EQUALS, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.EQUALS, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.EQUALS, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.EQUALS, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.EQUALS, "price", value, w);
                            }

                            break;
                        case 5:

                            value = Integer.valueOf((String) valueComparison);
                            replaceWeightedProduct(Operators.EQUALS, "weight", value, w);

                            break;
                        case 6:
                            value = Integer.valueOf((String) valueComparison);
                            for (Product product : searchProduct(Operators.EQUALS, "width", value)) {
                                System.out.println(product.toString());
                            }

                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.EQUALS, "height", value, d);

                            break;
                    }

                    break;
                case 6:

                    switch (inputComparisonKey) {
                        case 1:

                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.NOT_EQUALS, "id", value, d);
                            } else {
                                replaceWeightedProduct(Operators.NOT_EQUALS, "id", value, w);
                            }

                            break;
                        case 2:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.NOT_EQUALS, "name", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.NOT_EQUALS, "name", valueComparison, w);
                            }

                            break;
                        case 3:
                            if (d != null) {
                                replaceDimensionalProduct(Operators.NOT_EQUALS, "description", valueComparison, d);
                            } else {
                                replaceWeightedProduct(Operators.NOT_EQUALS, "description", valueComparison, w);
                            }

                            break;
                        case 4:
                            value = Integer.valueOf((String) valueComparison);
                            if (d != null) {
                                replaceDimensionalProduct(Operators.NOT_EQUALS, "price", value, d);
                            } else {
                                replaceWeightedProduct(Operators.NOT_EQUALS, "price", value, w);
                            }

                            break;
                        case 5:
                            value = Integer.valueOf((String) valueComparison);
                            replaceWeightedProduct(Operators.NOT_EQUALS, "weight", value, w);

                            break;
                        case 6:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.NOT_EQUALS, "width", value, d);

                            break;
                        case 7:
                            value = Integer.valueOf((String) valueComparison);
                            replaceDimensionalProduct(Operators.NOT_EQUALS, "height", value, d);
                            break;
                    }

                    break;
            }
        }

    }

    public static void businessLogicSearchProduct(int inputComparisonOperator, int inputComparisonKey, Object valueComparison) {
        int value;
        switch (inputComparisonOperator) {
            case 1:
                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:
                        for (Product product : searchProduct(Operators.GREATER_THAN, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.GREATER_THAN, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }
                break;
            case 2:
                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.GREATER_THAN_OR_EQUALS, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }

                break;
            case 3:
                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:
                        for (Product product : searchProduct(Operators.LESS_THAN, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.LESS_THAN, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }

                break;
            case 4:
                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:

                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.LESS_THAN_OR_EQUALS, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }

                break;
            case 5:

                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.EQUALS, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:
                        for (Product product : searchProduct(Operators.EQUALS, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.EQUALS, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.EQUALS, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.EQUALS, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.EQUALS, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.EQUALS, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }

                break;
            case 6:

                switch (inputComparisonKey) {
                    case 1:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "id", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 2:
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "name", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 3:
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "description", valueComparison)) {
                            System.out.println(product.toString());
                        }
                        break;
                    case 4:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "price", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 5:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "weight", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 6:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "width", value)) {
                            System.out.println(product.toString());
                        }

                        break;
                    case 7:
                        value = Integer.valueOf((String) valueComparison);
                        for (Product product : searchProduct(Operators.NOT_EQUALS, "height", value)) {
                            System.out.println(product.toString());
                        }
                        break;
                }

                break;
        }
    }

}
