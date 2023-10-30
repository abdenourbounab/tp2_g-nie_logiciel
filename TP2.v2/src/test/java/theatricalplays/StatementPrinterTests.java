package theatricalplays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import theatricalplays.InvoiceToHTML;

import java.util.List;
import java.util.Map;

import static org.approvaltests.Approvals.verify;


public class StatementPrinterTests {

    @Test
    void exampleStatement() {
        Map<String, Play> plays = Map.of(
                "hamlet",  Play.createPlay("Hamlet", Play.PlayType.tragedy),
                "as-like", Play.createPlay("As You Like It", Play.PlayType.comedy),
                "othello", Play.createPlay("Othello", Play.PlayType.tragedy));

        Customer customer = new Customer("BigCo", 12345, 100); // Create a Customer object

        Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);

        verify(result);
    }

    @Test
    void generateHTMLInvoiceTest() {
        Map<String, Play> plays = Map.of(
                "hamlet", Play.createPlay("Hamlet", Play.PlayType.tragedy),
                "as-like", Play.createPlay("As You Like It", Play.PlayType.comedy),
                "othello", Play.createPlay("Othello", Play.PlayType.tragedy));

        Customer customer = new Customer("BigCo", 12345, 100); // Create a Customer object

        Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        var generatedHTML =  InvoiceToHTML.generateHTMLInvoice(invoice.getCustomer().getName(), invoice.performances, plays); // Use the updated method with Invoice object

        verify(generatedHTML);
    }

    @Test
    void tragedyWithSmallAudience() {
        Map<String, Play> plays = Map.of("hamlet", Play.createPlay("Hamlet", Play.PlayType.tragedy));
        Customer customer = new Customer("BigCo", 12345, 100); // Create a Customer object
        Invoice invoice = new Invoice(customer, List.of(new Performance("hamlet", 10)));
        StatementPrinter statementPrinter = new StatementPrinter();

        var result = statementPrinter.print(invoice, plays);
        
        var result2 = InvoiceToHTML.generateHTMLInvoice(invoice.getCustomer().getName(), invoice.performances, plays); // Use the updated method with Invoice object
        String expected = "Statement for BigCo\n" +
                          "  Hamlet: $400.00 (10 seats)\n" +
                          "Amount owed is $400.00\n" +
                          "You earned 0 credits\n";

        String expectedHTML = "<html> <style> table, th, td {border:1px solid black;} </style> <head><title>Facture</title></head><body><h1>Invoice</h1><ul><li><b>Client : </b>BigCo</li></ul><table><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr><tr><td>Hamlet</td><td>10</td><td>$400.00</td></tr><tr><th colspan=\"2\">Total owed : </th><td>$400.00</td></tr><tr><th colspan=\"2\">Fidelity points earned : </th><td>0</td></tr></table></body></html>";

        assertEquals(expected, result);
        assertEquals(expectedHTML, result2);
    }

    @Test
    void comedyWithSmallAudience() {
        Map<String, Play> plays = Map.of("As You Like It", Play.createPlay("As You Like It", Play.PlayType.comedy));
        Customer customer = new Customer("BigCo", 12345, 100); // Create a Customer object
        Invoice invoice = new Invoice(customer, List.of(new Performance("As You Like It", 10)));
        StatementPrinter statementPrinter = new StatementPrinter();

        var result = statementPrinter.print(invoice, plays);
        var result2 = InvoiceToHTML.generateHTMLInvoice(invoice.getCustomer().getName(), invoice.performances, plays);

        String expected = "Statement for BigCo\n" +
                          "  As You Like It: $330.00 (10 seats)\n" +
                          "Amount owed is $330.00\n" +
                          "You earned 2 credits\n";
        
        String expectedHTML = "<html> <style> table, th, td {border:1px solid black;} </style> <head><title>Facture</title></head><body><h1>Invoice</h1><ul><li><b>Client : </b>BigCo</li></ul><table><tr><th>Piece</th><th>Seats sold</th><th>Price</th></tr><tr><td>As You Like It</td><td>10</td><td>$330.00</td></tr><tr><th colspan=\"2\">Total owed : </th><td>$330.00</td></tr><tr><th colspan=\"2\">Fidelity points earned : </th><td>2</td></tr></table></body></html>";

        assertEquals(expected, result);
        assertEquals(expectedHTML, result2);
    }




    @Test
    void exampleStatement2() {
        Map<String, Play> plays = Map.of(
                "hamlet",  Play.createPlay("Hamlet", Play.PlayType.tragedy),
                "as-like", Play.createPlay("As You Like It", Play.PlayType.comedy),
                "othello", Play.createPlay("Othello", Play.PlayType.tragedy));

        Customer customer = new Customer("BigCo", 12345, 130); // Create a Customer object

        Invoice invoice = new Invoice(customer, List.of(
                new Performance("hamlet", 55),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        StatementPrinter statementPrinter = new StatementPrinter();
        var result = statementPrinter.print(invoice, plays);
        
        String expected = "Statement for BigCo\n" +
                        "  Hamlet: $650.00 (55 seats)\n" +
                        "  As You Like It: $580.00 (35 seats)\n" +
                        "  Othello: $500.00 (40 seats)\n" +
                        "Amount owed is $1,730.00\n" +
                        "You earned 47 credits\n" +
                        "You have 177 Loyalty Points, you get a $15 reduction\n" +
                        "Amount owed after reduction $1,715.00\n" +
                        "New Fidelity Points 27\n";
        assertEquals(expected, result);
    }


    



    


    @Test
    void statementWithNewPlayTypes() {
        Map<String, Play> plays = Map.of(
                "henry-v",  Play.createPlay("Henry V", Play.PlayType.history),
                "as-like", Play.createPlay("As You Like It", Play.PlayType.pastoral));
    
        Customer customer = new Customer("BigCo", 12345, 100); 
    
        Invoice invoice = new Invoice(customer, List.of(
                new Performance("henry-v", 53),
                new Performance("as-like", 55)));
    
        StatementPrinter statementPrinter = new StatementPrinter();
        Assertions.assertThrows(Error.class, () -> {
            statementPrinter.print(invoice, plays);
        });
        Assertions.assertThrows(Error.class, () -> {
            InvoiceToHTML.generateHTMLInvoice(invoice.getCustomer().getName(), invoice.performances, plays);
        });
    }
    
}
