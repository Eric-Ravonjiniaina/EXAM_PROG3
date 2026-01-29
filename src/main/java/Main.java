import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DataRetriever dataRetriever = new DataRetriever();
        try {
            System.out.println("--- DÉBUT DES TESTS ---");

            // 1. TEST : Sauvegarde d'un ingrédient et d'un plat
            // (Nécessaire pour que saveOrder ne plante pas sur les stocks)
            Ingredient tomate = new Ingredient();
            tomate.setName("Tomate");
            tomate.setPrice(1.5);
            tomate.setCategory(CategoryEnum.VEGETABLE); // Assure-toi que cet Enum existe
            // tomate = dataRetriever.saveIngredient(tomate);

            // 2. TEST : Création d'une Commande (SAVE)
            System.out.println("\n1. Test de SAVE ORDER...");
            Order newOrder = new Order();
            newOrder.setReference("ORD-" + System.currentTimeMillis());
            newOrder.setCreationDatetime(Instant.now());

            // Assignation du Type et du Statut (Le travail demandé)
            newOrder.setType(OrderType.TAKE_AWAY);
            newOrder.setStatus(OrderStatus.CREATED);

            // On lui ajoute une liste vide de plats pour le test (ou remplie si tu as les IDs)
            newOrder.setDishOrderList(new ArrayList<>());

            Order savedOrder = dataRetriever.saveOrder(newOrder);
            System.out.println("✅ Commande enregistrée avec ID : " + savedOrder.getId());
            System.out.println("Type : " + savedOrder.getType() + " | Statut : " + savedOrder.getStatus());


            // 3. TEST : Mise à jour du Statut (SAVE pour modification)
            System.out.println("\n2. Test de MISE À JOUR du Statut...");
            savedOrder.setStatus(OrderStatus.READY); // On passe de CREATED à READY
            dataRetriever.saveOrder(savedOrder);
            System.out.println("✅ Statut mis à jour vers : " + savedOrder.getStatus());


            // 4. TEST : Récupération (FIND)
            System.out.println("\n3. Test de FIND ORDER BY REFERENCE...");
            Order retrievedOrder = dataRetriever.findOrderByReference(savedOrder.getReference());

            if (retrievedOrder != null) {
                System.out.println("✅ Commande retrouvée !");
                System.out.println("   Référence : " + retrievedOrder.getReference());
                System.out.println("   Type récupéré : " + retrievedOrder.getType());
                System.out.println("   Statut récupéré : " + retrievedOrder.getStatus());
            }

            System.out.println("\n--- FIN DES TESTS AVEC SUCCÈS ---");

        } catch (Exception e) {
            System.err.println("\n❌ ERREUR DURANT LE TEST :");
            e.printStackTrace();
        }
    }
}
