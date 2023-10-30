package theatricalplays;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class InvoiceToHTML {

    public static String generateHTMLInvoice(Customer customer, List<Performance> performances, Map<String, Play> plays) {
        // Entête de la page HTML
        StringBuilder html = new StringBuilder();
        html.append("<html> <style> table, th, td {border:1px solid black;} </style> <head><title>Facture</title></head><body>");

        // En-tête de la facture
        html.append("<h1>Invoice</h1>");

        html.append("<ul><li><b>Client : </b>").append(customer.getName()).append("</li></ul>");

        html.append("<table><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr>");

        // Initialize volumeCredits to 0
        int volumeCredits = 0;

        // Boucle sur les performances
        for (Performance perf : performances) {
            Play play = plays.get(perf.playID);

            // Calcul du montant et des crédits pour cette performance
            double thisAmount = calculatePerformanceAmount(play, perf);
            int thisVolumeCredits = calculateVolumeCredits(play, perf);

            // Ajouter des crédits pour cette performance
            volumeCredits += thisVolumeCredits;

            // Ajouter une ligne à la table HTML
            html.append("<tr>")
                .append("<td>").append(play.name).append("</td>")
                .append("<td>").append(perf.audience).append("</td>")
                .append("<td>").append(NumberFormat.getCurrencyInstance(Locale.US).format(thisAmount)).append("</td>")
                .append("</tr>");
        }

        

        // Calcul du montant total
        double totalAmount = calculateTotalAmount(performances, plays);

        customer.setLoyaltyPoints(customer.getLoyaltyPoints() + volumeCredits);

        // Ajout du montant total
        html.append("<tr><th colspan=\"2\">Total owed : </th>").append("<td>").append(NumberFormat.getCurrencyInstance(Locale.US).format(totalAmount)).append("</td></tr>");

        // Ajout des volumeCredits
        html.append("<tr><th colspan=\"2\">Fidelity points earned : </th>").append("<td>").append(volumeCredits).append("</td></tr>");

        int customerLoyaltyPoints = customer.getLoyaltyPoints();

        if (customerLoyaltyPoints >= 150) {
            
            double discountAmount = 15.0;
            double totalAmountReduced = totalAmount - discountAmount;
            // Ajout du message de réduction
            html.append("<tr><th colspan=\"3\">You have ").append(customerLoyaltyPoints)
                .append(" Loyalty Points, you get a $15 reduction</th></tr>");

            customerLoyaltyPoints -= 150;
            customer.setLoyaltyPoints(customerLoyaltyPoints);

            // Ajout du montant après réduction
            html.append("<tr><th colspan=\"2\">Amount owed after reduction : </th>")
                .append("<td>").append(NumberFormat.getCurrencyInstance(Locale.US).format(totalAmountReduced)).append("</td></tr>");

            // Ajout des nouveaux points de fidélité
            html.append("<tr><th colspan=\"2\">New Fidelity Points : </th>")
                .append("<td>").append(customerLoyaltyPoints).append("</td></tr>");
        }


        
        html.append("</table>");
        // Fin de la page HTML
        html.append("</body></html>");

        return html.toString();
    }

    private static double calculatePerformanceAmount(Play play, Performance perf) {
        double thisAmount = 0.0;
        switch (play.type) {
            case Play.PlayType.tragedy:
                thisAmount = 400.0;
                if (perf.audience > 30) {
                    thisAmount += 10.0 * (perf.audience - 30);
                }
                break;
            case Play.PlayType.comedy:
                thisAmount = 300.0;
                if (perf.audience > 20) {
                    thisAmount += 100.0 + 5.0 * (perf.audience - 20);
                }
                thisAmount += 3.0 * perf.audience;
                break;
            default:
                throw new Error("unknown type: " + play.type);
        }
        return thisAmount;
    }

    private static int calculateVolumeCredits(Play play, Performance perf) {
        // Ajouter des crédits de volume
        int volumeCredits = Math.max(perf.audience - 30, 0);

        // Ajouter des crédits supplémentaires pour chaque groupe de 5 personnes pour les comédies
        if (play.type.equals(Play.PlayType.comedy)) {
            volumeCredits += perf.audience / 5;
        }
        return volumeCredits;
    }

    private static double calculateTotalAmount(List<Performance> performances, Map<String, Play> plays) {
        double totalAmount = 0.0;
        for (Performance perf : performances) {
            Play play = plays.get(perf.playID);
            totalAmount += calculatePerformanceAmount(play, perf);
        }
        return totalAmount;
    }

    
}

