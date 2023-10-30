package theatricalplays;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;


public class StatementPrinter {

  public String print(Invoice invoice, Map<String, Play> plays) {
    //int totalAmount = 0;
    double totalAmount = 0.0;
    int volumeCredits = 0;
    StringBuilder result = new StringBuilder();
    
    // En-tête de la déclaration
    result.append(String.format("Statement for %s\n", invoice.customer));

    NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

    for (Performance perf : invoice.performances) {
      Play play = plays.get(perf.playID);
      //int thisAmount = 0;
      double thisAmount = 0.0;

      // Calcul du montant en fonction du type de pièce de théâtre
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
          throw new Error("unknown type: ${play.type}");
      }

      // Ajouter des crédits de volume
      volumeCredits += Math.max(perf.audience - 30, 0);

      // Ajouter des crédits supplémentaires pour chaque groupe de 5 personnes pour les comédies
      if (Play.PlayType.comedy.equals(play.type)) volumeCredits += Math.floor(perf.audience / 5);

      // Ajouter une ligne pour cette représentation
      result.append(String.format("  %s: %s (%s seats)\n", play.name, frmt.format(thisAmount), perf.audience));
      totalAmount += thisAmount;
    }

    // Ajouter le montant total dû
    result.append(String.format("Amount owed is %s\n", frmt.format(totalAmount)));

     // Ajouter le total des crédits gagnés
    result.append(String.format("You earned %s credits\n", volumeCredits));

    return result.toString();
  }

}
