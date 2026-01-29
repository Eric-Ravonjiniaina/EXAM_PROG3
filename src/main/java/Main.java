import java.time.Instant;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        Dish saladeVerte = dataRetriever.findDishById(1);
        System.out.println(saladeVerte);

        Dish poulet = dataRetriever.findDishById(2);
        System.out.println(poulet);

        Dish rizLegume = dataRetriever.findDishById(3);
        rizLegume.setPrice(100.0);
        Dish newRizLegume = dataRetriever.saveDish(rizLegume);
        System.out.println(newRizLegume); // Should not throw exception


        Dish rizLegumeAgain = dataRetriever.findDishById(3);
        rizLegumeAgain.setPrice(null);
        Dish savedNewRizLegume = dataRetriever.saveDish(rizLegume);
        System.out.println(savedNewRizLegume); // Should throw exception

        Ingredient laitue = dataRetriever.findIngredientById(1);
        System.out.println(laitue);

        // 1. Création d'un ingrédient (ex: Tomate)
        Ingredient tomate = new Ingredient();
        tomate.setName("Tomate");

        // 2. Simulation du stock : On ajoute 10 Tomates (Mouvement IN)
        StockValue dixTomates = new StockValue();
        dixTomates.setQuantity(20.0);
        dixTomates.setUnit(Unit.PCS); // Supposons que tu as une Enum Unit

        StockMovement entreeStock = new StockMovement();
        entreeStock.setType(MovementTypeEnum.IN);
        entreeStock.setCreationDatetime(Instant.now().minusSeconds(3600)); // Il y a 1h
        entreeStock.setValue(dixTomates);

        tomate.setStockMovementList(List.of(entreeStock));

        // 3. Création d'un plat (ex: Salade) qui demande 15 Tomates
        Dish salade = new Dish();
        DishIngredient besoin = new DishIngredient();
        besoin.setIngredient(tomate);
        besoin.setQuantity(5.0); // On en veut 15 alors qu'on en a 10

        // Supposons que ta classe Dish a une liste d'ingrédients
        salade.setDishIngredients(List.of(besoin));

        // 4. Création de la commande
        Order maCommande = new Order();
        DishOrder ligneCommande = new DishOrder();
        ligneCommande.setDish(salade);
        ligneCommande.setQuantity(1); // 1 seule salade
        maCommande.setDishOrderList(List.of(ligneCommande));

        // 5. TEST DE LA MÉTHODE
        try {
            dataRetriever.saveOrder(maCommande);
            System.out.println("Saved !");
        } catch (RuntimeException e) {
            // Ici, l'exception va être levée car 10 < 15
            System.err.println("ERREUR : " + e.getMessage());
        }
    }
}
